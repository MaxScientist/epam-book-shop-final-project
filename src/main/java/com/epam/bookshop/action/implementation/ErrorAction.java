package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.ERROR_PAGE;

public class ErrorAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher(ERROR_PAGE);
        dispatcher.forward(req, resp);
    }
}
