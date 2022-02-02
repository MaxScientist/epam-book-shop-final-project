package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.AuthorDAO;
import com.epam.bookshop.entity.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    private ConnectionPool connectionPool;
    private Connection connection;


    private static final String INSERT_AUTHOR = "INSERT INTO public.author(first_name, last_name) VALUES(?,?)";
    private static final String UPDATE_AUTHOR = "UPDATE public.author SET first_name =?, last_name = ? WHERE id = ?";
    private static final String SELECT_ALL_AUTHOR = "SELECT * FROM public.author";
    private static final String SELECT_AUTHOR_BY_NAME = "SELECT * FROM public.author WHERE (first_name = ? AND last_name = ?)";
    private static final String SELECT_ALL_AUTHORS_BY_BOOK_ID = "" +
            "SELECT a.id, a.first_name, a.last_name from public.book b" +
            " inner join public.book_to_author bta on b.id = bta.book_id" +
            " inner join public.author a on bta.author_id = a.id where b.id = ?";
//

    private Author getAuthorByResultSet(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));
        return author;
    }


    @Override
    public Long insert(Author author) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Long generatedId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
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
    public void update(Author author) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Author> selectAll() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Author> authorList = new ArrayList<>();
        Author author;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHOR)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    author = getAuthorByResultSet(resultSet);
                    authorList.add(author);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return authorList;
    }

    @Override
    public boolean isAuthorExists(Author author) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        boolean isExists;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExists = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }

    @Override
    public List<Author> selectByBookId(Long id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Author> authorList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_AUTHORS_BY_BOOK_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Author author = getAuthorByResultSet(resultSet);
                    authorList.add(author);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return authorList;
    }

    @Override
    public Long selectAuthorByName(Author author) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Long authorId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_NAME)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    authorId = resultSet.getLong(1);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return authorId;
    }
}
