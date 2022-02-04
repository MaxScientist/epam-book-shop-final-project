package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.UserDAO;
import com.epam.bookshop.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private static final String INSERT_USER = "INSERT INTO public.user " +
            " (first_name, last_name, phone_number, email, password, address, postal_code, is_banned, status_id, user_login, role) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?)";


    private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM public.user WHERE email = ?";
    private static final String SELECT_USER_BY_USER_LOGIN = "SELECT * FROM public.user WHERE user_login = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM public.user WHERE id = ?";
    private static final String SELECT_USER_BY_EMAIL_PASSWORD = "SELECT * FROM public.user WHERE (email = ? AND password = ?)";
    private static final String SELECT_ALL_USERS = "SELECT * FROM public.user";
    private static final String UPDATE_USER_ACCESS = "UPDATE public.user SET is_banned = ?, status_id = ? WHERE id = ?";
    private static final String UPDATE = "UPDATE public.user SET first_name= ?,last_name= ?, phone_number=?, email=?, password=?, address=?," +
            " postal_code=? WHERE id=?";
    private static final String UPDATE_USER_ROLE = "UPDATE public.user SET role = ? WHERE id = ?";

    private User getUserByResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setAddress(resultSet.getString("address"));
        user.setPostalCode(resultSet.getString("postal_code"));
        user.setBanned(resultSet.getBoolean("is_banned"));
        user.setStatusId(resultSet.getInt("status_id"));
        user.setUserLogin(resultSet.getString("user_login"));
        user.setRoleId(resultSet.getInt("role"));
        return user;
    }

    @Override
    public Long insert(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Long generatedId = null;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPhoneNumber());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getPostalCode());
            preparedStatement.setBoolean(8, user.isBanned());
            preparedStatement.setInt(9, user.getStatusId());
            preparedStatement.setString(10, user.getUserLogin());
            preparedStatement.setInt(11, user.getRoleId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getLong(1);
                }
            }
        } finally {
            LOGGER.info("New user has been added " + user);
            connectionPool.returnConnection(connection);
        }
        return generatedId;
    }

    @Override
    public void updateUserAccess(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ACCESS)) {
            preparedStatement.setBoolean(1, user.isBanned());
            preparedStatement.setInt(2, user.getStatusId());
            preparedStatement.setLong(3, user.getId());
            preparedStatement.executeUpdate();
        } finally {
            LOGGER.info("User access has been updated " + user);
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User selectUserByEmailPassword(String email, String password) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = getUserByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }

        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, user.getPhoneNumber());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getAddress());
            preparedStatement.setString(5, user.getPostalCode());
            preparedStatement.setLong(6, user.getId());
        } finally {
            LOGGER.info("User has been updated " + user);
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public User selectById(Long id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = getUserByResultSet(resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return user;
    }

    @Override
    public boolean isUserExistsByEmail(String email) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        boolean isExist;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExist = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExist;
    }

    @Override
    public boolean isUserExistsByLogin(String login) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        boolean isExists;
        try( PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_USER_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExists = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }


    @Override
    public List<User> selectAll() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<User> users = new ArrayList<>();
        User user;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = getUserByResultSet(resultSet);
                users.add(user);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return users;
    }

    @Override
    public void updateUserRole(User user) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_ROLE)) {
            preparedStatement.setInt(1, user.getRoleId());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }

    }

}
