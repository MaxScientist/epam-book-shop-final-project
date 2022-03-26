package com.epam.bookshop.action.builder;

import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.database.dao.CartDAO;
import com.epam.bookshop.database.dao.EditionDAO;
import com.epam.bookshop.database.dao.implementation.BookDAOImpl;
import com.epam.bookshop.database.dao.implementation.CartDAOImpl;
import com.epam.bookshop.database.dao.implementation.EditionDAOImpl;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.entity.CartItem;
import com.epam.bookshop.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class CartItemBuilder {

    private static CartItemBuilder instance = new CartItemBuilder();

    private final CartDAO cartDAO = new CartDAOImpl();
    private final BookDAO bookDAO = new BookDAOImpl();
    private final EditionDAO editionDAO = new EditionDAOImpl();

    private CartItemBuilder() {
    }

    public static CartItemBuilder getInstance() {
        if (instance == null) {
            instance = new CartItemBuilder();
        }
        return instance;
    }

    public CartItem fillNew(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Book book = bookDAO.selectById(Long.valueOf(request.getParameter(BOOK_ID)));

        CartItem cartItem = new CartItem();
        cartItem.setUserId(user.getId());
        cartItem.setBook(book);
        cartItem.setQuantity(DEFAULT_CART_ITEM_QUANTITY);
        return cartItem;
    }

    public List<CartItem> fillCartItemByIds(List<Long> cartIds) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem;
        for (Long cartItemId : cartIds) {
            cartItem = cartDAO.selectById(cartItemId);
            if (cartItem != null) {
                cartItem.setBook(bookDAO.selectById(cartItem.getBookId()));
                cartItems.add(cartItem);
            }
        }
        return cartItems;
    }

    public BigInteger calculateTotalPrice(List<CartItem> cartItems) throws SQLException {
        BigInteger totalPrice = new BigInteger(ZERO_AMOUNT);
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(editionDAO.selectByBookId(cartItem.getBookId()).getPrice());
        }
        return totalPrice;
    }

    public List<CartItem> fillUserCartItems(Long userId, Integer localeId) throws SQLException {
        BookBuilder bookBuilder = BookBuilder.getInstance();
        List<CartItem> cartItems = cartDAO.selectCartItemByUserId(userId);
        for (CartItem cartItem : cartItems) {
            Book book = bookDAO.selectById(cartItem.getBookId());
            bookBuilder.fillGivenUnit(book, localeId);
            cartItem.setUserId(userId);
            cartItem.setBook(book);
        }
        return cartItems;
    }

    public CartItem fillToUpdate(HttpServletRequest req) throws SQLException {
        CartItem cartItem = cartDAO.selectById(Long.parseLong(req.getParameter(CART_ITEM_ID)));
        cartItem.setQuantity(Integer.parseInt(req.getParameter(QUANTITY)));
        return cartItem;
    }


}
