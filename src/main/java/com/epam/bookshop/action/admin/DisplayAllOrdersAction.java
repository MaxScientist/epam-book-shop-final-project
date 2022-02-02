package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.OrderBuilder;
import com.epam.bookshop.database.dao.OrderStatusDAO;
import com.epam.bookshop.database.dao.implementation.OrderStatusDAOImpl;
import com.epam.bookshop.entity.Order;
import com.epam.bookshop.entity.OrderStatus;
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

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.PageNameConstants.ORDER_JSP;
import static com.epam.bookshop.constants.ParameterConstants.*;

public class DisplayAllOrdersAction implements Action {

    private final OrderBuilder orderBuilder = OrderBuilder.getInstance();
    private final OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);

        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }

        List<Order> orderList = orderBuilder.fillAllOrders(localeId);
        List<OrderStatus> orderStatusList = orderStatusDAO.selectAll(localeId);

        req.setAttribute(ORDERS, orderList);
        req.setAttribute(ORDER_STATUSES, orderStatusList);
        dispatcher = req.getRequestDispatcher(ORDER_JSP);
        dispatcher.forward(req, resp);
    }
}
