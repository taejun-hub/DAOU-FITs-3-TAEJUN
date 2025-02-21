package org.example.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.BoardDAO;
import org.example.dao.LoginDAO;
import org.example.mybatis.MyBatisSessionFactory;
import org.example.service.BoardServletService;
import org.example.service.LoginServletService;
import org.example.vo.BoardVO;
import org.example.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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

            // JSP를 이용해서 View 처리
            RequestDispatcher rd = req.getRequestDispatcher("welcome.jsp");
            req.setAttribute("member", memberVO);
            rd.forward(req, resp);

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

