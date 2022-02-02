package com.epam.bookshop.action.builder;

import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.database.dao.EditionDAO;
import com.epam.bookshop.database.dao.PublisherDAO;
import com.epam.bookshop.database.dao.implementation.BookDAOImpl;
import com.epam.bookshop.database.dao.implementation.EditionDAOImpl;
import com.epam.bookshop.database.dao.implementation.PublisherDAOImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditionBuilder {

    private static EditionBuilder instance = new EditionBuilder();
    private final PublisherDAO publisherDAO = new PublisherDAOImpl();
    private final PublisherBuilder publisherBuilder = PublisherBuilder.getInstance();
    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final EditionDAO editionDAO = new EditionDAOImpl();


//    public void fillNewEditionBook(HttpServletRequest request) throws SQLException, ParseException, IOException, ServletException {
//        Publisher publisher = publisherBuilder.fillNewPublisher(request);
//        Long publisherId = publisherDAO.insert(publisher);
//        Book book = bookBuilder.fillNewBook(request);
//        Long bookId = bookDAO.insert(book);
//        Edition edition = new Edition();
//        edition.setBookId(bookId);
//        edition.setBinding(request.getParameter(BOOK_BINDING));
//        edition.setDescription(request.getParameter(BOOK_DESCRIPTION));
//        edition.setIsbn(request.getParameter(BOOK_ISBN));
//        edition.setPages(Integer.parseInt(request.getParameter(BOOK_PAGES)));
//        edition.setPrice(new BigInteger(request.getParameter(BOOK_PRICE)));
//        edition.setPubId(publisherId);
//        String releaseDate = request.getParameter(BOOK_RELEASE_DATE);
//        Date releaseDateBook = getReleaseDate(releaseDate);
//        edition.setReleaseDate(releaseDateBook);
//        Long editionId = editionDAO.insert(edition);
//        edition.setId(editionId);
//
//    }

    private Date getReleaseDate(String date) throws ParseException {
        String dateFormat = "yyyy/MM/dd";
        return new SimpleDateFormat(dateFormat).parse(date);
    }

    public static EditionBuilder getInstance() {
        if (instance == null) {
            instance = new EditionBuilder();
        }
        return instance;
    }
}
