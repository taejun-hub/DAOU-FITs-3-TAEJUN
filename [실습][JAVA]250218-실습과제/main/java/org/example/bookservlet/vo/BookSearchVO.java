package org.example.bookservlet.vo;

public class BookSearchVO {

    private String keyword;
    private int price;



    public BookSearchVO() {}
    public BookSearchVO(String keyword, int price) {
        this.keyword = keyword;
        this.price = price;
    }


}
