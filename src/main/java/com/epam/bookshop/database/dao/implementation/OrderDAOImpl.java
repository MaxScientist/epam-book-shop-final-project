package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.OrderDAO;
import com.epam.bookshop.entity.Order;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String SELECT_ALL_ORDER = "SELECT * FROM public.order";
    private static final String INSERT_ORDER = "INSERT INTO public.order(user_id, order_status_id, total_price, created_date)" +
            " VALUES(?, ?, ?, ?)";
    private static final String UPDATE_ORDER_STATUS_ID = "UPDATE public.order SET order_status_id = ? WHERE id = ?";
    private static final String SELECT_ORDERS_BY_USER_ID = "SELECT * FROM public.order WHERE user_id = ?";
    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM public.order WHERE id = ?";

    private Order getOrderByResultSet(ResultSet resultSet) throws SQLException{
        Order order = new Order();
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("user_id"));
        order.setOrderStatusId(resultSet.getInt("order_status_id"));
        order.setTotalPrice(BigInteger.valueOf(resultSet.getLong("total_price")));
        order.setCreatedDate(resultSet.getDate("created_date"));
        return order;
    }

    @Override
    public Long insert(Order order) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Long generatedId = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setInt(2, order.getOrderStatusId());
            preparedStatement.setLong(3, Long.parseLong(String.valueOf(order.getTotalPrice())));
            preparedStatement.setDate(4, (Date) order.getCreatedDate());
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
    public List<Order> selectUserOrders(Long userId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = getOrderByResultSet(resultSet);
                    orderList.add(order);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderList;
    }

    @Override
    public List<Order> selectAll() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Order> orderList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Order order = getOrderByResultSet(resultSet);
                    orderList.add(order);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderList;
    }

    @Override
    public void updateOrderStatus(Long orderId, Integer statusId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_ID)) {
            preparedStatement.setInt(1, statusId);
            preparedStatement.setLong(2, orderId);
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public Order selectOrderById(Long orderId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {
            preparedStatement.setLong(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = getOrderByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return order;
    }
}
