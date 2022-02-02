package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.action.builder.EditionBuilder;
import com.epam.bookshop.action.builder.PublisherBuilder;
import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.database.dao.EditionDAO;
import com.epam.bookshop.database.dao.PublisherDAO;
import com.epam.bookshop.database.dao.implementation.BookDAOImpl;
import com.epam.bookshop.database.dao.implementation.EditionDAOImpl;
import com.epam.bookshop.database.dao.implementation.PublisherDAOImpl;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.Edition;
import com.epam.bookshop.entity.Publisher;
import com.epam.bookshop.util.validator.AccessValidator;
import com.epam.bookshop.util.validator.BookValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.*;

public class EditBookAction implements Action {

    private RequestDispatcher dispatcher;
    private final BookValidator bookValidator = BookValidator.getInstance();
    private final PublisherDAO publisherDAO = new PublisherDAOImpl();
    private final PublisherBuilder publisherBuilder = PublisherBuilder.getInstance();
    private final EditionDAO editionDAO = new EditionDAOImpl();
    private final EditionBuilder editionBuilder = EditionBuilder.getInstance();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final BookBuilder bookBuilder = BookBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {

        HttpSession session = req.getSession();
        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        } else if (bookValidator.isEmptyUpdateDataExists(req)) {
            displayErrorMessage(req, resp, EMPTY_FIELD_ERROR);
        }

        Book book = bookBuilder.fillToUpdate(req);
        bookDAO.update(book);


        Publisher publisher = publisherBuilder.fillToUpdate(req);
        publisherDAO.update(publisher);

        Edition edition = editionBuilder.fillToUpdate(req);
        if (bookValidator.isISBNValid(edition.getIsbn())) {
            editionDAO.update(edition);
        } else {
            displayErrorMessage(req, resp, ISBN_INVALID_ERROR);
        }

        dispatcher = req.getRequestDispatcher(SORT_BOOK_ACTION);
        dispatcher.forward(req, resp);
    }


    private void displayErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorName) throws ServletException, IOException {
        request.setAttribute(errorName, ERROR_OCCURRED);
        dispatcher = request.getRequestDispatcher(ADD_NEW_BOOK_PAGE);
        dispatcher.forward(request, response);
    }
}
