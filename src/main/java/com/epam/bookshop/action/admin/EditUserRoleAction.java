package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.builder.UserBuilder;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.database.dao.implementation.UserDAOImpl;
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
import static com.epam.bookshop.constants.ParameterConstants.ROLE_ADMIN_ID;
import static com.epam.bookshop.constants.ParameterConstants.USER_ID;
import static com.epam.bookshop.constants.ServiceConstants.DISPLAY_ALL_USERS_ACTION;

public class EditUserRoleAction implements Action {

    private RequestDispatcher dispatcher;
    private final UserDAO userDAO = new UserDAOImpl();
    private final UserBuilder userBuilder = UserBuilder.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        if (AccessValidator.isAccessDenied(ROLE_ADMIN_ID, session)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }

        Long modifiedUserCurrentId = Long.valueOf(req.getParameter(USER_ID));
        if (userDAO.selectById(modifiedUserCurrentId) != null) {
            userDAO.updateUserRole(userBuilder.fillToUpdateRole(req));
        }
        dispatcher = req.getRequestDispatcher(DISPLAY_ALL_USERS_ACTION);
        dispatcher.forward(req, resp);
    }
}
