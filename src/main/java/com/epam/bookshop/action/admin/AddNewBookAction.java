package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.AuthorBuilder;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.action.builder.EditionBuilder;
import com.epam.bookshop.action.builder.PublisherBuilder;
import com.epam.bookshop.database.dao.*;
import com.epam.bookshop.database.dao.implementation.*;
import com.epam.bookshop.entity.Author;
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


public class AddNewBookAction implements Action {

    private RequestDispatcher dispatcher;
    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private final BookValidator bookValidator = BookValidator.getInstance();
    private final AuthorBuilder authorBuilder = AuthorBuilder.getInstance();
    private final BookToAuthorDAO bookToAuthorDAO = new BookToAuthorDAOImpl();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final AuthorDAO authorDAO = new AuthorDAOImpl();
    private final PublisherBuilder publisherBuilder = PublisherBuilder.getInstance();
    private final PublisherDAO publisherDAO = new PublisherDAOImpl();
    private final EditionBuilder editionBuilder = EditionBuilder.getInstance();
    private final EditionDAO editionDAO = new EditionDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();


        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }

        if (bookValidator.isEmptyParamExists(req)) {
            displayErrorMessage(req, resp, EMPTY_FIELD_ERROR);
        } else {
            Author author = authorBuilder.fillNewAuthor(req);
            assembleAuthorDetails(author);

            Book book = bookBuilder.fillNewBook(req);
            assembleBookDetails(book, req, resp);

            bookToAuthorDAO.insert(book.getId(), author.getId());

            Publisher publisher = publisherBuilder.fillNewPublisher(req);
            assemblePublisherDetails(publisher);

            Edition edition = editionBuilder.fillNewEditionBook(req);
            assembleEditionDetails(req, resp, edition, book, publisher);

            dispatcher = req.getRequestDispatcher(SORT_BOOK_ACTION);
            dispatcher.forward(req, resp);
        }
    }

    private void assembleEditionDetails(HttpServletRequest req, HttpServletResponse resp, Edition edition, Book book, Publisher publisher) throws SQLException, ServletException, IOException {
        if (bookValidator.isISBNValid(edition.getIsbn())) {
            edition.setBookId(book.getId());
            edition.setPubId(publisher.getId());
            editionDAO.insert(edition);
        } else {
            displayErrorMessage(req, resp, ISBN_INVALID_ERROR);

        }
    }


    private void assemblePublisherDetails(Publisher publisher) throws SQLException {
        Long publisherId;
        Publisher publisherFromBase = publisherDAO.selectByPublishHouse(publisher.getPublishHouse());
        if (publisherFromBase == null || !publisherFromBase.getPublishHouse().
                equalsIgnoreCase(publisher.getPublishHouse())) {
            publisherId = publisherDAO.insert(publisher);
        } else {
            publisherId = publisherFromBase.getId();
        }
        publisher.setId(publisherId);
    }

    private void assembleBookDetails(Book book, HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        if (bookDAO.isBookExists(book.getTitle())) {
            displayErrorMessage(req, resp, SUCH_BOOK_EXISTS);
        } else {
            Long bookId = bookDAO.insert(book);
            book.setId(bookId);
        }
    }


    private void assembleAuthorDetails(Author author) throws SQLException {
        Long authorId;
        if (authorDAO.isAuthorExists(author)) {
            authorId = authorDAO.selectAuthorByName(author);
        } else {
            authorId = authorDAO.insert(author);
        }
        author.setId(authorId);
    }

    private void displayErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorName) throws ServletException, IOException {
        request.setAttribute(errorName, ERROR_OCCURRED);
        dispatcher = request.getRequestDispatcher(ADD_NEW_BOOK_PAGE);
        dispatcher.forward(request, response);
    }
}
