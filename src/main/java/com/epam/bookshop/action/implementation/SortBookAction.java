package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.constants.SortType;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.*;

public class SortBookAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer locale_id = (Integer) session.getAttribute(LOCALE_ID);

        List<Book> books = null;
        if (session.getAttribute(BOOKS) == null) {
            bookBuilder.fillAllToDisplay(locale_id);
        } else {
            books = (ArrayList<Book>) session.getAttribute(BOOKS);
        }

        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            books = bookBuilder.getActive(books);
        }
        if (req.getParameter(SORT_TYPE) != null) {
            bookBuilder.sortByType(books, SortType.valueOf(req.getParameter(SORT_TYPE)));
            session.setAttribute(SELECTED_SORT_TYPE, req.getParameter(SORT_TYPE));
        }
        session.setAttribute(BOOKS, books);
        dispatcher = req.getRequestDispatcher(MAIN);
        dispatcher.forward(req, resp);
    }
}
