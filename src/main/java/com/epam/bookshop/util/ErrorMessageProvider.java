package com.epam.bookshop.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

import static com.epam.bookshop.constants.ServiceConstants.ERROR_OCCURRED;

public class ErrorMessageProvider {

    private static final String FILENAME = "local";

    private ErrorMessageProvider() {
        throw new UnsupportedOperationException();
    }

    public static String getErrorMessage(String key) {
        ResourceBundle BUNDLE = ResourceBundle.getBundle(FILENAME);
        return BUNDLE.getString(key);
    }

    public static void displayErrorMessage(HttpServletRequest request, HttpServletResponse response, String errorName, String routePage) throws ServletException, IOException {
        request.setAttribute(errorName, ERROR_OCCURRED);
        RequestDispatcher dispatcher = request.getRequestDispatcher(routePage);
        dispatcher.forward(request, response);
    }

}
