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
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.SORT_BOOK_ACTION;

public class SearchAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        String title = req.getParameter(BOOK_TITLE);

        List<Book> books = bookBuilder.fillAllBySearch(localeId, title);

        req.setAttribute(BOOKS, books);
        dispatcher = req.getRequestDispatcher(SORT_BOOK_ACTION);
        dispatcher.forward(req, resp);
    }
}
