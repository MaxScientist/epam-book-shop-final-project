package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.AccessStatusDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessStatusDAOImpl implements AccessStatusDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String SELECT_VOLUME_BY_ID = "SELECT * FROM access_status WHERE id = ?";


    @Override
    public boolean isAccessStatusExists(Integer statusId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        boolean isExists;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VOLUME_BY_ID)) {
            preparedStatement.setLong(1, statusId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExists = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }
}
