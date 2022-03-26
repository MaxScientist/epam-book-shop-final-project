package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.CartItemBuilder;
import com.epam.bookshop.entity.CartItem;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.bookshop.constants.PageNameConstants.CONFIRM_PAGE;
import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_CART_ACTION;

public class CheckOutSelectedAction implements Action {

    private final CartItemBuilder cartItemBuilder = CartItemBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        if (AccessValidator.isAccessDenied(ROLE_USER_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        } else if (req.getParameterValues(CART_ITEM_ID) != null) {
            setItemAndPrice(req);
        } else {
            dispatcher = req.getRequestDispatcher(DISPLAY_CART_ACTION);
        }
        dispatcher.forward(req, resp);
    }

    private void setItemAndPrice(HttpServletRequest req) throws SQLException {
        List<Long> cartIds = Stream.of(req.getParameterValues(CART_ITEM_ID)).
                map(Long::valueOf).collect(Collectors.toList());
        List<CartItem> cartItems = cartItemBuilder.fillCartItemByIds(cartIds);

        if (cartItems.size() != EMPTY_REQUEST_LENGTH) {
            BigInteger totalPrice = cartItemBuilder.calculateTotalPrice(cartItems);

            req.setAttribute(CART_ITEMS, cartItems);
            req.setAttribute(CART_TOTAL_PRICE, totalPrice);
            dispatcher = req.getRequestDispatcher(CONFIRM_PAGE);
        }
    }
}
