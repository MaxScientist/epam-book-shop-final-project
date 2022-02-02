package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.constants.SortType;
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

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.MAIN;

public class SortBookAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer locale_id = (Integer) session.getAttribute(LOCALE_ID);
        List<Book> books = bookBuilder.fillAllToDisplay(locale_id);

        if (req.getParameter(SORT_TYPE) != null) {
            bookBuilder.sortByType(books, SortType.valueOf(req.getParameter(SORT_TYPE)));
            session.setAttribute(SELECTED_SORT_TYPE, req.getParameter(SORT_TYPE));
        }
        req.setAttribute(BOOKS, books);
        dispatcher = req.getRequestDispatcher(MAIN);
        dispatcher.forward(req, resp);
    }
}
