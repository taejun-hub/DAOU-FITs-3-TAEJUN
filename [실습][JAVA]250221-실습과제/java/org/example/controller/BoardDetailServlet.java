package org.example.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.BoardDAO;
import org.example.dao.CommentDAO;
import org.example.dao.LikeDAO;
import org.example.mybatis.MyBatisSessionFactory;
import org.example.service.BoardServletService;
import org.example.service.CommentServletService;
import org.example.service.LikeServletService;
import org.example.vo.BoardVO;
import org.example.vo.CommentVO;
import org.example.vo.LikeVO;
import org.example.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/boardDetailServlet")
public class BoardDetailServlet extends HttpServlet {

    private BoardServletService boardServletService;
    private CommentServletService commentServletService;
    private LikeServletService likeServletService;

    @Override
    public void init() throws ServletException {
        super.init();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.boardServletService = new BoardServletService(sqlSessionFactory, new BoardDAO());
        this.commentServletService = new CommentServletService(sqlSessionFactory, new CommentDAO());
        this.likeServletService = new LikeServletService(sqlSessionFactory, new LikeDAO());
        System.out.println("boardDetailServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("BoardDetail doGet called");

        req.setCharacterEncoding("UTF-8");

        boolean hasLiked = false;
        String board_id = req.getParameter("id");
        BoardVO board = this.boardServletService.getBoard(Integer.parseInt(board_id));

        HttpSession session = req.getSession(false);
        MemberVO member = (MemberVO) session.getAttribute("member");
        if (member != null) {
            hasLiked = this.likeServletService.hasUserLikedBoard(new LikeVO(member.getId(), Integer.parseInt(board_id)));
        }

        List<CommentVO> commentList = this.commentServletService.getCommentsByBoardId(Integer.parseInt(board_id));



        // JSP를 이용해서 View 처리
        RequestDispatcher rd = req.getRequestDispatcher("boardDetail.jsp");
        req.setAttribute("hasLiked", hasLiked);
        req.setAttribute("board", board);
        req.setAttribute("commentList", commentList);
//        req.setAttribute("member", member);
        rd.forward(req, resp);

        resp.setContentType("text/html;charset=UTF-8");


    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        System.out.println("doPost called");

        // 세션에서 로그인된 사용자 정보 가져오기
        HttpSession session = req.getSession(false);
        String member_id = null;

        if (session != null) {
            MemberVO member = (MemberVO) session.getAttribute("member");
            if (member != null) {
                member_id = member.getId();
            }
        }
        String board_id = req.getParameter("board_id");
        int success = this.boardServletService.deleteBoard(Integer.parseInt(board_id), member_id);
        System.out.println("success = " + success);
        if (success > 0) {
            resp.getWriter().println("<script>alert('글 삭제 성공'); window.history.back;</script>");
        } else {
            resp.getWriter().println("<script>alert('삭제 실패!');</script>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
