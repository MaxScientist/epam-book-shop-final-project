package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.AuthorBuilder;
import com.epam.bookshop.database.dao.AuthorDAO;
import com.epam.bookshop.database.dao.implementation.AuthorDAOImpl;
import com.epam.bookshop.entity.Author;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.ParameterConstants.EMPTY_FIELD_ERROR;
import static com.epam.bookshop.constants.ServiceConstants.EDIT_BOOK_PAGE_ACTION;
import static com.epam.bookshop.constants.ServiceConstants.ERROR_OCCURRED;

public class EditAuthorAction implements Action {

    private final AuthorDAO authorDAO = new AuthorDAOImpl();
    private final AuthorBuilder authorBuilder = AuthorBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher;
        HttpSession session = req.getSession();

        AccessValidator.isAdminRole(req, resp, session);

        Author author = authorBuilder.fillToUpdate(req);

        if (author.getFirstName().isEmpty() || author.getLastName().isEmpty() ||
                authorDAO.selectById(author.getId()) == null) {
            req.setAttribute(EMPTY_FIELD_ERROR, ERROR_OCCURRED);
        } else
            authorDAO.update(author);

        dispatcher = req.getRequestDispatcher(EDIT_BOOK_PAGE_ACTION);
        dispatcher.forward(req, resp);
    }
}
