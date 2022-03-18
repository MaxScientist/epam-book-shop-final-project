package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.LOGIN;
import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.USER;

public class LoginPageAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        RequestDispatcher dispatcher;
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            dispatcher = req.getRequestDispatcher(MAIN);
        } else {
            dispatcher = req.getRequestDispatcher(LOGIN);
        }
        dispatcher.forward(req, resp);
    }
}
