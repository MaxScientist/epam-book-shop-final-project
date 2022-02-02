package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.database.dao.OrderDAO;
import com.epam.bookshop.database.dao.OrderStatusDAO;
import com.epam.bookshop.database.dao.implementation.OrderDAOImpl;
import com.epam.bookshop.database.dao.implementation.OrderStatusDAOImpl;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.LOGIN;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_ALL_ORDERS_ACTION;
import static com.epam.bookshop.constants.ParameterConstants.*;

public class EditOrderStatusAction implements Action {

    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final OrderStatusDAO orderStatusDAO = new OrderStatusDAOImpl();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);

        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(LOGIN);
            dispatcher.forward(req, resp);
        }

        Integer orderStatusId = Integer.valueOf(req.getParameter(ORDER_STATUS_ID));
        Long orderId = Long.valueOf(req.getParameter(ORDER_ID));

        if ((orderDAO.selectOrderById(orderId) != null) &&
                (orderStatusDAO.selectOrderStatusById(orderStatusId, localeId) != null)) {
            orderDAO.updateOrderStatus(orderId, orderStatusId);
        }
        dispatcher = req.getRequestDispatcher(DISPLAY_ALL_ORDERS_ACTION);
        dispatcher.forward(req, resp);
    }
}
