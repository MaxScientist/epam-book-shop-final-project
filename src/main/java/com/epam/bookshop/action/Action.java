package com.epam.bookshop.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public interface Action {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException;
}
