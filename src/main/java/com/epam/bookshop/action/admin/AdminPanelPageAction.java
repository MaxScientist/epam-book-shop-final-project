package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.util.validator.AccessValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.bookshop.constants.PageNameConstants.ADMIN_PANEL_PAGE;

public class AdminPanelPageAction implements Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AccessValidator.isAdminRole(req, resp, req.getSession());

        RequestDispatcher dispatcher = req.getRequestDispatcher(ADMIN_PANEL_PAGE);
        dispatcher.forward(req, resp);
    }
}
