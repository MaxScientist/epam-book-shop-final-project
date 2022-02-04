package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.database.dao.LanguageDAO;
import com.epam.bookshop.database.dao.implementation.LanguageDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import static com.epam.bookshop.constants.ParameterConstants.LOCALE;
import static com.epam.bookshop.constants.ParameterConstants.LOCALE_ID;
import static com.epam.bookshop.constants.ServiceConstants.INDEX_JSP;

public class ChangeLanguageAction implements Action {

    private RequestDispatcher dispatcher;


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        HttpSession session = req.getSession();
        String selectLocalization = req.getParameter(LOCALE);

        LanguageDAO languageDAO = new LanguageDAOImpl();
        Integer languageId = languageDAO.selectIdByName(selectLocalization);
        if (languageId != null) {
            session.setAttribute(LOCALE, selectLocalization);
            session.setAttribute(LOCALE_ID, languageId);
        }
        dispatcher = req.getRequestDispatcher(INDEX_JSP);
        dispatcher.forward(req, resp);
    }
}
