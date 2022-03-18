package com.epam.bookshop.action.builder;

import com.epam.bookshop.database.dao.EditionDAO;
import com.epam.bookshop.database.dao.implementation.EditionDAOImpl;
import com.epam.bookshop.entity.Edition;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.SQLException;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class EditionBuilder {

    private static EditionBuilder instance = new EditionBuilder();
    private final EditionDAO editionDAO = new EditionDAOImpl();

    private EditionBuilder() {
    }

    public static EditionBuilder getInstance() {
        if (instance == null) {
            instance = new EditionBuilder();
        }
        return instance;
    }

    public Edition fillNewEditionBook(HttpServletRequest request) {
        Edition edition = new Edition();

        edition.setBinding(request.getParameter(BOOK_BINDING).trim());
        edition.setDescription(request.getParameter(BOOK_DESCRIPTION).trim());
        edition.setIsbn(request.getParameter(BOOK_ISBN).replaceAll(ONLY_NUMBER_REGEX, WHITE_SPACE).trim());
        edition.setPages(Integer.parseInt(request.getParameter(BOOK_PAGES).trim()));
        edition.setPrice(new BigInteger(request.getParameter(BOOK_PRICE).trim()));
        String releaseDate = request.getParameter(BOOK_RELEASE_DATE);
        Date releaseDateBook = Date.valueOf(releaseDate);
        edition.setReleaseDate(releaseDateBook);

        return edition;
    }

    public Edition fillToUpdate(HttpServletRequest req) throws SQLException {
        Edition edition = fillNewEditionBook(req);
        edition.setId(editionDAO.selectByBookId(Long.valueOf(req.getParameter(BOOK_ID))).getId());
        edition.setBookId(Long.valueOf(req.getParameter(BOOK_ID)));
        edition.setPubId(editionDAO.selectByBookId(Long.valueOf(req.getParameter(BOOK_ID))).getPubId());
        if (edition.getReleaseDate() == null) {
            edition.setReleaseDate(editionDAO.selectByBookId(Long.valueOf(req.getParameter(BOOK_ID))).getReleaseDate());
        }
        return edition;
    }


}
