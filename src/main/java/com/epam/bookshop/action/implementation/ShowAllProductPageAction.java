package com.epam.bookshop.action.implementation;

import com.epam.bookshop.action.Action;
import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.database.dao.implementation.BookDAOImpl;
import com.epam.bookshop.entity.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static com.epam.bookshop.constants.PageNameConstants.CATALOG_PAGE;
import static com.epam.bookshop.constants.ParameterConstants.BOOKS;

public class ShowAllProductPageAction implements Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ParseException, SQLException, ServletException, IOException {
        BookDAO bookDAO = new BookDAOImpl();
        List<Book> bookList =  bookDAO.selectAllBooks();

        req.setAttribute(BOOKS, bookList);
        req.getRequestDispatcher(CATALOG_PAGE).forward(req,resp);
    }
}
