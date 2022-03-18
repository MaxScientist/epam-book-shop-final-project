package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.UserBuilder;
import com.epam.bookshop.database.dao.AccessStatusDAO;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.database.dao.implementation.AccessStatusDAOImpl;
import com.epam.bookshop.database.dao.implementation.UserDAOImpl;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.ParameterConstants.*;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_ALL_USERS_ACTION;

public class EditUserCredentialsAction implements Action {

    private final UserDAO userDAO = new UserDAOImpl();
    private final UserBuilder userBuilder = UserBuilder.getInstance();
    private final AccessStatusDAO accessStatusDAO = new AccessStatusDAOImpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;

        AccessValidator.isAdminRole(req, resp, session);

        updateUserRole(req);
        dispatcher = req.getRequestDispatcher(DISPLAY_ALL_USERS_ACTION);
        dispatcher.forward(req, resp);
    }

    private void updateUserRole(HttpServletRequest req) throws SQLException {
        Long modifiedUserCurrentId = Long.valueOf(req.getParameter(USER_ID));
        Integer modifiedUserStatusId = Integer.valueOf(req.getParameter(STATUS_ID));

        if (userDAO.selectById(modifiedUserCurrentId) != null &&
                accessStatusDAO.isAccessStatusExists(modifiedUserStatusId)) {
            userDAO.updateUserAccess(userBuilder.fillToUpdateAccess(req));
            if (userDAO.selectById(modifiedUserCurrentId).getRoleId() != Integer.parseInt(req.getParameter(ROLE_ID)))
                userDAO.updateUserRole(userBuilder.fillToUpdateRole(req));
        }
        if (userDAO.selectById(modifiedUserCurrentId) != null &&
                userDAO.selectById(modifiedUserCurrentId).getRoleId() != Integer.parseInt(req.getParameter(ROLE_ID)))
            userDAO.updateUserRole(userBuilder.fillToUpdateRole(req));
    }
}
