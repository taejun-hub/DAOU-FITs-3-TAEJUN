package org.example.dao;

import org.apache.ibatis.session.SqlSession;
import org.example.vo.LikeVO;


public class LikeDAO {
    private SqlSession sqlSession;
    public LikeDAO() {}

    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public LikeVO getLike(LikeVO likeVO) {
        return sqlSession.selectOne("like.selectOne", likeVO);
    }
    public int create(LikeVO likeVO) {
        return this.sqlSession.insert("like.insert", likeVO);
    }

    public int delete(LikeVO likeVO) {
        return this.sqlSession.delete("like.delete", likeVO);
    }
}
