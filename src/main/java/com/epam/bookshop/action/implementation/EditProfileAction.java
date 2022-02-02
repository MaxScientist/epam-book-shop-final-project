package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.UserBuilder;
import com.epam.bookshop.constants.ParameterConstants;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.database.dao.implementation.UserDAOImpl;
import com.epam.bookshop.entity.User;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.EDIT_PROFILE_PAGE_ACTION;
import static com.epam.bookshop.constants.ServiceConstants.ERROR_OCCURRED;
import static com.epam.bookshop.util.validator.UserValidator.*;


public class EditProfileAction implements Action {


    private final UserDAO userDAO = new UserDAOImpl();
    private final UserBuilder userBuilder = UserBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        User oldUser = (User) session.getAttribute(USER);

        if (AccessValidator.isBannedOrDeleted(oldUser)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        }

        User updatedUser = userBuilder.fillToUpdate(req, oldUser);
        if (updatedUser.equals(oldUser)) {
            dispatcher = req.getRequestDispatcher(EDIT_PROFILE_PAGE_ACTION);
            dispatcher.forward(req, resp);
        } else if (!isLoginFormatCorrect(updatedUser.getUserLogin())) {
            displayErrorMessage(req, resp, ParameterConstants.LOGIN_ERROR);
        } else if (!isPasswordFormatCorrect(req.getParameter(ParameterConstants.USER_PASSWORD))) {
            displayErrorMessage(req, resp, PASSWORD_ERROR);
        } else if (!isPhoneFormatCorrect(updatedUser.getPhoneNumber())) {
            displayErrorMessage(req, resp, PHONE_NUMBER_ERROR);
        } else if (!isPostalCodeFormatCorrect(updatedUser.getPostalCode())) {
            displayErrorMessage(req, resp, POSTAL_CODE_ERROR);
        }
        else {
            userDAO.update(updatedUser);
            session.setAttribute(USER, updatedUser);
            dispatcher = req.getRequestDispatcher(EDIT_PROFILE_PAGE_ACTION);
            dispatcher.forward(req, resp);
        }
    }

    private void displayErrorMessage(HttpServletRequest request, HttpServletResponse response,
                                     String errorName) throws ServletException, IOException {
        request.setAttribute(errorName, ERROR_OCCURRED);
        dispatcher = request.getRequestDispatcher(EDIT_PROFILE_PAGE_ACTION);
        dispatcher.forward(request, response);
    }
}
