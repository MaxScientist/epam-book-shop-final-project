package com.epam.bookshop.action.admin;

import com.epam.bookshop.action.Action;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.PageNameConstants.ADMIN_PANEL_PAGE;

public class AdminPanelPageAction implements Action {

    private RequestDispatcher dispatcher;
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        dispatcher = req.getRequestDispatcher(ADMIN_PANEL_PAGE);
        dispatcher.forward(req, resp);
    }
}
