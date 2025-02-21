package org.example.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import org.example.dao.LikeDAO;
import org.example.vo.LikeVO;


public class LikeServletService {
    private LikeDAO likeDAO;
    private SqlSessionFactory sqlSessionFactory;


    public LikeServletService(SqlSessionFactory sqlSessionFactory, LikeDAO likeDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.likeDAO = likeDAO;
    }


    public int likeBoard(LikeVO likeVO) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        System.out.println("likeBoard service");
        System.out.println("boardID: " + likeVO.getBoard_id());
        System.out.println("likeID: " + likeVO.getLike_id());
        this.likeDAO.setSqlSession(sqlSession);
        int result = 0;
        try {
            result = this.likeDAO.create(likeVO);
            if (result > 0) {
                sqlSession.commit();
            } else {
                sqlSession.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public int unlikeBoard(LikeVO likeVO) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.likeDAO.setSqlSession(sqlSession);

        int result = 0;
        try {
            result = this.likeDAO.delete(likeVO);
            if (result > 0) {
                sqlSession.commit();
            } else {
                sqlSession.rollback();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public boolean hasUserLikedBoard(LikeVO likeVO) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.likeDAO.setSqlSession(sqlSession);

        System.out.println("memberID: " + likeVO.getMember_id());
        System.out.println("boardID: " + likeVO.getBoard_id());

        LikeVO result = null;
        try {
            result = this.likeDAO.getLike(likeVO);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result != null;
    }


}
