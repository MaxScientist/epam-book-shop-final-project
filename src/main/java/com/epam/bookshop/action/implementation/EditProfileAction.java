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

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.EDIT_PROFILE_PAGE_ACTION;
import static com.epam.bookshop.util.ErrorMessageProvider.displayErrorMessage;
import static com.epam.bookshop.util.validator.UserValidator.*;


public class EditProfileAction implements Action {

    private final UserDAO userDAO = new UserDAOImpl();
    private final UserBuilder userBuilder = UserBuilder.getInstance();
    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        User oldUser = (User) session.getAttribute(USER);

        if (AccessValidator.isBannedOrDeleted(oldUser)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        } else {
            User updatedUser = userBuilder.fillToUpdate(req, oldUser);
            userFormatParameterChecking(req, resp, session, oldUser, updatedUser);
        }
        dispatcher.forward(req, resp);
    }

    private void userFormatParameterChecking(HttpServletRequest req, HttpServletResponse resp, HttpSession session, User oldUser, User updatedUser) throws ServletException, IOException, SQLException {
        if (updatedUser.equals(oldUser)) {
            dispatcher = req.getRequestDispatcher(EDIT_PROFILE_PAGE_ACTION);
        } else if (isLoginFormatCorrect(updatedUser.getUserLogin())) {
            displayErrorMessage(req, resp, ParameterConstants.LOGIN_ERROR, EDIT_PROFILE_PAGE_ACTION);
        } else if (!isPasswordFormatCorrect(req.getParameter(ParameterConstants.USER_PASSWORD))) {
            displayErrorMessage(req, resp, PASSWORD_ERROR, EDIT_PROFILE_PAGE_ACTION);
        } else if (!isPhoneFormatCorrect(updatedUser.getPhoneNumber())) {
            displayErrorMessage(req, resp, PHONE_NUMBER_ERROR, EDIT_PROFILE_PAGE_ACTION);
        } else if (!isPostalCodeFormatCorrect(updatedUser.getPostalCode())) {
            displayErrorMessage(req, resp, POSTAL_CODE_ERROR, EDIT_PROFILE_PAGE_ACTION);
        } else {
            userDAO.update(updatedUser);
            session.setAttribute(USER, updatedUser);
            dispatcher = req.getRequestDispatcher(EDIT_PROFILE_PAGE_ACTION);
        }
    }
}
