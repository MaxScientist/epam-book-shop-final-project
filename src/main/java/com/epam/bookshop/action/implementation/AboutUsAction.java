package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.bookshop.constants.PageNameConstants.ABOUT_US_PAGE;

public class AboutUsAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(ABOUT_US_PAGE);
        dispatcher.forward(req, resp);
    }
}
