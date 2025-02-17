package org.example.bookservlet.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bookservlet.dao.BookDAO;
import org.example.bookservlet.mybatis.MyBatisSessionFactory;
import org.example.bookservlet.service.BookServletService;
import org.example.bookservlet.vo.BookVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search")
public class BookServlet extends HttpServlet {
    private BookServletService bookServletService;

    public BookServlet() {
        System.out.println("constructor called");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // 초기화 코드가 들어온다.
        SqlSessionFactory sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
        BookDAO bookDAO = new BookDAO();
        this.bookServletService = new BookServletService(sqlSessionFactory, bookDAO);
        System.out.println("init called");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet called");

        // Servlet 이 클라이언트에게 처리된 결과를 돌려주려면
        // 1. 데이터 형태 지정
        resp.setContentType("text/html;charset=UTF-8");

        // 2. 결과를 돌려주기 위핸 데이터 통로를 연다. (PrintWriter 이용)
        PrintWriter out = resp.getWriter();

        // 3. 통로를 통해 데이터 전달
        out.println("<!DOCTYPE html>");
        out.println("<html lang='ko'>");
        out.println("<head>");
        out.println("<title>도서 검색 프로그램</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>도서 검색 프로그램</h1>");
        out.println("<label for='bookTitle'>도서명 키워드</label>");
        out.println("<input type='text' id='bookTitle' placeholder='도서명을 입력하세요'>");
        out.println("<p>가격 필터</p>");
        out.println("<input type='radio' name='priceFilter' id='price1' value='10000'>");
        out.println("<label for='price1'>10,000원 미만</label><br>");
        out.println("<input type='radio' name='priceFilter' id='price2' value='20000'>");
        out.println("<label for='price2'>20,000원 미만</label><br>");
        out.println("<input type='radio' name='priceFilter' id='price3' value='30000'>");
        out.println("<label for='price3'>30,000원 미만</label><br><br>");
        out.println("<input type='radio' name='priceFilter' id='price4' value='40000'>");
        out.println("<label for='price3'>40,000원 미만</label><br><br>");
        out.println("<button>도서 검색</button>");
        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 클라이언트의 요청이 POST 방식일 때 호출
        System.out.println("doPost called");

        // 입력을 받을 때 한글과 같은 유니코드가 포함되어 있다면 encoding 필요
        req.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("keyword");
        int priceFilter = Integer.parseInt(req.getParameter("priceFilter"));
        List<BookVO> bookVOList = this.bookServletService.searchBookByKeywordAndPrice(keyword, priceFilter);


        // 1. 데이터 형태 지정
        resp.setContentType("text/html;charset=UTF-8");

        // 2. 결과를 돌려주기 위핸 데이터 통로를 연다. (PrintWriter 이용)
        PrintWriter out = resp.getWriter();

        // 3. 통로를 통해 데이터 전달
        out.println("<!DOCTYPE html>");
        out.println("<html lang='ko'>");
        out.println("<head>");
        out.println("<title>도서 검색 프로그램</title>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>검색결과입니다.</h2>");
        out.println("<h3>검색 키워드: " + keyword + "</h3>");
        out.println("<h3>검색 가격:"  + priceFilter + "</h3>");
        out.println("<ul>");
        for (BookVO bookVO : bookVOList) {
            System.out.println(bookVO.getBisbn());
            System.out.println(bookVO.getBauthor());
            out.println("<li><a href='bookDetails?isbn=" + bookVO.getBisbn() +
                    "&title=" + bookVO.getBtitle()  +
                    "&price=" + bookVO.getBprice() +
                    "&author=" + bookVO.getBauthor() + "'>" +
                    bookVO.getBtitle() + ", " +
                    bookVO.getBprice() +
                    "</a></li>");

        }
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
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
