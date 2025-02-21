package org.example.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.BoardDAO;
import org.example.mybatis.MyBatisSessionFactory;
import org.example.service.BoardServletService;
import org.example.vo.BoardVO;
import org.example.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteBoard")
public class DeleteBoardServlet extends HttpServlet {
    private BoardServletService boardServletService;
    @Override
    public void init() throws ServletException {
        super.init();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.boardServletService = new BoardServletService(sqlSessionFactory, new BoardDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("delete doPost called");
        HttpSession session = req.getSession(false);
        MemberVO member = (MemberVO) session.getAttribute("member");
        req.setCharacterEncoding("utf-8");
        String board_id = req.getParameter("id");
        String member_id = member.getId();

        this.boardServletService.deleteBoard(Integer.parseInt(board_id), member_id);
        resp.sendRedirect("boardServlet");
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
