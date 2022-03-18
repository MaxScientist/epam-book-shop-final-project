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
import java.util.List;

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.SORT_BOOK_ACTION;

public class SearchAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        String title = req.getParameter(BOOK_TITLE);

        List<Book> books = bookBuilder.fillAllBySearch(localeId, title);
        session.setAttribute(BOOKS, books);

        RequestDispatcher dispatcher = req.getRequestDispatcher(SORT_BOOK_ACTION);
        dispatcher.forward(req, resp);
    }
}
