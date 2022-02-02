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
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.ROLE_ADMIN_ID;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_ALL_GENRES;

public class EditGenreAction implements Action {

    private RequestDispatcher dispatcher;
    private final GenreDAO genreDAO = new GenreDAOImpl();
    private final GenreBuilder genreBuilder = GenreBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, req.getSession())) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }

        List<Genre> genre = genreBuilder.fillUpdate(req);

        genreDAO.update(genre);
        dispatcher = req.getRequestDispatcher(DISPLAY_ALL_GENRES);
        dispatcher.forward(req, resp);
    }
}
