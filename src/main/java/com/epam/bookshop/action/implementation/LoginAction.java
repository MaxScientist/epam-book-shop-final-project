package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.constants.ParameterConstants;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.database.dao.implementation.UserDAOImpl;
import com.epam.bookshop.entity.User;
import com.epam.bookshop.util.ErrorMessageProvider;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.LOGIN_PAGE_ACTION;
import static com.epam.bookshop.constants.ServiceConstants.TO_MAIN;
import static com.epam.bookshop.util.validator.AccessValidator.isBannedOrDeleted;

public class LoginAction implements Action {
    private final UserDAO userDAO = new UserDAOImpl();
    private final static String userLogIn = "User '%s' has logged into the web-site.";
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        RequestDispatcher dispatcher;
        if (user != null) {
            dispatcher = request.getRequestDispatcher(MAIN);
        } else {
            String email = request.getParameter(ParameterConstants.USER_EMAIL).trim();
            String cipheredPassword = DigestUtils.md5Hex(request.getParameter(ParameterConstants.USER_PASSWORD));

            dispatcher = checkAccountEntrance(request, session, email, cipheredPassword);
        }
        dispatcher.forward(request, response);
    }

    private RequestDispatcher checkAccountEntrance(HttpServletRequest request, HttpSession session, String email, String cipheredPassword) throws SQLException {
        User user;
        RequestDispatcher dispatcher;
        user = userDAO.selectUserByEmailPassword(email, cipheredPassword);

        if (user == null) {
            request.setAttribute(EMAIL_PASSWORD_ERROR, ErrorMessageProvider.getErrorMessage(KEY_ERROR_SIGN_IN));
            dispatcher = request.getRequestDispatcher(LOGIN_PAGE_ACTION);
        } else if (isBannedOrDeleted(user)) {
            request.setAttribute(ParameterConstants.USER_NOT_EXISTS_ERROR, ErrorMessageProvider.getErrorMessage(KEY_ERROR_USER_NOT_EXISTS));
            dispatcher = request.getRequestDispatcher(LOGIN_PAGE_ACTION);
        } else {
            session.setAttribute(USER, user);
            LOGGER.info(String.format(userLogIn, user.getUserLogin()));
            dispatcher = request.getRequestDispatcher(TO_MAIN);
        }
        return dispatcher;
    }
}
