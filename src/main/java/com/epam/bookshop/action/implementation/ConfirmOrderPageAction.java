package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.CONFIRM_PAGE;

public class ConfirmOrderPageAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();

        req.getRequestDispatcher(CONFIRM_PAGE).forward(req,resp);
    }
}
