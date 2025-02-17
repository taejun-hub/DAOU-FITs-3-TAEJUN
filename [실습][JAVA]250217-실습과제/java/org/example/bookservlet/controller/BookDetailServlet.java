package org.example.bookservlet.controller;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/bookDetails")
public class BookDetailServlet extends HttpServlet {

    public BookDetailServlet() {
        System.out.println("constructor called");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // 초기화 코드가 들어온다.

        System.out.println("init called");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet called");

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String isbn = req.getParameter("isbn");
        String author = req.getParameter("author");
        int price = Integer.parseInt(req.getParameter("price"));

        resp.setContentType("text/html;charset=UTF-8");

        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>책 세부 정보</title></head><body>");
        out.println("<h1>" + title + "</h1>");
        out.println("<p>" + isbn + "</p>");
        out.println("<p>" + price + "</p>");
        out.println("<p>" + author+ "</p>");
        out.println("</body></html>");
        out.flush();
        out.close();

    }

    @Override
    public void destroy() {
        // resource 해제
        super.destroy();
        System.out.println("destroy called");

    }
}
