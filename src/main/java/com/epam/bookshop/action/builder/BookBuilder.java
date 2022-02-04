package com.epam.bookshop.action.builder;

import com.epam.bookshop.constants.SortType;
import com.epam.bookshop.database.dao.*;
import com.epam.bookshop.database.dao.implementation.*;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.Edition;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

    public Book fillNewBook(HttpServletRequest request) {

        Book book = new Book();
        book.setTitle(request.getParameter(BOOK_TITLE).trim());
        book.setGenreId(Long.parseLong(request.getParameter(GENRE_ID)));
        book.setAccessStatusId(ACCESS_STATUS_ACTIVE_ID);
        book.setLanguageId(Integer.parseInt(request.getParameter(LANGUAGE_ID)));
        return book;
    }
    public Book fillToUpdate(HttpServletRequest req) throws IOException, ServletException, SQLException {
        Book book = fillNewBook(req);
        book.setId(Long.valueOf(req.getParameter(BOOK_ID)));
        book.setAccessStatusId(Integer.parseInt(req.getParameter(ACCESS_STATUS_ID)));

        return book;
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

    public List<Book> fillAllBySearch(Integer localeId, String title) throws SQLException {
        List<Book> books = bookDAO.selectByTitle(title);
        fillGivenListOfBooks(books, localeId);
        return books;
    }
    public List<Book> getActive(List<Book> books) {
        List<Book> activeVolumes = new ArrayList<>();
        for (Book book : books) {
            if (book.getAccessStatusId().equals(ACCESS_STATUS_ACTIVE_ID))
                activeVolumes.add(book);
        }
        return activeVolumes;
    }

    public static BookBuilder getInstance() {
        if (instance == null) {
            instance = new BookBuilder();
        }
        return instance;
    }


}
