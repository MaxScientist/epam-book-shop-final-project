package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.database.dao.GenreDAO;
import com.epam.bookshop.database.dao.implementation.GenreDAOImpl;
import com.epam.bookshop.entity.Genre;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.ADD_BOOK_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.GENRES;
import static com.epam.bookshop.constants.ParameterConstants.LOCALE_ID;

public class AddNewBookPageAction implements Action {

    private final GenreDAO genreDAO = new GenreDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;

        AccessValidator.isAdminRole(req, resp, session);

        Integer localeId = (Integer) session.getAttribute(LOCALE_ID);
        List<Genre> genres = genreDAO.selectGenresByLanguageId(localeId);
        req.setAttribute(GENRES, genres);
        dispatcher = req.getRequestDispatcher(ADD_BOOK_PAGE);
        dispatcher.forward(req, resp);


    }
}
