package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.UserBuilder;
import com.epam.bookshop.constants.ParameterConstants;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.database.dao.implementation.UserDAOImpl;
import com.epam.bookshop.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.INDEX_JSP;
import static com.epam.bookshop.constants.ServiceConstants.SIGN_UP_USER_ACTION_PAGE;
import static com.epam.bookshop.util.ErrorMessageProvider.getErrorMessage;
import static com.epam.bookshop.util.validator.UserValidator.*;

public class SignUpUserAction implements Action {

    private final UserDAO userDAO = new UserDAOImpl();
    private final UserBuilder userBuilder = UserBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();

        User user = userBuilder.fillNew(request);

        isFormatCorrect(request, response, user);
        isEmailOrLoginExists(request, response, user);

        Long generatedId = userDAO.insert(user);
        user.setId(generatedId);
        session.setAttribute(USER, user);
        dispatcher = request.getRequestDispatcher(INDEX_JSP);
        dispatcher.forward(request, response);

    }

    private void isEmailOrLoginExists(HttpServletRequest request, HttpServletResponse response, User user) throws SQLException, ServletException, IOException {
        if (userDAO.isUserExistsByEmail(user.getEmail())) {
            displayErrorMessage(request, response, ParameterConstants.EMAIL_ERROR, ParameterConstants.KEY_ERROR_EMAIL_FORMAT);
        } else if (userDAO.isUserExistsByLogin(user.getUserLogin())) {
            displayErrorMessage(request, response, LOGIN_ERROR, KEY_ERROR_LOGIN_EXISTS);
        }
    }

    private void isFormatCorrect(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
        if (isLoginFormatCorrect(user.getUserLogin())) {
            displayErrorMessage(request, response, ParameterConstants.LOGIN_ERROR, ParameterConstants.KEY_ERROR_LOGIN_FORMAT);
        } else if (!isEmailFormatCorrect(user.getEmail())) {
            displayErrorMessage(request, response, ParameterConstants.EMAIL_ERROR, ParameterConstants.KEY_ERROR_EMAIL_FORMAT);
        } else if (!isPasswordFormatCorrect(request.getParameter(ParameterConstants.USER_PASSWORD))) {
            displayErrorMessage(request, response, PASSWORD_ERROR, ParameterConstants.KEY_ERROR_PASSWORD_FORMAT);
        } else if (!isPhoneFormatCorrect(user.getPhoneNumber())) {
            displayErrorMessage(request, response, PHONE_NUMBER_ERROR, ParameterConstants.KEY_ERROR_PHONE_NUMBER);
        } else if (!isPostalCodeFormatCorrect(user.getPostalCode())) {
            displayErrorMessage(request, response, POSTAL_CODE_ERROR, ParameterConstants.KEY_ERROR_POSTAL_CODE_FORMAT);
        }
    }

    private void displayErrorMessage(HttpServletRequest request, HttpServletResponse response,
                                     String errorName, String errorKey) throws ServletException, IOException {
        request.setAttribute(errorName, getErrorMessage(errorKey));
        dispatcher = request.getRequestDispatcher(SIGN_UP_USER_ACTION_PAGE);
        dispatcher.forward(request, response);
    }
}
