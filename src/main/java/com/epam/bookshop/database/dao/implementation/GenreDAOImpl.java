package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.GenreDAO;
import com.epam.bookshop.entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDAOImpl implements GenreDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String SELECT_ALL_GENRES = "SELECT * FROM public.genre";
    private static final String SELECT_ALL_GENRES_BY_LANGUAGE_ID = "SELECT * FROM public.genre WHERE language_id = ?";
    private static final String INSERT_GENRE = "INSERT INTO public.genre(id, language_id, name) VALUES(?, ?, ?)";
    private static final String UPDATE_GENRE = "UPDATE public.genre SET name = ? WHERE (id = ? AND language_id = ?)";
    private static final String SELECT_GENRE_BY_ID = "SELECT * FROM public.genre WHERE id = ?";

    private static final String SELECT_GENRE_BY_BOOK_LANGUAGE_ID =
            "select g.id, g.language_id, g.name from public.book b inner join public.genre g on b.genre_id = g.id where b.id = ? AND g.language_id=?";


    private Genre getGenreByResultSet(ResultSet resultSet) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("id"));
        genre.setLanguageId(resultSet.getInt("language_id"));
        genre.setName(resultSet.getString("name"));
        return genre;
    }

    @Override
    public void insert(List<Genre> genres) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        connection.setAutoCommit(false);
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GENRE)) {
                for (Genre genre : genres) {
                    preparedStatement.setInt(1, genre.getId());
                    preparedStatement.setInt(2, genre.getLanguageId());
                    preparedStatement.setString(3, genre.getName());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Genre selectGenreByBookLanguageId(Long bookId, Integer sessionLanguageId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Genre genre = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENRE_BY_BOOK_LANGUAGE_ID)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setInt(2, sessionLanguageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    genre = getGenreByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genre;
    }

    @Override
    public Genre selectById(Integer genreId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Genre genre = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GENRE_BY_ID)) {
            preparedStatement.setInt(1, genreId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    genre = getGenreByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genre;
    }

    @Override
    public List<Genre> selectGenresByLanguageId(Integer sessionLanguageId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Genre> genreList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GENRES_BY_LANGUAGE_ID)) {
            preparedStatement.setInt(1, sessionLanguageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Genre genre = getGenreByResultSet(resultSet);
                    genreList.add(genre);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genreList;
    }

    @Override
    public List<Genre> selectAll() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Genre> genreList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_GENRES)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Genre genre = getGenreByResultSet(resultSet);
                    genreList.add(genre);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return genreList;
    }

    @Override
    public void update(List<Genre> genres) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        connection.setAutoCommit(false);
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_GENRE)) {
                for (Genre genre : genres) {
                    preparedStatement.setString(1, genre.getName());
                    preparedStatement.setInt(2, genre.getId());
                    preparedStatement.setInt(3, genre.getLanguageId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
