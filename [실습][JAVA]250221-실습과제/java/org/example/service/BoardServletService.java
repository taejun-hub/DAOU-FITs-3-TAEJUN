package org.example.service;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.dao.BoardDAO;
import org.example.vo.BoardVO;

import java.util.List;
import java.util.Objects;

public class BoardServletService {
    private BoardDAO boardDAO;
    private SqlSessionFactory sqlSessionFactory;


    public BoardServletService(SqlSessionFactory sqlSessionFactory, BoardDAO boardDAO) {
        this.sqlSessionFactory = sqlSessionFactory;
        this.boardDAO = boardDAO;
    }

    public List<BoardVO> getBoards() {
        List<BoardVO> boards = null;
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.boardDAO.setSqlSession(sqlSession);

        try {
            boards = this.boardDAO.selectList();

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

    public int updateBoard(BoardVO boardVO) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.boardDAO.setSqlSession(sqlSession);
        int success = 0;
        try {
            int board_id = boardVO.getBoard_id();
            if (Objects.equals(this.boardDAO.selectOne(board_id).getMember_id(), boardVO.getMember_id())) {
                success = this.boardDAO.update(boardVO);
                if (success > 0) {
                    sqlSession.commit();
                    System.out.println("글 수정 성공");
                } else {
                    System.out.println("글 삭제 실패");
                    sqlSession.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return success;
    }

    public int deleteBoard(int board_id, String member_id) {
        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        this.boardDAO.setSqlSession(sqlSession);
        System.out.println("board: " + board_id + "member_id: " + member_id);
        int success = 0;
        try {
            BoardVO boardVO = this.boardDAO.selectOne(board_id);
            System.out.println(boardVO.getMember_id());
            if (Objects.equals(boardVO.getMember_id(), member_id)) {
                success = this.boardDAO.delete(board_id);
                if (success > 0) {
                    sqlSession.commit();
                    System.out.println("글 삭제 성공");
                } else {
                    System.out.println("글 삭제 실패");
                    sqlSession.rollback();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return success;
    }
}
