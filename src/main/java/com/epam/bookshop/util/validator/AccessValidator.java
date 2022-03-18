package com.epam.bookshop.util.validator;

import com.epam.bookshop.entity.User;
import com.epam.bookshop.exceptions.InvalidAccessException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.*;


public class AccessValidator {

    private AccessValidator() throws InvalidAccessException {
        throw new InvalidAccessException("User is banned or user's access is denied.");
    }

    public static boolean isAccessDenied(Integer acceptedRoleId, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            return isBannedOrDeleted(user) || !acceptedRoleId.equals(user.getRoleId());
        }
        return true;

    }

    public static boolean isBannedOrDeleted(User user) {
        return user.isBanned() || user.getStatusId().equals(ACCESS_STATUS_DELETED_ID);
    }

    public static void isAdminRole(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {
        if (isAccessDenied(ROLE_ADMIN_ID, session)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(ERROR_PAGE);
            dispatcher.forward(req, resp);
        }
    }
}
