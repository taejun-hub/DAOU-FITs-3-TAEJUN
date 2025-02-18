package org.example.bookservlet.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bookservlet.dao.BoardDAO;
import org.example.bookservlet.mybatis.MyBatisSessionFactory;
import org.example.bookservlet.service.BoardServletService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BoardDetailServlet extends HttpServlet {

    private BoardServletService boardServletService;

    @Override
    public void init() throws ServletException {
        super.init();
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        this.boardServletService = new BoardServletService(sqlSessionFactory, new BoardDAO());
        System.out.println("boardDetailServlet init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
