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

@WebServlet("/updateBoard")
public class UpdateBoardServlet extends HttpServlet {
    private BoardServletService boardServletService;
    @Override
    public void init() throws ServletException {
        super.init();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.boardServletService = new BoardServletService(sqlSessionFactory, new BoardDAO());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession(false);
        MemberVO member = (MemberVO) session.getAttribute("member");
        String board_id = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String member_id = member.getId();
        BoardVO boardVO = new BoardVO(Integer.parseInt(board_id), title, content, member_id);

        int success = this.boardServletService.updateBoard(boardVO);
        System.out.println(success);
        resp.sendRedirect("boardDetailServlet?id=" + board_id);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
