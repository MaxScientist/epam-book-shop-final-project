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
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.ROLE_ADMIN_ID;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_ALL_GENRES;


public class AddNewGenreAction implements Action {

    private final GenreBuilder genreBuilder = GenreBuilder.getInstance();
    private final GenreDAO genreDAO = new GenreDAOImpl();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)){
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }



        List<Genre>  genres = genreBuilder.fillNew(req);
        genreDAO.insert(genres);
        dispatcher = req.getRequestDispatcher(DISPLAY_ALL_GENRES);
        dispatcher.forward(req, resp);

    }
}
