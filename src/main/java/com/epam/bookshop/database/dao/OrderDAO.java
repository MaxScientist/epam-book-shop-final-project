package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    Long insert(Order order) throws SQLException;

    List<Order> selectUserOrders(Long userId) throws SQLException;

    List<Order> selectAll() throws SQLException;

    void updateOrderStatus(Long orderId, Integer statusId) throws SQLException;

    Order selectOrderById(Long orderId) throws SQLException;
}
