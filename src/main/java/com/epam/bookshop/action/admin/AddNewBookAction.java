package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.BookBuilder;
import com.epam.bookshop.util.validator.AccessValidator;
import com.epam.bookshop.util.validator.BookValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.EMPTY_FIELD_ERROR;
import static com.epam.bookshop.constants.ParameterConstants.ROLE_ADMIN_ID;
import static com.epam.bookshop.constants.ServiceConstants.*;


public class AddNewBookAction implements Action {

    private RequestDispatcher dispatcher;
    private final BookBuilder bookBuilder = BookBuilder.getInstance();
    private final BookValidator bookValidator = BookValidator.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();


        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }

        if (bookValidator.isEmptyParamExists(req)) {
            displayErrorMessage(req, resp);
        } else {
            bookBuilder.fillNewBook(req);
            dispatcher = req.getRequestDispatcher(TO_MAIN);
            dispatcher.forward(req, resp);
        }
    }

    private void displayErrorMessage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(EMPTY_FIELD_ERROR, ERROR_OCCURRED);
        dispatcher = request.getRequestDispatcher(ADD_NEW_BOOK_PAGE);
        dispatcher.forward(request, response);
    }
}
