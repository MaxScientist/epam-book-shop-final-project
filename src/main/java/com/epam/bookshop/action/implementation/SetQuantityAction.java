package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.CartItemBuilder;
import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.database.dao.implementation.CartDAOImpl;
import com.epam.bookshop.entity.CartItem;
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

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_CART_ACTION;
import static com.epam.bookshop.constants.ServiceConstants.ERROR_OCCURRED;

public class SetQuantityAction implements Action {

    private final CartItemBuilder cartItemBuilder = CartItemBuilder.getInstance();
    private final CartDAO cartDAO = new CartDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        RequestDispatcher dispatcher;
        if (AccessValidator.isAccessDenied(ROLE_USER_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }

        User user = (User) session.getAttribute(USER);
        CartItem cartItem = cartItemBuilder.fillToUpdate(req);

        if (cartItem.getQuantity() > DEFAULT_CART_ITEM_QUANTITY &&
                cartItem.getUserId().equals(user.getId())) {
            cartDAO.updateQuantity(cartItem);
        } else {
            req.setAttribute(HIDDEN_INPUT_ERROR, ERROR_OCCURRED);
        }
        dispatcher = req.getRequestDispatcher(DISPLAY_CART_ACTION);
        dispatcher.forward(req, resp);
    }
}
