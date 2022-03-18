package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.CONFIRM_PAGE;

public class ConfirmOrderPageAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CONFIRM_PAGE).forward(req, resp);
    }
}
