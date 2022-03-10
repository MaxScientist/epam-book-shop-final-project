package com.epam.bookshop.util.validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class BookValidator {

    private static BookValidator instance = new BookValidator();

    public static BookValidator getInstance() {
        if (instance == null) {
            instance = new BookValidator();
        }
        return instance;
    }

    public boolean isISBNValid(String isbn) {
        return Pattern.matches("\\d{13}", isbn);
    }

    public boolean isEmptyParamExists(HttpServletRequest request) throws IOException, ServletException {
        return (request.getParameter(AUTHOR_FIRST_NAME).isEmpty()) ||
                (request.getParameter(AUTHOR_LAST_NAME).isEmpty()) ||
                (request.getParameter(LANGUAGE_ID).isEmpty()) ||
                (request.getParameter(BOOK_TITLE).isEmpty()) ||
                (request.getParameter(GENRE_ID).isEmpty()) ||
                request.getParameter(PUBLISHER_HOUSE).isEmpty() ||
                request.getParameter(BOOK_BINDING).isEmpty() ||
                request.getParameter(BOOK_DESCRIPTION).isEmpty() ||
                request.getParameter(BOOK_ISBN).isEmpty() ||
                request.getParameter(BOOK_PAGES).isEmpty() ||
                request.getParameter(BOOK_PRICE).isEmpty() ||
                request.getParameter(BOOK_RELEASE_DATE).isEmpty() ||
                request.getPart(BOOK_IMAGE).getSubmittedFileName().isEmpty();
    }

    public boolean isBookDataEmpty(HttpServletRequest request) {
        return request.getParameter(BOOK_TITLE).isEmpty() ||
                request.getParameter(GENRE_ID).isEmpty() ||
                request.getParameter(PUBLISHER_HOUSE).isEmpty() ||
                request.getParameter(BOOK_BINDING).isEmpty() ||
                request.getParameter(BOOK_DESCRIPTION).isEmpty() ||
                request.getParameter(BOOK_ISBN).isEmpty()  ||
                request.getParameter(BOOK_PAGES).isEmpty() ||
                request.getParameter(BOOK_PRICE).isEmpty();
    }
}
