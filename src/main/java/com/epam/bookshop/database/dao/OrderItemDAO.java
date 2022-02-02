package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.OrderItem;

import java.sql.SQLException;
import java.util.List;


public interface OrderItemDAO {

    Long insert(OrderItem orderItem) throws SQLException;

    List<OrderItem> selectAllByOrderId(Long orderId) throws SQLException;
}
