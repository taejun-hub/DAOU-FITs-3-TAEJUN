package org.example.bookservlet.vo;

public class MemberVO {
    private String id;
    private String pw;
    private String name;


    public MemberVO() {}
    public MemberVO(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }
}
