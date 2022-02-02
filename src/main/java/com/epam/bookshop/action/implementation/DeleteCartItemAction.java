package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.database.dao.implementation.CartDAOImpl;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.CART_ITEM_ID;
import static com.epam.bookshop.constants.ParameterConstants.ROLE_USER_ID;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_CART_ACTION;

public class DeleteCartItemAction implements Action {

    private final CartDAO cartDAO = new CartDAOImpl();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        if(AccessValidator.isAccessDenied(ROLE_USER_ID, req.getSession())) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }
        Long cartItemId = Long.valueOf(req.getParameter(CART_ITEM_ID));

        if (cartDAO.selectById(cartItemId) != null) {
            cartDAO.delete(cartItemId);
        }
        dispatcher = req.getRequestDispatcher(DISPLAY_CART_ACTION);
        dispatcher.forward(req, resp);
    }
}
