package org.example.vo;

import java.time.LocalDate;
import java.util.Date;

public class BoardVO {
    private int board_id;
    private String title;
    private String content;
    private String member_id;
    private String author;
    private LocalDate created_at;
    private int comment_cnt;
    private int like_cnt;
    private int view_cnt;

    public int getLike_cnt() {
        return like_cnt;
    }

    public void setLike_cnt(int like_cnt) {
        this.like_cnt = like_cnt;
    }

    public BoardVO(int board_id, int like_cnt, String title, int view_cnt,LocalDate created_at, int comment_cnt, String author) {
        this.board_id = board_id;
        this.title = title;
        this.view_cnt = view_cnt;
        this.comment_cnt = comment_cnt;
        this.like_cnt = like_cnt;
        this.author = author;
        this.created_at = created_at;
    }


    public BoardVO(int board_id, String title, String content, String member_id) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.member_id = member_id;
    }


    public BoardVO(String title, String content, String member_id) {
        this.title = title;
        this.content = content;
        this.member_id = member_id;
    }




    public BoardVO(int board_id, String title, String content, String member_id, String author , LocalDate created_at) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.member_id = member_id;
        this.author = author;
        this.created_at = created_at;
    }

    public int getComment_cnt() {
        return comment_cnt;
    }

    public void setComment_cnt(int comment_cnt) {
        this.comment_cnt = comment_cnt;
    }

    public int getView_cnt() {
        return view_cnt;
    }

    public void setView_cnt(int viewCnt) {
        this.view_cnt = viewCnt;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }


    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }
}
