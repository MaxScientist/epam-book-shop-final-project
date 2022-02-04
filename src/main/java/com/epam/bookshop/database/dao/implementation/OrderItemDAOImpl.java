package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.OrderItemDAO;
import com.epam.bookshop.entity.OrderItem;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private static final String INSERT_ORDER_ITEM = "INSERT INTO public.order_item(order_id, book_id, fixed_price, quantity)" +
            "VALUES(?,?,?,?)";
    private static final String SELECT_ALL_ORDER_ITEM_BY_ORDER_ID = "" +
            "SELECT * FROM public.order_item WHERE order_id = ?";


    private OrderItem getOrderItemByResultSet(ResultSet resultSet) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setOrderId(resultSet.getLong("order_id"));
        orderItem.setBookId(resultSet.getLong("book_id"));
        orderItem.setFixedPrice(BigInteger.valueOf(resultSet.getLong("fixed_price")));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        return orderItem;
    }
    @Override
    public Long insert(OrderItem orderItem) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Long generatedId = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, orderItem.getOrderId());
            preparedStatement.setLong(2, orderItem.getBookId());
            preparedStatement.setLong(3, Long.parseLong(String.valueOf(orderItem.getFixedPrice())));
            preparedStatement.setInt(4, orderItem.getQuantity());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getLong(1);
                }
            }
        } finally {
            LOGGER.info("New order item has been added " + orderItem);
            connectionPool.returnConnection(connection);
        }
        return generatedId;
    }

    @Override
    public List<OrderItem> selectAllByOrderId(Long orderId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<OrderItem> orderItemList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDER_ITEM_BY_ORDER_ID)) {
            preparedStatement.setLong(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderItem orderItem = getOrderItemByResultSet(resultSet);
                    orderItemList.add(orderItem);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return orderItemList;
    }
}
