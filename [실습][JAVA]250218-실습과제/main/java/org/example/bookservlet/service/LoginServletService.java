package org.example.bookservlet.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bookservlet.dao.LoginDAO;
import org.example.bookservlet.vo.MemberVO;

public class LoginServletService {
    private SqlSessionFactory sqlSessionFactory;
    private LoginDAO loginDAO;

    public LoginServletService(SqlSessionFactory sqlSessionFactory, LoginDAO loginDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.loginDAO = loginDAO;
    }

    public MemberVO loginUser(String id, String password) {
        MemberVO memberVO = null;
        SqlSession sqlSession = sqlSessionFactory.openSession();
        this.loginDAO.setSqlSession(sqlSession);

        try {
            memberVO = this.loginDAO.selectUser(id);
            if (memberVO != null && memberVO.getPw().equals(password)) {
                System.out.println("login success");
                return memberVO;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return memberVO;
    }
}
