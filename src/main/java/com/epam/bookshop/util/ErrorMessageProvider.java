package com.epam.bookshop.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ResourceBundle;

public class ErrorMessageProvider {

    private static final String FILENAME = "local";
    private static final String LOCALE = "locale";

    private static ResourceBundle BUNDLE;

    private ErrorMessageProvider (){
        throw new UnsupportedOperationException();
    }

    public static String getErrorMessage(HttpServletRequest request, String key) {
        HttpSession session  =   request.getSession();
//
//        String language = (String) session.getAttribute(LOCALE);
//        Locale sessionLocale = new Locale(language);
        BUNDLE = ResourceBundle.getBundle(FILENAME);
        return BUNDLE.getString(key);
    }
}
