package com.epam.bookshop.database.connection;

import org.jasypt.util.text.AES256TextEncryptor;

import java.util.ResourceBundle;

public final class DataBaseSupplier {
    public static final String DB_DRIVER;
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PASSWORD;
    public static final int POOL_SIZE;
    private static final String saltPassword = "my_secret_salt";
    private static final ResourceBundle properties = ResourceBundle.getBundle("ConnectionPool");

    private DataBaseSupplier(){
    }

    static {
        DB_DRIVER = properties.getString("db.driver");
        DB_URL = properties.getString("db.url");
        DB_USER = properties.getString("db.user");
        DB_PASSWORD = decryptingDBPassword(properties.getString("db.password"));
        POOL_SIZE = Integer.parseInt(properties.getString("db.poolsize"));
    }

    private static String decryptingDBPassword(String encryptedPassword){
        AES256TextEncryptor decryptor = new AES256TextEncryptor();
        decryptor.setPassword(saltPassword);
        return decryptor.decrypt(encryptedPassword);
    }
}
