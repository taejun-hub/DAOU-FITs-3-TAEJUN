package org.example.bookservlet.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.bookservlet.dao.BoardDAO;
import org.example.bookservlet.dao.LoginDAO;
import org.example.bookservlet.mybatis.MyBatisSessionFactory;
import org.example.bookservlet.service.BoardServletService;
import org.example.bookservlet.service.LoginServletService;
import org.example.bookservlet.vo.BoardVO;
import org.example.bookservlet.vo.MemberVO;

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

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private LoginServletService loginServletService;
    private BoardServletService boardServletService;

    @Override
    public void init() throws ServletException {
        super.init();

        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.loginServletService = new LoginServletService(sqlSessionFactory, new LoginDAO());
        this.boardServletService = new BoardServletService(sqlSessionFactory, new BoardDAO());
        System.out.println("loginServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        resp.setContentType("text/html;charset=UTF-8");

        MemberVO memberVO = this.loginServletService.loginUser(id, pw);
        if (memberVO != null && memberVO.getPw().equals(pw)) {
            // 로그인 성공
            HttpSession session = req.getSession();
            session.setAttribute("member", memberVO);
            List<BoardVO> boardList = this.boardServletService.getBoardsForMember(memberVO.getId());
            for (BoardVO boardVO : boardList) {
                System.out.println(boardVO.getTitle() + "\t" + boardVO.getContent());
            }

            // JSP를 이용해서 View 처리
            RequestDispatcher rd = req.getRequestDispatcher("welcome.jsp");
            req.setAttribute("member", memberVO);
            req.setAttribute("boardList", boardList);
            rd.forward(req, resp);

            // JDP를
            resp.sendRedirect("search.html");
        } else {
            // 로그인 실패
            resp.sendRedirect("loginError.html");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}

