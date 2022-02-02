package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.OrderStatus;

import java.sql.SQLException;
import java.util.List;

public interface OrderStatusDAO {

    OrderStatus selectOrderStatusById(Integer orderStatusId,
                                      Integer sessionLanguageId) throws SQLException;

    List<OrderStatus> selectAll(Integer sessionLanguageId) throws SQLException;
}
