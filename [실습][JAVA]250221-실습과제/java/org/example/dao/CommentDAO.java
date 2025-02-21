package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.vo.CommentVO;

import java.util.List;

public class CommentDAO {
    private SqlSession sqlSession;

    public CommentDAO() {}

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<CommentVO> selectCommentByBoardId(int boardId) {
        return sqlSession.selectList("comment.selectCommentByBoardId", boardId);
    }

    public int insertComment(CommentVO commentVO) {
        return sqlSession.insert("comment.insert", commentVO);
    }
    public int updateComment(CommentVO commentVO) {
        return sqlSession.update("comment.update", commentVO);
    }
    public int deleteComment(int comment_id) {
        return sqlSession.delete("comment.delete", comment_id);
    }
}
