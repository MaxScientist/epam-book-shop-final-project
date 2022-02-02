package com.epam.bookshop.controller;


import com.epam.bookshop.action.Action;
import com.epam.bookshop.action.ActionFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
public class ShopServiceController extends HttpServlet {

    private static long serialVersionUID = 1L;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());


    private final ActionFactory actionFactory = ActionFactory.getInstance();

    public ShopServiceController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, ParseException {
        try {
            String pathInfo = request.getServletPath() + request.getPathInfo();
            Action action = ActionFactory.getInstance().getAction(pathInfo);
            action.execute(request, response);
        } catch (ParseException | SQLException e) {
            LOGGER.error("Couldn't execute request", e);
            throw e;
        }
    }
}
