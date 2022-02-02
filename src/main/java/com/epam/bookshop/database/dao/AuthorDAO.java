package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDAO {

    Long insert(Author author) throws SQLException;

    void update(Author author) throws SQLException;

    List<Author> selectAll() throws SQLException;

    boolean isAuthorExists(Author author) throws SQLException;

    Long selectAuthorByName(Author author) throws SQLException;

    List<Author> selectByBookId(Long id) throws SQLException;

    Author selectById(Long authorId) throws SQLException;
}
