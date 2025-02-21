package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.vo.BoardVO;

import java.util.List;

public class BoardDAO {

    private SqlSession sqlSession;
    public BoardDAO() {}

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<BoardVO> selectList() {
        return this.sqlSession.selectList("board.selectList");
    }

    public BoardVO selectOne(int board_id) {
        return this.sqlSession.selectOne("board.selectOne", board_id);
    }

    public int create(BoardVO boardVO) {
        return this.sqlSession.insert("board.insert", boardVO);
    }

    public int update(BoardVO boardVO) {
        return this.sqlSession.update("board.update", boardVO);
    }
    public int delete(int board_id) {
        return this.sqlSession.delete("board.delete", board_id);
    }
}
