package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
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
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.EDIT_USERS_PAGE;
import static com.epam.bookshop.constants.PageNameConstants.LOGIN;
import static com.epam.bookshop.constants.ParameterConstants.ROLE_ADMIN_ID;

public class DisplayAllUsersAction implements Action {

    private RequestDispatcher dispatcher;
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(LOGIN);
            dispatcher.forward(req, resp);
        }
        List<User> userList = userDAO.selectAll();
        req.setAttribute(ParameterConstants.USERS, userList);
        dispatcher = req.getRequestDispatcher(EDIT_USERS_PAGE);
        dispatcher.forward(req, resp);
    }
}
