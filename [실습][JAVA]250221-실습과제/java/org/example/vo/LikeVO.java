package org.example.vo;

import java.time.LocalDate;

public class LikeVO {
    private int like_id;
    private String member_id;
    private int board_id;
    private LocalDate created_at;


    public LikeVO(String member_id, int board_id) {
        this.member_id = member_id;
        this.board_id = board_id;
    }

    public int getLike_id() {
        return like_id;
    }

    public void setLike_id(int like_id) {
        this.like_id = like_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
