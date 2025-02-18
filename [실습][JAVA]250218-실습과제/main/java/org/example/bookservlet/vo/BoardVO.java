package org.example.bookservlet.vo;

public class BoardVO {
    private int board_id;
    private String title;
    private String content;
    private String member_id;
    private String author;


    public BoardVO(int board_id, String title, String content, String member_id, String author) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.member_id = member_id;
        this.author = author;
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
