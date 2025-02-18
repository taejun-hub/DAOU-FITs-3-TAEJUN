package org.example.bookservlet.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.bookservlet.vo.BoardVO;

import java.util.List;

public class BoardDAO {

    private SqlSession sqlSession;
    public BoardDAO() {}

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<BoardVO> selectList(String member_id) {
        return this.sqlSession.selectList("bookmybatis.board.selectList", member_id);
    }

    public BoardVO selectOne(int board_id) {
        return this.sqlSession.selectOne("bookmybatis.board.selectOne", board_id);
    }

    public int create(BoardVO boardVO) {
        return this.sqlSession.insert("bookmybatis.board.insert", boardVO);
    }
}
