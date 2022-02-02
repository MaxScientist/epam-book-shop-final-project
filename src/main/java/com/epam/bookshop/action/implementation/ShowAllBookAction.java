package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.ServiceConstants.CATALOG;

public class ShowAllBookAction implements Action {

    private RequestDispatcher dispatcher;

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {

        req.getRequestDispatcher(CATALOG).forward(req,resp);
    }
}
