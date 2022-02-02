package com.epam.bookshop.entity;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Objects;

public class Edition {

    private Long id;
    private Long bookId;
    private String isbn;
    private Long pubId;
    private String binding;
    private Integer pages;
    private BigInteger price;
    private String description;
    private Date releaseDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getPubId() {
        return pubId;
    }

    public void setPubId(Long pubId) {
        this.pubId = pubId;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public BigInteger getPrice() {
        return price;
    }

    public void setPrice(BigInteger price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edition edition = (Edition) o;
        return Objects.equals(id, edition.id) && Objects.equals(bookId, edition.bookId) && Objects.equals(isbn, edition.isbn) && Objects.equals(pubId, edition.pubId) && Objects.equals(binding, edition.binding) && Objects.equals(pages, edition.pages) && Objects.equals(price, edition.price) && Objects.equals(description, edition.description) && Objects.equals(releaseDate, edition.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, isbn, pubId, binding, pages, price, description, releaseDate);
    }

    @Override
    public String toString() {
        return "Edition{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", isbn='" + isbn + '\'' +
                ", pubId=" + pubId +
                ", format='" + binding + '\'' +
                ", pages=" + pages +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
