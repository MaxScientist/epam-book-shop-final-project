package com.epam.bookshop.database.dao;

import java.sql.SQLException;

public interface BookToAuthorDAO {

    Long insert(Long bookId, Long authorId) throws SQLException;

    boolean isPairExists(Long bookId, Long authorId) throws SQLException;

    void delete(Long bookId, Long authorId) throws SQLException;
}
