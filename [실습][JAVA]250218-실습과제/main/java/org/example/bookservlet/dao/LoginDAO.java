package org.example.bookservlet.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.bookservlet.vo.MemberVO;

public class LoginDAO {
    private SqlSession sqlSession;

    public LoginDAO() {}

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public MemberVO selectUser(String id) {
        return sqlSession.selectOne("bookmybatis.member.selectMember", id);
    }


}
