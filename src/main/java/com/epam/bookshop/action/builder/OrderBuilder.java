package com.epam.bookshop.action.builder;

import com.epam.bookshop.database.dao.OrderDAO;
import com.epam.bookshop.database.dao.OrderStatusDAO;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.database.dao.implementation.OrderDAOImpl;
import com.epam.bookshop.database.dao.implementation.OrderStatusDAOImpl;
import com.epam.bookshop.database.dao.implementation.UserDAOImpl;
import com.epam.bookshop.entity.Order;
import com.epam.bookshop.entity.OrderItem;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static com.epam.bookshop.constants.ParameterConstants.ZERO_AMOUNT;
import static com.epam.bookshop.constants.ParameterConstants.ORDER_STATUS_IN_PROCESSING;


public class OrderBuilder {

    private static OrderBuilder instance = new OrderBuilder();

    private final OrderItemBuilder orderItemBuilder = OrderItemBuilder.getInstance();
    private final OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final OrderDAO orderDAO = new OrderDAOImpl();

    private OrderBuilder() {
    }

    public static OrderBuilder getInstance() {
        if (instance == null) {
            instance = new OrderBuilder();
        }
        return instance;
    }

    public Order fillNew(Long userId, List<Long> cartItemIds) throws SQLException {
        Date createDate = Date.valueOf(LocalDate.now());
        List<OrderItem> orderItems = orderItemBuilder.fillNewOrderItems(cartItemIds);

        Order order = new Order();
        order.setUserId(userId);
        order.setOrderStatusId(ORDER_STATUS_IN_PROCESSING);
        order.setTotalPrice(calculateTotalPrice(orderItems));
        order.setCreatedDate(createDate);
        order.setOrderItems(orderItems);
        return order;
    }

    private BigInteger calculateTotalPrice(List<OrderItem> orderItems) {
        BigInteger totalPrice = new BigInteger(ZERO_AMOUNT);
        for (OrderItem orderItem : orderItems) {
            totalPrice = totalPrice.add(orderItem.getFixedPrice().multiply(new BigInteger(String.valueOf(orderItem.getQuantity()))));
        }
        return totalPrice;
    }

    public List<Order> fillUserOrders(Long userId, Integer localeId) throws SQLException {
        List<Order> orderList = orderDAO.selectUserOrders(userId);
        for (Order order : orderList) {
            order.setOrderItems(orderItemBuilder.fillOrderItems(order.getId(), localeId));
            order.setStatus(orderStatusDAO.selectOrderStatusById(order.getOrderStatusId(), localeId));
        }
        return orderList;
    }

    public List<Order> fillAllOrders(Integer localeId) throws SQLException {
        List<Order> orderList = orderDAO.selectAll();
        for (Order order : orderList) {
            order.setOrderItems(orderItemBuilder.fillOrderItems(order.getId(), localeId));
            order.setStatus(orderStatusDAO.selectOrderStatusById(order.getOrderStatusId(), localeId));
            order.setUser(userDAO.selectById(order.getUserId()));
        }
        return orderList;
    }
}
