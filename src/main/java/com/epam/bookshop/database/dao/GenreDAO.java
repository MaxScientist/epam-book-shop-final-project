package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.Genre;

import java.sql.SQLException;
import java.util.List;

public interface GenreDAO {

    void insert(List<Genre> genre) throws SQLException;

    Genre selectGenreByBookLanguageId(Long bookId, Integer sessionLanguageId) throws SQLException;

    Genre selectById(Integer genreId) throws SQLException;

    List<Genre> selectGenresByLanguageId(Integer sessionLanguageId) throws SQLException;

    List<Genre> selectAll() throws SQLException;

    void update(List<Genre> genres) throws SQLException;
}
