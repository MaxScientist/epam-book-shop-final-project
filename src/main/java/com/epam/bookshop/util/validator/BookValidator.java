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
        return request.getParameter(AUTHOR_FIRST_NAME).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(AUTHOR_LAST_NAME).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(LANGUAGE_ID).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_TITLE).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(GENRE_ID).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(PUBLISHER_HOUSE).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_BINDING).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_DESCRIPTION).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_ISBN).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_PAGES).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_PRICE).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_RELEASE_DATE).length() == EMPTY_REQUEST_LENGTH ||
                request.getPart(BOOK_IMAGE).getSubmittedFileName().length() == EMPTY_REQUEST_LENGTH;
    }

    public boolean isEmptyUpdateDataExists(HttpServletRequest request) {
        return request.getParameter(BOOK_TITLE).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(GENRE_ID).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(PUBLISHER_HOUSE).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_BINDING).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_DESCRIPTION).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_ISBN).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_PAGES).length() == EMPTY_REQUEST_LENGTH ||
                request.getParameter(BOOK_PRICE).length() == EMPTY_REQUEST_LENGTH;
    }
}
