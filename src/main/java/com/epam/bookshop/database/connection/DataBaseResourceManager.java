package com.epam.bookshop.database.connection;

import java.util.Locale;
import java.util.ResourceBundle;

public class DataBaseResourceManager {

    private final static String DB_PROPERTIES = "db";
    private final static DataBaseResourceManager INSTANCE = new DataBaseResourceManager();

    private ResourceBundle resourceBundle = ResourceBundle.getBundle(DB_PROPERTIES, Locale.ENGLISH);

    public static DataBaseResourceManager getInstance() {
        return INSTANCE;
    }

    public String getValue(String key) {
        return resourceBundle.getString(key);
    }
}
