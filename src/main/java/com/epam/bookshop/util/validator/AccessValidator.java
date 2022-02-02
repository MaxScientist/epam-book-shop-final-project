package com.epam.bookshop.util.validator;

import com.epam.bookshop.entity.User;
import com.epam.bookshop.exceptions.InvalidAccessException;

import javax.servlet.http.HttpSession;

import static com.epam.bookshop.constants.ParameterConstants.ACCESS_STATUS_DELETED_ID;
import static com.epam.bookshop.constants.ParameterConstants.USER;


public class AccessValidator {

    private AccessValidator() throws InvalidAccessException {
        throw new InvalidAccessException();
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
}
