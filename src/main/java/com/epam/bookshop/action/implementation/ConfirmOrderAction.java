package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.OrderBuilder;
import com.epam.bookshop.database.dao.OrderDAO;
import com.epam.bookshop.database.dao.OrderItemDAO;
import com.epam.bookshop.database.dao.implementation.OrderDAOImpl;
import com.epam.bookshop.database.dao.implementation.OrderItemDAOImpl;
import com.epam.bookshop.entity.Order;
import com.epam.bookshop.entity.OrderItem;
import com.epam.bookshop.entity.User;
import com.epam.bookshop.util.validator.AccessValidator;
import com.epam.bookshop.util.validator.CartItemValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.bookshop.constants.PageNameConstants.LOGIN;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.*;

public class ConfirmOrderAction implements Action {

    private final OrderBuilder orderBuilder = OrderBuilder.getInstance();
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
    private final CartItemValidator cartItemValidator = CartItemValidator.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        if (!AccessValidator.isAccessDenied(ROLE_USER_ID, session)) {
            List<Long> cartItemIds = Stream.of(req.getParameterValues(CART_ITEM_ID)).map(Long::valueOf)
                    .collect(Collectors.toList());
            User user = (User) session.getAttribute(USER);

            if (!cartItemValidator.isCartItemExist(cartItemIds)) {
                req.setAttribute(HIDDEN_INPUT_ERROR, ERROR_OCCURRED);
                dispatcher = req.getRequestDispatcher(DISPLAY_CART_ACTION);
            } else {
                orderConfirmation(req, cartItemIds, user);
            }
        } else {
            dispatcher = req.getRequestDispatcher(LOGIN);
        }
        dispatcher.forward(req, resp);
    }

    private void orderConfirmation(HttpServletRequest req, List<Long> cartItemIds, User user) throws SQLException {
        Order newOrder = orderBuilder.fillNew(user.getId(), cartItemIds);
        Long newOrderId = orderDAO.insert(newOrder);

        for (OrderItem orderItem : newOrder.getOrderItems()) {
            orderItem.setOrderId(newOrderId);
            orderItemDAO.insert(orderItem);
        }
        req.setAttribute(ORDER, newOrder);
        dispatcher = req.getRequestDispatcher(DISPLAY_CUSTOMER_ORDER_ACTION);
    }
}
