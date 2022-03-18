package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.PageNameConstants.SIGN_UP;
import static com.epam.bookshop.constants.ParameterConstants.USER;

public class SignUpPageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);

        RequestDispatcher dispatcher;
        if (user != null) {
            dispatcher = req.getRequestDispatcher(MAIN);
        } else {
            dispatcher = req.getRequestDispatcher(SIGN_UP);
        }
        dispatcher.forward(req, resp);
    }
}
