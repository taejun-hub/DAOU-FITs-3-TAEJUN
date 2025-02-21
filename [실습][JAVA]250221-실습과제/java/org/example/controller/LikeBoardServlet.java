package org.example.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.LikeDAO;
import org.example.mybatis.MyBatisSessionFactory;
import org.example.service.LikeServletService;
import org.example.vo.CommentVO;
import org.example.vo.LikeVO;
import org.example.vo.MemberVO;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/likeBoard")
public class LikeBoardServlet extends HttpServlet {
    private LikeServletService likeServletService;

    @Override
    public void init() throws ServletException {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.likeServletService = new LikeServletService(sqlSessionFactory, new LikeDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("LikeBoardServlet doPost called");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        JSONObject jsonResponse = new JSONObject();

        // 세션에서 로그인된 사용자 정보 가져오기
        HttpSession session = req.getSession(false);
        String memberId = null;

        if (session != null) {
            MemberVO member = (MemberVO) session.getAttribute("member");
            if (member != null) {
                memberId = member.getId();
            }
        }
        System.out.println("memberId: " + memberId);
        if (memberId == null) {
            resp.getWriter().println("<script>alert('로그인이 필요합니다.'); location.href='login.jsp';</script>");
            return;
        }
        String board_id = req.getParameter("board_id");
        System.out.println("board_id: " + board_id);

        int success = this.likeServletService.likeBoard(new LikeVO(memberId, Integer.parseInt(board_id)));
        if (success > 0) {
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "좋아요 성공");
        } else {
            jsonResponse.put("status", "fail");
        }
        out.println(jsonResponse);
        jsonResponse.put("message", "서버 오류 발생");
        out.flush();

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
