package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.LOGIN;
import static com.epam.bookshop.constants.PageNameConstants.MAIN;
import static com.epam.bookshop.constants.ParameterConstants.USER;

public class LoginPageAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            req.getRequestDispatcher(MAIN).forward(req, resp);
        } else {
            req.getRequestDispatcher(LOGIN).forward(req, resp);
        }
    }
}
