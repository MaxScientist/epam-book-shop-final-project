package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.OrderBuilder;
import com.epam.bookshop.entity.Order;
import com.epam.bookshop.entity.User;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.CUSTOMER_ORDER_JSP;
import static com.epam.bookshop.constants.PageNameConstants.LOGIN;
import static com.epam.bookshop.constants.ParameterConstants.*;

public class DisplayCustomerAction implements Action {

    private final OrderBuilder orderBuilder = OrderBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);

        if (AccessValidator.isAccessDenied(ROLE_USER_ID, session)) {
            dispatcher = req.getRequestDispatcher(LOGIN);
            dispatcher.forward(req, resp);
        }

        User user = (User) session.getAttribute(USER);
        List<Order> orderList = orderBuilder.fillUserOrders(user.getId(), localeId);

        req.setAttribute(USER_ORDERS, orderList);
        dispatcher = req.getRequestDispatcher(CUSTOMER_ORDER_JSP);
        dispatcher.forward(req, resp);
    }
}
