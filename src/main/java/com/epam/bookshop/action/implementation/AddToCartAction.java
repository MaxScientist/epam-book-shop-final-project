package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.CartItemBuilder;
import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.database.dao.implementation.BookDAOImpl;
import com.epam.bookshop.database.dao.implementation.CartDAOImpl;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.CartItem;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.*;

public class AddToCartAction implements Action {

    private final CartItemBuilder cartItemBuilder = CartItemBuilder.getInstance();
    private final CartDAO cartDAO = new CartDAOImpl();
    private final BookDAO bookDAO = new BookDAOImpl();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher;
        if (AccessValidator.isAccessDenied(ROLE_USER_ID, req.getSession())) {
            dispatcher = req.getRequestDispatcher(SIGN_UP_USER_ACTION_PAGE);
        } else {
            Book book = bookDAO.selectById(Long.valueOf(req.getParameter(BOOK_ID)));

            if (book == null || book.getAccessStatusId().equals(ACCESS_STATUS_DELETED_ID)) {
                req.setAttribute(HIDDEN_INPUT_ERROR, ERROR_OCCURRED);
            } else {
                CartItem cartItem = cartItemBuilder.fillNew(req);
                if (!cartDAO.isBookExistsInCart(cartItem)) {
                    cartDAO.insert(cartItem);
                }
            }
            dispatcher = req.getRequestDispatcher(BOOK_DETAILS_ACTION);
        }
        dispatcher.forward(req, resp);
    }
}
