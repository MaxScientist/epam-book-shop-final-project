package com.epam.bookshop.action.builder;

import com.epam.bookshop.database.dao.OrderItemDAO;
import com.epam.bookshop.database.dao.implementation.OrderItemDAOImpl;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.CartItem;
import com.epam.bookshop.entity.OrderItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemBuilder {

    private static OrderItemBuilder instance = new OrderItemBuilder();

    private final CartItemBuilder cartItemBuilder = CartItemBuilder.getInstance();
    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private final OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

    private OrderItemBuilder() {
    }

    public List<OrderItem> fillNewOrderItems(List<Long> cartItemIds) throws SQLException {
        List<OrderItem> orderItems = new ArrayList<>();
        List<CartItem> cartItems = cartItemBuilder.fillCartItemByIds(cartItemIds);

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setBookId(cartItem.getBookId());
            Book book = cartItem.getBook();
            bookBuilder.fillGivenUnit(book, book.getLanguageId());
            orderItem.setBook(book);
            orderItem.setFixedPrice(book.getBookPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public List<OrderItem> fillOrderItems(Long orderId, Integer sessionLanguageId) throws SQLException {
        List<OrderItem> orderItemList = orderItemDAO.selectAllByOrderId(orderId);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setBook(bookBuilder.fillOneToDisplay(orderItem.getBookId(), sessionLanguageId));
        }
        return orderItemList;
    }

    public static OrderItemBuilder getInstance() {
        if (instance == null) {
            instance = new OrderItemBuilder();
        }
        return instance;

    }


}
