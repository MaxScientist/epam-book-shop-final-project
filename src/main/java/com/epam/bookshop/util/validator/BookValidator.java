package com.epam.bookshop.util.validator;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class BookValidator {

    private static BookValidator instance = new BookValidator();

    public static BookValidator getInstance() {
        if (instance == null) {
            instance = new BookValidator();
        }
        return instance;
    }

    public boolean isEmptyParamExists(HttpServletRequest request) {
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
                request.getParameter(BOOK_RELEASE_DATE).length() == EMPTY_REQUEST_LENGTH;
    }
}
