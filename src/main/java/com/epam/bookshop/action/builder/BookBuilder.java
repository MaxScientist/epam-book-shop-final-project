package com.epam.bookshop.action.builder;

import com.epam.bookshop.constants.SortType;
import com.epam.bookshop.database.dao.*;
import com.epam.bookshop.database.dao.implementation.*;
import com.epam.bookshop.entity.Author;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.Edition;
import com.epam.bookshop.entity.Publisher;
import com.epam.bookshop.util.ImageUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class BookBuilder {

    private static BookBuilder instance = new BookBuilder();

    private final AuthorDAO authorDAO = new AuthorDAOImpl();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final EditionDAO editionDAO = new EditionDAOImpl();
    private final GenreDAO genreDAO = new GenreDAOImpl();
    private final PublisherDAO publisherDAO = new PublisherDAOImpl();
    private final BookToAuthorDAO bookToAuthorDAO = new BookToAuthorDAOImpl();
    private final AuthorBuilder authorBuilder = AuthorBuilder.getInstance();
    private final PublisherBuilder publisherBuilder = PublisherBuilder.getInstance();


    private BookBuilder() {
    }

    public List<Book> fillByFilter(List<Integer> genreIdList, Integer localeId) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        for (Integer genreId : genreIdList) {
            bookList.addAll(bookDAO.selectAllByFilter(genreId, localeId));
        }
        fillGivenListOfBooks(bookList, localeId);
        bookList = bookList.stream().distinct().collect(Collectors.toList());
        return bookList;
    }

    public Book fillOneToDisplay(Long bookId, Integer sessionLanguageId) throws SQLException {
        Book book = bookDAO.selectById(bookId);
        fillGivenUnit(book, sessionLanguageId);
        return book;
    }

    public List<Book> fillAllToDisplay(Integer localeId) throws SQLException {
        List<Book> bookList = bookDAO.selectAllBooks();
        fillGivenListOfBooks(bookList, localeId);
        return bookList;
    }

    private void fillGivenListOfBooks(List<Book> bookList, Integer localeId) throws SQLException {
        for (Book book : bookList) {
            fillBookWithEditionInfo(book, localeId);
        }
    }

    public void fillGivenUnit(Book book, Integer localeId) throws SQLException {
        fillBookWithEditionInfo(book, localeId);
    }

    private void fillBookWithEditionInfo(Book book, Integer localeId) throws SQLException {
        Edition edition = editionDAO.selectByBookId(book.getId());
        book.setAuthors(authorDAO.selectByBookId(book.getId()));
        book.setBinding(edition.getBinding());
        book.setBookPrice(editionDAO.selectByBookId(book.getId()).getPrice());
        book.setDescription(edition.getDescription());
        book.setGenre(genreDAO.selectGenreByBookLanguageId(book.getId(), localeId));
        book.setIsbn(edition.getIsbn());
        book.setPages(edition.getPages());
        book.setPublisher(publisherDAO.selectById(edition.getPubId()));
        book.setReleaseDate(edition.getReleaseDate());
    }

    public void fillNewBook(HttpServletRequest request) throws SQLException, IOException, ServletException {

        Author author = authorBuilder.fillNewAuthor(request);
        Long authorId;
        if (authorDAO.isAuthorExists(author)) {
            authorId = authorDAO.selectAuthorByName(author);
        } else {
            authorId = authorDAO.insert(author);
        }
        author.setId(authorId);

        Book book = new Book();
        book.setTitle(request.getParameter(BOOK_TITLE));
        book.setGenreId(Long.parseLong(request.getParameter(GENRE_ID)));
        book.setAccessStatusId(ACCESS_STATUS_ACTIVE_ID);
        book.setLanguageId(Integer.parseInt(request.getParameter(LANGUAGE_ID)));

        Part imageBook = request.getPart(BOOK_IMAGE);
        if (imageBook.getSize() > EMPTY_REQUEST_LENGTH) {
            if (ImageUtil.isImageFormatValid(imageBook)) {
                book.setBookImage(ImageUtil.imageToBase(request.getPart(BOOK_IMAGE).getInputStream()));
            }
        }

        Long bookId = bookDAO.insert(book);
        bookToAuthorDAO.insert(bookId, authorId);

        Publisher publisher = publisherBuilder.fillNewPublisher(request);
        Long publisherId;
        Publisher publisherFromBase = publisherDAO.selectByPublishHouse(publisher.getPublishHouse());
        if (publisherFromBase == null || !publisherFromBase.getPublishHouse().
                equalsIgnoreCase(publisher.getPublishHouse())) {
            publisherId = publisherDAO.insert(publisher);
        } else {
            publisherId = publisherFromBase.getId();
        }

        Edition edition = new Edition();
        edition.setPubId(publisherId);
        edition.setBookId(bookId);

        edition.setBinding(request.getParameter(BOOK_BINDING));
        book.setBinding(request.getParameter(BOOK_BINDING));

        edition.setDescription(request.getParameter(BOOK_DESCRIPTION));
        book.setDescription(request.getParameter(BOOK_DESCRIPTION));

        edition.setIsbn(request.getParameter(BOOK_ISBN));
        book.setIsbn(request.getParameter(BOOK_ISBN));

        edition.setPages(Integer.parseInt(request.getParameter(BOOK_PAGES)));
        book.setPages(Integer.parseInt(request.getParameter(BOOK_PAGES)));

        edition.setPrice(new BigInteger(request.getParameter(BOOK_PRICE)));
        book.setBookPrice(new BigInteger(request.getParameter(BOOK_PRICE)));

        String releaseDate = request.getParameter(BOOK_RELEASE_DATE);
        Date releaseDateBook = Date.valueOf(releaseDate);
        edition.setReleaseDate(releaseDateBook);
        book.setReleaseDate(releaseDateBook);
        Long editionId = editionDAO.insert(edition);
        edition.setId(editionId);
    }

    public void sortByType(List<Book> books, SortType sortType) {
        switch (sortType) {
            case PRICE_ASC:
                books.sort(Comparator.comparing(Book::getBookPrice));
                break;
            case PRICE_DESC:
                books.sort(Comparator.comparing(Book::getBookPrice, Comparator.reverseOrder()));
                break;
            case TITLE:
                books.sort(Comparator.comparing(Book::getTitle));
                break;
        }
    }


    public static BookBuilder getInstance() {
        if (instance == null) {
            instance = new BookBuilder();
        }
        return instance;
    }


}
