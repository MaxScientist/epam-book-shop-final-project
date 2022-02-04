package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.OrderStatusDAO;
import com.epam.bookshop.entity.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDAOImpl implements OrderStatusDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String SELECT_ORDER_STATUS_BY_ID = "SELECT * FROM public.order_status WHERE (id =? AND language_id = ?)";
    private static final String SELECT_ALL = "SELECT * FROM public.order_status WHERE language_id = ?";


    private OrderStatus getOrderStatusByResultSet(ResultSet resultSet) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(resultSet.getLong("id"));
        orderStatus.setLanguageId(resultSet.getInt("language_id"));
        orderStatus.setName(resultSet.getString("name"));
        return orderStatus;
    }

    @Override
    public OrderStatus selectOrderStatusById(Integer orderStatusId, Integer sessionLanguageId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        OrderStatus orderStatus = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_STATUS_BY_ID)) {
            preparedStatement.setInt(1, orderStatusId);
            preparedStatement.setInt(2, sessionLanguageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    orderStatus = getOrderStatusByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderStatus;
    }

    @Override
    public List<OrderStatus> selectAll(Integer sessionLanguageId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        List<OrderStatus> orderStatusList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            preparedStatement.setInt(1, sessionLanguageId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderStatus orderStatus = getOrderStatusByResultSet(resultSet);
                    orderStatusList.add(orderStatus);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderStatusList;
    }
}
