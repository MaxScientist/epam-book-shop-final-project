package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.Edition;

import java.sql.SQLException;
import java.util.List;

public interface EditionDAO {

    List<Edition> selectAll() throws SQLException;

    Long insert(Edition edition) throws SQLException;

    void update(Edition edition) throws SQLException;

    Edition selectByISBN(String isbn) throws SQLException;

    Edition selectByBookId(Long bookId) throws SQLException;
}
