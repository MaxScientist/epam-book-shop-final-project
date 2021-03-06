package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.database.dao.BookToAuthorDAO;
import com.epam.bookshop.database.dao.implementation.BookToAuthorDAOImpl;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.EDIT_BOOK_PAGE_ACTION;

public class DeleteAuthorAction implements Action {

    private final BookToAuthorDAO bookToAuthorDAO = new BookToAuthorDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher;
        HttpSession session = req.getSession();

        AccessValidator.isAdminRole(req, resp, session);

        Long bookId = Long.valueOf(req.getParameter(BOOK_ID));
        Long authorId = Long.valueOf(req.getParameter(AUTHOR_ID));

        if (bookToAuthorDAO.isPairExists(bookId, authorId))
            bookToAuthorDAO.delete(bookId, authorId);

        dispatcher = req.getRequestDispatcher(EDIT_BOOK_PAGE_ACTION);
        dispatcher.forward(req, resp);
    }
}
