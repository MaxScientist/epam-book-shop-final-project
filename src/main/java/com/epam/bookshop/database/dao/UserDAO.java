package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {


    Long insert(User user) throws SQLException;

    void updateUserAccess(User user) throws SQLException;

    User selectUserByEmailPassword(String email, String password) throws SQLException;

    void update(User user) throws SQLException;

    User selectById(Long id) throws SQLException;

    boolean isUserExistsByEmail(String email) throws SQLException;

    boolean isUserExistsByLogin(String login) throws SQLException;

    List<User> selectAll() throws SQLException;

    void updateUserRole(User user) throws SQLException;



}
