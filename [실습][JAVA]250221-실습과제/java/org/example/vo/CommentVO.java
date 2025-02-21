package org.example.vo;

import java.time.LocalDate;

public class CommentVO {
    private int comment_id;
    private int board_id;
    private String user_id;
    private String author;
    private String content;
    private LocalDate created_at;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public CommentVO(int comment_id, int board_id, String content, String user_id, String author, LocalDate created_at) {
        this.comment_id = comment_id;
        this.board_id = board_id;
        this.content = content;
        this.user_id = user_id;
        this.author = author;
        this.created_at = created_at;
    }


    public CommentVO(String content, int board_id, String user_id) {
        this.board_id = board_id;
        this.content = content;
        this.user_id = user_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
