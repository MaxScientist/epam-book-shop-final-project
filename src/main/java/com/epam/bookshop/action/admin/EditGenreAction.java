package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.GenreBuilder;
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

import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_ALL_GENRES;

public class EditGenreAction implements Action {

    private final GenreDAO genreDAO = new GenreDAOImpl();
    private final GenreBuilder genreBuilder = GenreBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;

        AccessValidator.isAdminRole(req, resp, session);

        List<Genre> genre = genreBuilder.fillUpdate(req);
        genreDAO.update(genre);

        dispatcher = req.getRequestDispatcher(DISPLAY_ALL_GENRES);
        dispatcher.forward(req, resp);
    }
}
