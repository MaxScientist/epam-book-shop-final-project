package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.CartItemBuilder;
import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.database.dao.implementation.CartDAOImpl;
import com.epam.bookshop.entity.CartItem;
import com.epam.bookshop.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.CART_PAGE;
import static com.epam.bookshop.constants.ServiceConstants.TO_MAIN;
import static com.epam.bookshop.constants.ParameterConstants.*;


public class CART_PAGE implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        if (user != null) {
            CartDAO cartDAO = new CartDAOImpl();
            Integer localeId = (Integer) session.getAttribute(LOCALE_ID);

            List<CartItem> cartItem = CartItemBuilder.getInstance().fillUserCartItems(user.getId(),localeId);
            session.setAttribute(CART_ITEMS, cartItem);

            req.getRequestDispatcher(CART_PAGE).forward(req, resp);
        } else {
            req.getRequestDispatcher(TO_MAIN).forward(req, resp);
        }
    }
}
