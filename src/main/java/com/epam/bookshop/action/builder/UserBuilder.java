package com.epam.bookshop.action.builder;

import com.epam.bookshop.constants.ParameterConstants;
import com.epam.bookshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.*;
public class UserBuilder {
    private static UserBuilder instance = new UserBuilder();
    public final static boolean NOT_BANNED = false;

    private UserBuilder() {
    }

    public User fillNew(HttpServletRequest request) {
        User user = new User();
        user.setFirstName(request.getParameter(USER_FIRST_NAME));
        user.setLastName(request.getParameter(USER_LAST_NAME));
        user.setPhoneNumber(request.getParameter(ParameterConstants.USER_PHONE_NUMBER).trim());
        user.setEmail(request.getParameter(ParameterConstants.USER_EMAIL).trim());
        user.setPassword(DigestUtils.md5Hex(request.getParameter(ParameterConstants.USER_PASSWORD)));
        user.setAddress(request.getParameter(ParameterConstants.USER_ADDRESS).trim());
        user.setPostalCode(request.getParameter(ParameterConstants.USER_POSTAL_CODE).trim());
        user.setBanned(NOT_BANNED);
        user.setStatusId(ACCESS_STATUS_ACTIVE_ID);
        user.setUserLogin(request.getParameter(ParameterConstants.USER_LOGIN).trim());
        user.setRoleId(ROLE_USER_ID);
        return user;
    }


    public User fillToUpdate(HttpServletRequest request, User user) {
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setRoleId(user.getRoleId());
        updateUser.setBanned(user.isBanned());
        updateUser.setStatusId(user.getStatusId());

        if (request.getParameter(USER_FIRST_NAME) != null) {
            if (!request.getParameter(USER_FIRST_NAME).equals(user.getFirstName())) {
                updateUser.setFirstName(request.getParameter(USER_FIRST_NAME).trim());
            }
        }
        if (request.getParameter(USER_LAST_NAME) != null) {
            if (!request.getParameter(USER_LAST_NAME).equals(user.getLastName())) {
                updateUser.setLastName(request.getParameter(USER_LAST_NAME).trim());
            }
        }
        if (request.getParameter(ParameterConstants.USER_LOGIN) != null) {
            if (!request.getParameter(ParameterConstants.USER_LOGIN).equals(user.getUserLogin())) {
                updateUser.setUserLogin(request.getParameter(ParameterConstants.USER_LOGIN).trim());
            }
        }
        if (request.getParameter(ParameterConstants.USER_POSTAL_CODE) != null) {
            if (!request.getParameter(ParameterConstants.USER_POSTAL_CODE).equals(user.getPostalCode())) {
                updateUser.setPostalCode(request.getParameter(ParameterConstants.USER_POSTAL_CODE).trim());
            }
        }
        if (request.getParameter(ParameterConstants.USER_ADDRESS) !=null) {
            if (!request.getParameter(ParameterConstants.USER_ADDRESS).equals(user.getAddress())) {
                updateUser.setAddress(request.getParameter(ParameterConstants.USER_ADDRESS).trim());
            }
        }
        if (request.getParameter(ParameterConstants.USER_PHONE_NUMBER) != null) {
            if (!request.getParameter(ParameterConstants.USER_PHONE_NUMBER).equals(user.getPhoneNumber())) {
                updateUser.setPhoneNumber(request.getParameter(ParameterConstants.USER_PHONE_NUMBER).trim());
            }
        }
        if (request.getParameter(ParameterConstants.USER_PASSWORD)!=null) {
            if (!request.getParameter(ParameterConstants.USER_PASSWORD).equals(user.getPassword())) {
                updateUser.setPassword(DigestUtils.md5Hex(request.getParameter(ParameterConstants.USER_PASSWORD)));
            }
        }

        updateUser = user;
        return updateUser;
    }

    public User fillToUpdateRole(HttpServletRequest req) {
        User user = new User();
        user.setId(Long.valueOf(req.getParameter(USER_ID)));
        user.setRoleId(Integer.parseInt(req.getParameter(ROLE_ID)));
        return user;
    }

    public User fillToUpdateAccess(HttpServletRequest req) {
        User user = new User();
        user.setId(Long.valueOf(req.getParameter(USER_ID)));
        user.setStatusId(Integer.parseInt(req.getParameter(STATUS_ID)));
        user.setBanned(Boolean.parseBoolean(req.getParameter(IS_USER_BANNED)));
        return user;
    }

    public static UserBuilder getInstance() {
        if (instance == null) {
            instance = new UserBuilder();
        }
        return instance;
    }


}
