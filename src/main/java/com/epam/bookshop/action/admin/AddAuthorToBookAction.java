package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.AuthorBuilder;
import com.epam.bookshop.database.dao.AuthorDAO;
import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.database.dao.BookToAuthorDAO;
import com.epam.bookshop.database.dao.implementation.AuthorDAOImpl;
import com.epam.bookshop.database.dao.implementation.BookDAOImpl;
import com.epam.bookshop.database.dao.implementation.BookToAuthorDAOImpl;
import com.epam.bookshop.entity.Author;
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
import static com.epam.bookshop.constants.ServiceConstants.ERROR_OCCURRED;


public class AddAuthorToBookAction implements Action {

    private final BookDAO bookDAO = new BookDAOImpl();
    private final AuthorDAO authorDAO = new AuthorDAOImpl();
    private final AuthorBuilder authorBuilder = AuthorBuilder.getInstance();
    private final BookToAuthorDAO bookToAuthorDAO = new BookToAuthorDAOImpl();
    private RequestDispatcher dispatcher;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        AccessValidator.isAdminRole(req, resp, session);

        Author author = authorBuilder.fillNewAuthor(req);
        Long bookId = Long.valueOf(req.getParameter(BOOK_ID));

        bookToAuthorPairing(req, resp, author, bookId);
        dispatcher = req.getRequestDispatcher(EDIT_BOOK_PAGE_ACTION);
        dispatcher.forward(req, resp);


    }

    private void bookToAuthorPairing(HttpServletRequest req, HttpServletResponse resp, Author author, Long bookId) throws SQLException, ServletException, IOException {
        if (bookDAO.selectById(bookId) == null) {
            req.setAttribute(HIDDEN_INPUT_ERROR, ERROR_OCCURRED);
        } else if (authorDAO.isAuthorExists(author)) {
            if (!bookToAuthorDAO.isPairExists(bookId, authorDAO.selectAuthorByName(author))) {
                Long authorId = authorDAO.selectAuthorByName(author);
                bookToAuthorDAO.insert(bookId, authorId);
                dispatcher = req.getRequestDispatcher(EDIT_BOOK_PAGE_ACTION);
                dispatcher.forward(req, resp);
            } else {
                req.setAttribute(NOT_UNIQUE_BOOK_AUTHOR_ERROR, ERROR_OCCURRED);
            }
        } else {
            Long authorId = authorDAO.insert(author);
            bookToAuthorDAO.insert(bookId, authorId);
        }
    }
}
