package com.atoken.cn.android_tcp.provider;

/**
 * Created by Aatoken on 2018/6/12.
 */

public class ProBook {
    public int bookId;
    public String bookName;

    public ProBook(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public ProBook() {
    }

    @Override
    public String toString() {
        return "ProBook{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
