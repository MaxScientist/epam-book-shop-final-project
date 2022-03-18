package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.PageNameConstants.EDIT_BOOK_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;

public class EditBookPageAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;

        AccessValidator.isAdminRole(req, resp, session);

        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        Long bookId = Long.valueOf(req.getParameter(BOOK_ID));
        Book pickedBook = bookBuilder.fillOneToDisplay(bookId, localeId);

        req.setAttribute(BOOK_INFO, pickedBook);

        dispatcher = req.getRequestDispatcher(EDIT_BOOK_PAGE);
        dispatcher.forward(req, resp);
    }
}
