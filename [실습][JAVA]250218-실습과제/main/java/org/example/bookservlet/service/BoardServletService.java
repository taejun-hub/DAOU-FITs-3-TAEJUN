package org.example.bookservlet.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bookservlet.dao.BoardDAO;
import org.example.bookservlet.vo.BoardVO;

import java.util.ArrayList;
import java.util.List;

public class BoardServletService {
    private BoardDAO boardDAO;
    private SqlSessionFactory sqlSessionFactory;


    public BoardServletService(SqlSessionFactory sqlSessionFactory, BoardDAO boardDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.boardDAO = boardDAO;
    }

    public List<BoardVO> getBoardsForMember(String member_id) {
        List<BoardVO> boards = null;
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        System.out.println(member_id);
        this.boardDAO.setSqlSession(sqlSession);

        try {
            boards = this.boardDAO.selectList(member_id);
            System.out.println("boards: " + boards);
            for (BoardVO board : boards) {
                System.out.println(board);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return boards;
    }

    public BoardVO getBoard(int board_id) {
        BoardVO board = null;
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.boardDAO.setSqlSession(sqlSession);

        try {
            board = this.boardDAO.selectOne(board_id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return board;
    }

    public void addBoard(BoardVO board) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.boardDAO.setSqlSession(sqlSession);
        System.out.println("board: " + board);
        try {
            int success = this.boardDAO.create(board);
            if (success > 0) {
                sqlSession.commit();
                System.out.println("글 작성 성공");
            } else {
                System.out.println("글 작성 실패");
                sqlSession.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }
}
