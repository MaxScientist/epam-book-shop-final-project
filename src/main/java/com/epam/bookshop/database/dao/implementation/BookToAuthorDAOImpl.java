package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.BookToAuthorDAO;

import java.sql.*;

public class BookToAuthorDAOImpl implements BookToAuthorDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String INSERT = "INSERT INTO public.book_to_author(book_id, author_id) VALUES(?, ?)";
    private static final String SELECT_BY_BOOK_AUTHOR_ID = "SELECT * FROM public.book_to_author WHERE (book_id = ? AND author_id = ?)";
    private static final String DELETE_BOOK_AUTHOR_ID = "DELETE FROM public.book_to_author WHERE (book_id = ? and author_id=?)";


    @Override
    public Long insert(Long bookId, Long authorId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Long generatedId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, authorId);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getLong(1);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return generatedId;
    }

    @Override
    public boolean isPairExists(Long bookId, Long authorId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        boolean isExists;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_BOOK_AUTHOR_ID)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, authorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExists = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }

    @Override
    public void delete(Long bookId, Long authorId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_AUTHOR_ID)) {
            preparedStatement.setLong(1, bookId);
            preparedStatement.setLong(2, authorId);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
