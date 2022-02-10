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
import com.epam.bookshop.util.ImageUtil;
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
import static com.epam.bookshop.util.ErrorMessageProvider.displayErrorMessage;

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
            displayErrorMessage(req, resp, EMPTY_FIELD_ERROR, ADD_NEW_BOOK_PAGE);
        }

        Book book = bookBuilder.fillToUpdate(req);
        if (req.getParameter(BOOK_IMAGE) == null) {
            book.setBookImage(bookDAO.selectById(Long.valueOf(req.getParameter(BOOK_ID))).getBookImage());
        } else if (req.getPart(BOOK_IMAGE).getSize() > EMPTY_REQUEST_LENGTH) {
            if (ImageUtil.isImageFormatValid((req.getPart(BOOK_IMAGE)))) {
                book.setBookImage(ImageUtil.imageToBase(req.getPart(BOOK_IMAGE).getInputStream()));
            } else {
                displayErrorMessage(req, resp, IMAGE_ERROR, ADD_NEW_BOOK_PAGE);
            }
        }

        Publisher publisher = publisherBuilder.fillToUpdate(req);

        Edition edition = editionBuilder.fillToUpdate(req);
        if (!bookValidator.isISBNValid(edition.getIsbn())) {
            displayErrorMessage(req, resp, ISBN_ERROR, ADD_NEW_BOOK_PAGE);
        }

        bookDAO.update(book);
        publisherDAO.update(publisher);
        editionDAO.update(edition);

        dispatcher = req.getRequestDispatcher(SORT_BOOK_ACTION);
        dispatcher.forward(req, resp);
    }

}
