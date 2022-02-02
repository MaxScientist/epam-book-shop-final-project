package com.epam.bookshop.util.validator;

import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.database.dao.OrderDAO;
import com.epam.bookshop.database.dao.implementation.CartDAOImpl;
import com.epam.bookshop.database.dao.implementation.OrderDAOImpl;

import java.sql.SQLException;
import java.util.List;

public class CartItemValidator {

    private static CartItemValidator instance = new CartItemValidator();
    private final CartDAO cartDAO = new CartDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();


    public boolean isCartItemsExist(List<Long> cartItemIds) throws SQLException {

        for (Long cartItemId : cartItemIds) {
            if (cartDAO.selectById(cartItemId) == null) {
                return false;
            }
        }
        return true;
    }

    public static CartItemValidator getInstance() {
        if (instance == null) {
            instance = new CartItemValidator();
        }
        return instance;
    }
}
