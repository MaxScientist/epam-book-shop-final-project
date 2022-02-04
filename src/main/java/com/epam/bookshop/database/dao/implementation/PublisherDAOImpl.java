package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.PublisherDAO;
import com.epam.bookshop.entity.Publisher;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAOImpl implements PublisherDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private static final String INSERT_PUBLISHER = "INSERT INTO public.publisher(publish_house) VALUES(?)";
    private static final String UPDATE_PUBLISHER = "UPDATE public.publisher SET publish_house = ? WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM public.publisher";
    private static final String SELECT_PUBLISHER_BY_ID = "SELECT * FROM public.publisher WHERE id = ?";
    private static final String SELECT_PUBLISHER_BY_PUBLISH_HOUSE = "SELECT * FROM public.publisher WHERE publish_house = ?";

    private Publisher getPublisherResultSet(ResultSet resultSet) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setId(resultSet.getLong("id"));
        publisher.setPublishHouse(resultSet.getString("publish_house"));
        return publisher;
    }

    @Override
    public List<Publisher> selectAll() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        List<Publisher> publisherList = new ArrayList<>();
        Publisher publisher;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    publisher = getPublisherResultSet(resultSet);
                    publisherList.add(publisher);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisherList;
    }

    @Override
    public Long insert(Publisher publisher) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Long generatedId = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PUBLISHER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, publisher.getPublishHouse());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getLong(1);
                }
            }
        } finally {
            LOGGER.info("New publisher has been added " + publisher);
            connectionPool.returnConnection(connection);
        }
        return generatedId;
    }

    @Override
    public void update(Publisher publisher) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PUBLISHER)) {
            preparedStatement.setString(1, publisher.getPublishHouse());
            preparedStatement.setLong(2, publisher.getId());
            preparedStatement.executeUpdate();
        } finally {
            LOGGER.info("Publisher has been updated " + publisher);
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Publisher selectById(Long id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Publisher publisher = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PUBLISHER_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    publisher = getPublisherResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisher;
    }

    @Override
    public Publisher selectByPublishHouse(String publishHouse) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Publisher publisher = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PUBLISHER_BY_PUBLISH_HOUSE)) {
            preparedStatement.setString(1, publishHouse);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    publisher = getPublisherResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return publisher;
    }
}
