package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.EditionDAO;
import com.epam.bookshop.entity.Edition;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EditionDAOImpl implements EditionDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String SELECT_ALL = "SELECT * FROM public.edition";
    private static final String INSERT_EDITION = "INSERT INTO public.edition ( isbn, binding, pages," +
            "price, description, release_date, book_id, pub_id) VALUES(?,?,?,?,?,?,?,?)";
    private static final String UPDATE_EDITION = "UPDATE public.edition SET  isbn = ?, binding = ?," +
            "pages = ?, price = ?, description = ?, release_date = ?, pub_id = ? WHERE id = ?";
    private static final String SELECT_BY_ISBN = "SELECT * FROM public.edition WHERE isbn = ?";
    private static final String SELECT_EDITION_BY_BOOK_ID = "" +
            "select e.id, e.isbn, e.binding, e.pages, e.price, e.description, e.release_date, e.book_id, e.pub_id " +
            "from public.book b inner join public.edition e on b.id = e.book_id where b.id = ?";

    private Edition getEditionByResultSet(ResultSet resultSet) throws SQLException {
        Edition edition = new Edition();
        edition.setId(resultSet.getLong("id"));
        edition.setIsbn(resultSet.getString("isbn"));
        edition.setBinding(resultSet.getString("binding"));
        edition.setPages(resultSet.getInt("pages"));
        edition.setPrice(BigInteger.valueOf((resultSet.getLong("price"))));
        edition.setDescription(resultSet.getString("description"));
        edition.setReleaseDate(resultSet.getDate("release_date"));
        edition.setBookId(resultSet.getLong("book_id"));
        edition.setPubId(resultSet.getLong("pub_id"));

        return edition;
    }

    @Override
    public List<Edition> selectAll() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        List<Edition> editionList = new ArrayList<>();
        Edition edition;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    edition = getEditionByResultSet(resultSet);
                    editionList.add(edition);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }


        return editionList;
    }

    @Override
    public Long insert(Edition edition) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Long generatedId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EDITION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, edition.getIsbn());
            preparedStatement.setString(2, edition.getBinding());
            preparedStatement.setInt(3, edition.getPages());
            preparedStatement.setLong(4, Long.parseLong(String.valueOf((edition.getPrice()))));
            preparedStatement.setString(5, edition.getDescription());
            preparedStatement.setDate(6, edition.getReleaseDate());
            preparedStatement.setLong(7, edition.getBookId());
            preparedStatement.setLong(8, edition.getPubId());
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
    public void update(Edition edition) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EDITION)) {
            preparedStatement.setString(1, edition.getIsbn());
            preparedStatement.setString(2, edition.getBinding());
            preparedStatement.setInt(3, edition.getPages());
            preparedStatement.setLong(4, Long.parseLong(String.valueOf((edition.getPrice()))));
            preparedStatement.setString(5, edition.getDescription());
            preparedStatement.setDate(6, edition.getReleaseDate());
            preparedStatement.setLong(7, edition.getPubId());
            preparedStatement.setLong(8, edition.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Edition selectByISBN(String isbn) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Edition edition = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ISBN)) {
            preparedStatement.setString(1, isbn);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    edition = getEditionByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return edition;
    }

    @Override
    public Edition selectByBookId(Long bookId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Edition edition = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EDITION_BY_BOOK_ID)) {
            preparedStatement.setLong(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    edition = getEditionByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return edition;
    }
}
