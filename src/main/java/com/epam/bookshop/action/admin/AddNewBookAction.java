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

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.*;
import static com.epam.bookshop.util.ErrorMessageProvider.displayErrorMessage;


public class AddNewBookAction implements Action {

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
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;

        AccessValidator.isAdminRole(req, resp, session);

        if (bookValidator.isEmptyParamExists(req)) {
            displayErrorMessage(req, resp, EMPTY_FIELD_ERROR, ADD_NEW_BOOK_PAGE);
        }
        assembleAllBookDetails(req, resp);

        dispatcher = req.getRequestDispatcher(TO_MAIN);
        dispatcher.forward(req, resp);
    }

    private void assembleAllBookDetails(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Author author = authorBuilder.fillNewAuthor(req);
        Book book = checkBookDetails(req, resp);
        Publisher publisher = publisherBuilder.fillNewPublisher(req);
        Edition edition = editionBuilder.fillNewEditionBook(req);

        if (!bookValidator.isISBNValid(edition.getIsbn())) {
            displayErrorMessage(req, resp, ISBN_ERROR, ADD_NEW_BOOK_PAGE);
        }
        assembleAuthorDetails(author);
        assembleBookDetails(book);

        bookToAuthorDAO.insert(book.getId(), author.getId());

        assemblePublisherDetails(publisher);
        assembleEditionDetails(edition, book, publisher);
    }

    private Book checkBookDetails(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        Book book = bookBuilder.fillNewBook(req);
        if (bookDAO.isBookExists(book.getTitle())) {
            displayErrorMessage(req, resp, SUCH_BOOK_EXISTS_ERROR, ADD_NEW_BOOK_PAGE);
        } else if (req.getPart(BOOK_IMAGE).getSize() > EMPTY_REQUEST_LENGTH) {
            if (ImageUtil.isImageFormatValid((req.getPart(BOOK_IMAGE)))) {
                book.setBookImage(ImageUtil.imageToBase(req.getPart(BOOK_IMAGE).getInputStream()));
            } else {
                displayErrorMessage(req, resp, IMAGE_ERROR, ADD_NEW_BOOK_PAGE);
            }
        }
        return book;
    }

    private void assembleEditionDetails(Edition edition, Book book, Publisher publisher) throws SQLException {
        edition.setBookId(book.getId());
        edition.setPubId(publisher.getId());
        editionDAO.insert(edition);
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

    private void assembleBookDetails(Book book) throws SQLException {
        Long bookId = bookDAO.insert(book);
        book.setId(bookId);
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


}
