package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.LanguageDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageDAOImpl implements LanguageDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String SELECT_ID_BY_NAME = "SELECT id FROM public.language WHERE name = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM public.language WHERE id = ?";


    @Override
    public Integer selectIdByName(String name) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Integer id = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ID_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    id = resultSet.getInt("id");
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return id;
    }

    @Override
    public boolean isLanguageExists(Integer id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        boolean isExists;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExists = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }
}
