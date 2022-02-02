package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.constants.SortType;
import com.epam.bookshop.database.dao.GenreDAO;
import com.epam.bookshop.database.dao.implementation.GenreDAOImpl;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.Genre;
import com.epam.bookshop.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.ParameterConstants.SORT_TYPES;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.SORT_BOOK_ACTION;


public class MainPageAction implements Action {

    private final GenreDAO genreDAO = new GenreDAOImpl();
    private final BookBuilder bookBuilder = BookBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession(true);
        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        List<Genre> genres = genreDAO.selectGenresByLanguageId(localeId);
        List<Book> books = bookBuilder.fillAllToDisplay(localeId);

        session.setAttribute(GENRES, genres);
        req.setAttribute(BOOKS, books);
        session.setAttribute(SORT_TYPES, SortType.values());
        req.getRequestDispatcher(SORT_BOOK_ACTION).forward(req, resp);
    }
}
