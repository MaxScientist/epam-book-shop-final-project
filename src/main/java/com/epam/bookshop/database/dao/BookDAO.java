package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.Book;

import java.sql.SQLException;
import java.util.List;


public interface BookDAO {

    Long insert(Book book) throws SQLException;

    Book selectById(Long bookId) throws SQLException;

    void update(Book book) throws SQLException;

    List<Book> selectAllBooks() throws SQLException;

    List<Book> selectAllByFilter(Integer genreId, Integer localeID) throws SQLException;

    boolean isBookExists(String title) throws SQLException;

    List<Book> selectByTitle(String title) throws SQLException;
}
