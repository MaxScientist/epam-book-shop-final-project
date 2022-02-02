package com.epam.bookshop.database.dao;

import java.sql.SQLException;

public interface AccessStatusDAO {

    boolean isAccessStatusExists(Integer statusId) throws SQLException;
}
