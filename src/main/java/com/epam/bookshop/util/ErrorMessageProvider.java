package com.epam.bookshop.util;

import java.util.ResourceBundle;

public class ErrorMessageProvider {

    private static final String FILENAME = "local";

    private static ResourceBundle BUNDLE;

    private ErrorMessageProvider() {
        throw new UnsupportedOperationException();
    }

    public static String getErrorMessage(String key) {
        BUNDLE = ResourceBundle.getBundle(FILENAME);
        return BUNDLE.getString(key);
    }


}
