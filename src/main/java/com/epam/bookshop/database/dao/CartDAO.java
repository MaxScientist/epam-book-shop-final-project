package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.CartItem;

import java.sql.SQLException;
import java.util.List;

public interface CartDAO {

    Long insert(CartItem cartItem) throws SQLException;

    CartItem selectById(Long id) throws SQLException;

    List<CartItem> selectCartItemByUserId(Long userId) throws SQLException;

    boolean isBookExistsInCart(CartItem cartItem) throws SQLException;

    void delete(Long cartItemId) throws SQLException;

    void updateQuantity(CartItem cartItem) throws SQLException;
}
