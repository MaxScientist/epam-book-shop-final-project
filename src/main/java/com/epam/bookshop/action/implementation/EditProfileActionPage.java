package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.entity.User;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.EDIT_PROFILE_PAGE;
import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.USER;

public class EditProfileActionPage implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;

        User user = (User) session.getAttribute(USER);

        if (AccessValidator.isBannedOrDeleted(user)) {
            dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        } else {
            dispatcher = req.getRequestDispatcher(EDIT_PROFILE_PAGE);
        }
        dispatcher.forward(req, resp);
    }
}
