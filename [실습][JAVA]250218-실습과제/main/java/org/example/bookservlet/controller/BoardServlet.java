package org.example.bookservlet.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.bookservlet.dao.BoardDAO;
import org.example.bookservlet.mybatis.MyBatisSessionFactory;
import org.example.bookservlet.service.BoardServletService;
import org.example.bookservlet.vo.BoardVO;
import org.example.bookservlet.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/boardServlet")
public class BoardServlet extends HttpServlet {

    private BoardServletService boardServletService;

    @Override
    public void init() throws ServletException {
        super.init();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.boardServletService = new BoardServletService(sqlSessionFactory, new BoardDAO());
        System.out.println("boardServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        // 세션에서 로그인된 사용자 정보 가져오기
        HttpSession session = req.getSession(false);
        String memberId = null;

        if (session != null) {
            MemberVO member = (MemberVO) session.getAttribute("member");
            if (member != null) {
                memberId = member.getId();
            }
        }

        // 로그인되지 않은 사용자는 글 작성 불가
        if (memberId == null) {
            resp.getWriter().println("<script>alert('로그인이 필요합니다.'); location.href='login.jsp';</script>");
            return;
        }
        System.out.println("memberId = " + memberId);
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        System.out.println("title:" + title + " content:" + content + " memberId:" + memberId);

//        resp.setContentType("text/html;charset=utf-8");
        this.boardServletService.addBoard(new BoardVO(title, content, memberId));
        resp.getWriter().println("<script>alert('글 작성 성공'); location.href='board.html';</script>");

    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
