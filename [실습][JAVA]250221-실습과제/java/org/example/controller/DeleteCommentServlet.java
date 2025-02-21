package org.example.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.CommentDAO;
import org.example.mybatis.MyBatisSessionFactory;
import org.example.service.CommentServletService;
import org.example.vo.CommentVO;
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

@WebServlet("/deleteComment")
public class DeleteCommentServlet extends HttpServlet {
    CommentServletService commentServletService;

    @Override
    public void init() throws ServletException {
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.commentServletService = new CommentServletService(sqlSessionFactory, new CommentDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");  // 🔹 JSON 응답 설정
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

        // 로그인되지 않은 사용자는 글 작성 불가
        if (memberId == null) {
            resp.getWriter().println("<script>alert('로그인이 필요합니다.'); location.href='login.jsp';</script>");
            return;
        }
        String comment_id = req.getParameter("comment_id");

        int success = this.commentServletService.deleteComment(Integer.parseInt(comment_id));
        if (success > 0) {
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "댓글이 삭제되었습니다.");
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
