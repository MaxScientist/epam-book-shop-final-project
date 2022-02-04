package com.epam.bookshop.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book {

    private Long id;
    private String title;
    private Integer accessStatusId;
    private Long genreId;
    private String bookImage;
    private Integer languageId;
    private List<Author> authors;
    private Genre genre;
    private BigInteger bookPrice;
    private String description;
    private Publisher publisher;
    private String isbn;
    private Integer pages;
    private String binding;
    private Date releaseDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAccessStatusId() {
        return accessStatusId;
    }

    public void setAccessStatusId(Integer accessStatusId) {
        this.accessStatusId = accessStatusId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Genre getGenre() {
        return genre;
    }

    public BigInteger getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(BigInteger bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
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
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(accessStatusId, book.accessStatusId) && Objects.equals(genreId, book.genreId) && Objects.equals(bookImage, book.bookImage) && Objects.equals(languageId, book.languageId) && Objects.equals(authors, book.authors) && Objects.equals(genre, book.genre) && Objects.equals(bookPrice, book.bookPrice) && Objects.equals(description, book.description) && Objects.equals(publisher, book.publisher) && Objects.equals(isbn, book.isbn) && Objects.equals(pages, book.pages) && Objects.equals(binding, book.binding) && Objects.equals(releaseDate, book.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, accessStatusId, genreId, bookImage, languageId, authors, genre, bookPrice, description, publisher, isbn, pages, binding, releaseDate);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", accessStatusId=" + accessStatusId +
                ", genreId=" + genreId +
                ", bookImage='" + bookImage + '\'' +
                ", languageId=" + languageId +
                ", authors=" + authors +
                ", genre=" + genre +
                ", bookPrice=" + bookPrice +
                ", description='" + description + '\'' +
                ", publisher=" + publisher +
                ", isbn='" + isbn + '\'' +
                ", pages=" + pages +
                ", binding='" + binding + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
