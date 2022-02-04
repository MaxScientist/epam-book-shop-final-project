package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.entity.CartItem;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private static final String INSERT_CART = "INSERT INTO public.cart (user_id, book_id, quantity) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_CART_ITEMS_BY_USER_ID = "SELECT * FROM public.cart WHERE user_id = ?";
    private static final String SELECT_CART_ITEMS_BY_USER_BOOK_ID = "SELECT * FROM public.cart WHERE user_id = ? AND book_id = ?";
    private static final String UPDATE_QUANTITY_BY_ID = "UPDATE public.cart SET quantity = ? WHERE id = ?";
    private static final String DELETE_BY_ID = "DELETE FROM public.cart WHERE id = ?";
    private static final String SELECT_BY_ID = "SELECT * FROM public.cart WHERE id = ?";


    private CartItem getCartItemByResultSet(ResultSet resultSet) throws SQLException {
        CartItem cartItem = new CartItem();

        cartItem.setId(resultSet.getLong("id"));
        cartItem.setUserId(resultSet.getLong("user_id"));
        cartItem.setBookId(resultSet.getLong("book_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));

        return cartItem;
    }

    @Override
    public Long insert(CartItem cartItem) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Long generatedId = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CART, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, cartItem.getUserId());
            preparedStatement.setLong(2, cartItem.getBook().getId());
            preparedStatement.setInt(3, cartItem.getQuantity());

            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getLong(1);
                }
            }
        } finally {
            LOGGER.info("New cart item has been added " + cartItem);
            connectionPool.returnConnection(connection);
        }
        return generatedId;
    }

    @Override
    public CartItem selectById(Long id) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        CartItem cartItem = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cartItem = getCartItemByResultSet(resultSet);
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return cartItem;
    }

    @Override
    public List<CartItem> selectCartItemByUserId(Long userId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        List<CartItem> cartItems = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CART_ITEMS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cartItems.add(getCartItemByResultSet(resultSet));
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return cartItems;
    }

    @Override
    public boolean isBookExistsInCart(CartItem cartItem) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        boolean isExist;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CART_ITEMS_BY_USER_BOOK_ID)) {
            preparedStatement.setLong(1, cartItem.getUserId());
            preparedStatement.setLong(2, cartItem.getBook().getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExist = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExist;
    }

    @Override
    public void delete(Long cartItemId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, cartItemId);
            preparedStatement.executeUpdate();
        } finally {
            LOGGER.info("Cart item has been added " + cartItemId);
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public void updateQuantity(CartItem cartItem) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUANTITY_BY_ID)) {
            preparedStatement.setInt(1, cartItem.getQuantity());
            preparedStatement.setLong(2, cartItem.getId());
            preparedStatement.executeUpdate();
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}
