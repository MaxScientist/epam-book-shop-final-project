package com.epam.bookshop.database.dao;

import java.sql.SQLException;

public interface LanguageDAO {

    Integer selectIdByName(String name) throws SQLException;

    boolean isLanguageExists(Integer valueOf) throws SQLException;
}
