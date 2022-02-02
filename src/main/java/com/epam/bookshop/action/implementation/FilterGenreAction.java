package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.database.dao.GenreDAO;
import com.epam.bookshop.database.dao.implementation.GenreDAOImpl;
import com.epam.bookshop.entity.Book;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.bookshop.constants.ServiceConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.*;


public class FilterGenreAction implements Action {

    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private final GenreDAO genreDAO = new GenreDAOImpl();

    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        List<Book> bookList;

        if (req.getParameterValues(GENRE_ID) != null) {
            List<Integer> genreIdList = Stream.of(req.getParameterValues(GENRE_ID)).map(Integer::valueOf)
                    .collect(Collectors.toList());
            bookList = bookBuilder.fillByFilter(genreIdList, localeId);
            req.setAttribute(CHECKED_GENRE_IDS, genreIdList);
        } else {
            bookList = bookBuilder.fillAllToDisplay(localeId);
        }
        req.setAttribute(BOOKS, bookList);

//        List<Genre> genreList = genreDAO.selectGenresByLanguageId(localeId);
//        session.setAttribute(GENRES,  genreList);
        dispatcher = req.getRequestDispatcher(MAIN);
        dispatcher.forward(req, resp);

    }
}
