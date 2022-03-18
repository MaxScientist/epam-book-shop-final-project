package com.epam.bookshop.action.builder;

import com.epam.bookshop.constants.ParameterConstants;
import com.epam.bookshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class UserBuilder {
    public final static boolean NOT_BANNED = false;
    private static UserBuilder instance = new UserBuilder();

    private UserBuilder() {
    }

    public static UserBuilder getInstance() {
        if (instance == null) {
            instance = new UserBuilder();
        }
        return instance;
    }

    public User fillNew(HttpServletRequest request) {
        User user = new User();
        user.setFirstName(request.getParameter(USER_FIRST_NAME).trim());
        user.setLastName(request.getParameter(USER_LAST_NAME).trim());
        user.setPhoneNumber(request.getParameter(ParameterConstants.USER_PHONE_NUMBER).trim());
        user.setEmail(request.getParameter(ParameterConstants.USER_EMAIL).trim());
        user.setPassword(DigestUtils.md5Hex(request.getParameter(ParameterConstants.USER_PASSWORD)));
        user.setAddress(request.getParameter(ParameterConstants.USER_ADDRESS).trim());
        user.setPostalCode(request.getParameter(ParameterConstants.USER_POSTAL_CODE).trim());
        user.setUserLogin(request.getParameter(ParameterConstants.USER_LOGIN).trim());
        user.setBanned(NOT_BANNED);
        user.setStatusId(ACCESS_STATUS_ACTIVE_ID);
        user.setRoleId(ROLE_USER_ID);
        return user;
    }

    public User fillToUpdate(HttpServletRequest request, User user) {
        userDetailsUpdate(request, user);
        return user;
    }

    private void userFirstNameUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(USER_FIRST_NAME).equals(updateUser.getFirstName())) {
            updateUser.setFirstName(request.getParameter(USER_FIRST_NAME).trim());
        }
    }

    private void userLastNameUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(USER_LAST_NAME).equals(updateUser.getLastName())) {
            updateUser.setLastName(request.getParameter(USER_LAST_NAME).trim());
        }
    }

    private void userLoginUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(ParameterConstants.USER_LOGIN).equals(updateUser.getUserLogin())) {
            updateUser.setUserLogin(request.getParameter(ParameterConstants.USER_LOGIN).trim());
        }
    }

    private void userPostalCodeUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(ParameterConstants.USER_POSTAL_CODE).equals(updateUser.getPostalCode())) {
            updateUser.setPostalCode(request.getParameter(ParameterConstants.USER_POSTAL_CODE).trim());
        }
    }

    private void userAddressUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(ParameterConstants.USER_ADDRESS).equals(updateUser.getAddress())) {
            updateUser.setAddress(request.getParameter(ParameterConstants.USER_ADDRESS).trim());
        }
    }

    private void userPhoneNumberUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(ParameterConstants.USER_PHONE_NUMBER).equals(updateUser.getPhoneNumber())) {
            updateUser.setPhoneNumber(request.getParameter(ParameterConstants.USER_PHONE_NUMBER).trim());
        }
    }

    private void userPasswordUpdate(HttpServletRequest request, User updateUser) {
        if (!request.getParameter(ParameterConstants.USER_PASSWORD).equals(updateUser.getPassword())) {
            updateUser.setPassword(DigestUtils.md5Hex(request.getParameter(ParameterConstants.USER_PASSWORD)));
        }
    }

    private void userDetailsUpdate(HttpServletRequest request, User updateUser) {
        userFirstNameUpdate(request, updateUser);
        userLastNameUpdate(request, updateUser);
        userLoginUpdate(request, updateUser);
        userAddressUpdate(request, updateUser);
        userPostalCodeUpdate(request, updateUser);
        userPhoneNumberUpdate(request, updateUser);
        userPasswordUpdate(request, updateUser);
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


}
