package com.epam.bookshop.entity;

import java.math.BigInteger;

public class OrderItem {

    private Long id;
    private Long orderId;
    private Book book;
    private BigInteger fixedPrice;
    private Integer quantity;
    private Long bookId;

    public BigInteger getFixedPrice() {
        return fixedPrice;
    }

    public void setFixedPrice(BigInteger fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }
}
