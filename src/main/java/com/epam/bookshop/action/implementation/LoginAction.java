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
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.*;
import static com.epam.bookshop.util.validator.AccessValidator.isBannedOrDeleted;


public class LoginAction implements Action {
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        RequestDispatcher dispatcher;
        if (user != null) {
            dispatcher = request.getRequestDispatcher(MAIN);
            dispatcher.forward(request, response);
        } else {
            String email = request.getParameter(ParameterConstants.USER_EMAIL).trim();
            String cipheredPassword = DigestUtils.md5Hex(request.getParameter(ParameterConstants.USER_PASSWORD));

            user = userDAO.selectUserByEmailPassword(email, cipheredPassword);

            if (user == null) {
                request.setAttribute(EMAIL_PASSWORD_ERROR, ErrorMessageProvider.getErrorMessage(KEY_ERROR_USER_NOT_EXISTS));
                dispatcher = request.getRequestDispatcher(LOGIN_PAGE_ACTION);
                dispatcher.forward(request, response);
            } else if (isBannedOrDeleted(user)) {
                request.setAttribute(ParameterConstants.USER_NOT_EXISTS_ERROR, ErrorMessageProvider.getErrorMessage(KEY_ERROR_USER_NOT_EXISTS));
                dispatcher = request.getRequestDispatcher(LOGIN_PAGE_ACTION);
                dispatcher.forward(request, response);
            } else {
                session.setAttribute(USER, user);
                LOGGER.info("User '"+ user.getUserLogin() + "' has logged into the system.");
                dispatcher = request.getRequestDispatcher(TO_MAIN);
                dispatcher.forward(request, response);
            }
        }
    }
}
