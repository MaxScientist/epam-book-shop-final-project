package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.CartItemBuilder;
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
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.CART_PAGE;
import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;


public class DisplayCartAction implements Action {

    private final CartItemBuilder cartItemBuilder = CartItemBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        if (AccessValidator.isAccessDenied(ROLE_USER_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        } else {
            User user = (User) session.getAttribute(USER);
            Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
            List<CartItem> cartItems = cartItemBuilder.fillUserCartItems(user.getId(), localeId);
            req.setAttribute(CART_ITEMS, cartItems);
            dispatcher = req.getRequestDispatcher(CART_PAGE);
        }
        dispatcher.forward(req, resp);
    }
}
