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
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.CONFIRM_PAGE;
import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_CART_ACTION;


public class CheckOutAllAction implements Action {

    private final CartItemBuilder cartItemBuilder = CartItemBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        RequestDispatcher dispatcher;
        if (AccessValidator.isAccessDenied(ROLE_USER_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        } else {
            Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
            List<CartItem> cartItemList = cartItemBuilder.fillUserCartItems(user.getId(), localeId);

            if (cartItemList.size() == EMPTY_REQUEST_LENGTH) {
                dispatcher = req.getRequestDispatcher(DISPLAY_CART_ACTION);
            } else {
                BigInteger totalPrice = cartItemBuilder.calculateTotalPrice(cartItemList);
                req.setAttribute(CART_ITEMS, cartItemList);
                req.setAttribute(CART_TOTAL_PRICE, totalPrice);
                dispatcher = req.getRequestDispatcher(CONFIRM_PAGE);
            }
        }
        dispatcher.forward(req, resp);
    }
}
