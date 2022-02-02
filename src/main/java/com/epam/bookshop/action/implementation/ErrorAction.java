package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class ErrorAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
//        RequestDispatcher dispatcher = req.getRequestDispatcher(TO_MAIN);
//        dispatcher.forward(req, resp);
    }
}
