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
import java.util.logging.Logger;

import static com.epam.bookshop.constants.ParameterConstants.USER;
import static com.epam.bookshop.constants.ServiceConstants.INDEX_JSP;

public class LogOutAction implements Action {

    static Logger LOGGER = Logger.getLogger(LogOutAction.class.getName());

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute(USER);
        LOGGER.info("User '"+user.getUserLogin()+"' has logged out from the web-site.");
        session.setAttribute(USER,null);
        req.getRequestDispatcher(INDEX_JSP).forward(req, resp);
    }
}
