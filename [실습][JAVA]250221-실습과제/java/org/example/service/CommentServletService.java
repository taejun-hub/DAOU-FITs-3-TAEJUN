package org.example.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.CommentDAO;
import org.example.vo.CommentVO;

import java.util.List;

public class CommentServletService {
    private CommentDAO commentDAO;
    private SqlSessionFactory sqlSessionFactory;


    public CommentServletService(SqlSessionFactory sqlSessionFactory, CommentDAO commentDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.commentDAO = commentDAO;
    }

    public List<CommentVO> getCommentsByBoardId(int boardId) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.commentDAO.setSqlSession(sqlSession);
        List<CommentVO> comments = null;

        try {
            comments = this.commentDAO.selectCommentByBoardId(boardId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return comments;
    }

    public int addComment(CommentVO comment) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.commentDAO.setSqlSession(sqlSession);
        int result = 0;
        try {
            result = this.commentDAO.insertComment(comment);
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

    public int deleteComment(int comment_id) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.commentDAO.setSqlSession(sqlSession);
        System.out.println("comment_id:" + comment_id);
        int result = 0;
        try {
            result = this.commentDAO.deleteComment(comment_id);
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
}

