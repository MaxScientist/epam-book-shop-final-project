package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.PageNameConstants.BOOK_INFO_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;

public class ProductDetailsAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        Long bookId = Long.valueOf(req.getParameter(BOOK_ID));
        Book pickedBook = bookBuilder.fillOneToDisplay(bookId, localeId);

        req.setAttribute(BOOK_INFO, pickedBook);

        RequestDispatcher dispatcher = req.getRequestDispatcher(BOOK_INFO_PAGE);
        dispatcher.forward(req, resp);
    }
}
