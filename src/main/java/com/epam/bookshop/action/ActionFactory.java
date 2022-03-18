package com.epam.bookshop.action;


import com.epam.bookshop.action.admin.*;
import com.epam.bookshop.action.implementation.*;

import java.util.HashMap;
import java.util.Map;

import static com.epam.bookshop.constants.ServiceConstants.*;


public class ActionFactory {

    private static final Map<String, Action> ACTION_MAP = new HashMap<>();
    private static final ActionFactory SERVICE_FACTORY = new ActionFactory();

    static {
        ACTION_MAP.put(ADD_AUTHOR_TO_BOOK_ACTION, new AddAuthorToBookAction());
        ACTION_MAP.put(ADD_NEW_BOOK_ACTION, new AddNewBookAction());
        ACTION_MAP.put(ADD_NEW_BOOK_PAGE, new AddNewBookPageAction());
        ACTION_MAP.put(ADD_NEW_GENRE_ACTION, new AddNewGenreAction());
        ACTION_MAP.put(ADD_TO_CART, new AddToCartAction());
        ACTION_MAP.put(ADMIN_PANEL_ACTION, new AdminPanelPageAction());
        ACTION_MAP.put(BOOK_DETAILS_ACTION, new ProductDetailsAction());
        ACTION_MAP.put(CART, new CartPageAction());
        ACTION_MAP.put(CHANGE_LANGUAGE_ACTION, new ChangeLanguageAction());
        ACTION_MAP.put(CHECK_OUT_ALL_ACTION, new CheckOutAllAction());
        ACTION_MAP.put(CHECK_OUT_SELECTED_ITEM, new CheckOutSelectedAction());
        ACTION_MAP.put(CONFIRM_ORDER, new ConfirmOrderAction());
        ACTION_MAP.put(CONFIRM_ORDER_ACTION, new ConfirmOrderPageAction());
        ACTION_MAP.put(CUSTOMER, new LoginAction());
        ACTION_MAP.put(DELETE_AUTHOR_FROM_BOOK_ACTION, new DeleteAuthorAction());
        ACTION_MAP.put(DELETE_CART_ITEM_ACTION, new DeleteCartItemAction());
        ACTION_MAP.put(DISPLAY_ALL_GENRES, new DisplayAllGenresAction());
        ACTION_MAP.put(DISPLAY_ALL_ORDERS_ACTION, new DisplayAllOrdersAction());
        ACTION_MAP.put(DISPLAY_ALL_USERS_ACTION, new DisplayAllUsersAction());
        ACTION_MAP.put(DISPLAY_CART_ACTION, new DisplayCartAction());
        ACTION_MAP.put(DISPLAY_CUSTOMER_ORDER_ACTION, new DisplayCustomerAction());
        ACTION_MAP.put(EDIT_AUTHOR_ACTION, new EditAuthorAction());
        ACTION_MAP.put(EDIT_BOOK_PAGE_ACTION, new EditBookPageAction());
        ACTION_MAP.put(EDIT_GENRE_ACTION, new EditGenreAction());
        ACTION_MAP.put(EDIT_ORDER_STATUS_ACTION, new EditOrderStatusAction());
        ACTION_MAP.put(EDIT_PROFILE_ACTION, new EditProfileAction());
        ACTION_MAP.put(EDIT_PROFILE_PAGE_ACTION, new EditProfileActionPage());
        ACTION_MAP.put(EDIT_USER_CREDENTIALS_ACTION, new EditUserCredentialsAction());
        ACTION_MAP.put(ERROR_ACTION, new ErrorAction());
        ACTION_MAP.put(FILTER_GENRE, new FilterGenreAction());
        ACTION_MAP.put(LOGIN_PAGE_ACTION, new LoginPageAction());
        ACTION_MAP.put(LOGOUT_ACTION, new LogOutAction());
        ACTION_MAP.put(SET_QUANTITY_ACTION, new SetQuantityAction());
        ACTION_MAP.put(SIGN_UP_USER_ACTION, new SignUpUserAction());
        ACTION_MAP.put(SIGN_UP_USER_ACTION_PAGE, new SignUpPageAction());
        ACTION_MAP.put(SORT_BOOK_ACTION, new SortBookAction());
        ACTION_MAP.put(TO_MAIN, new MainPageAction());
        ACTION_MAP.put(EDIT_BOOK_ACTION, new EditBookAction());
        ACTION_MAP.put(SEARCH_ACTION, new SearchAction());
        ACTION_MAP.put(ABOUT_US_ACTION, new AboutUsAction());
    }

    private ActionFactory() {
    }

    public static ActionFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public Action getAction(String request) {
        Action action = ACTION_MAP.get(ERROR_ACTION);

        for (Map.Entry<String, Action> pair : ACTION_MAP.entrySet()) {
            if (request.equalsIgnoreCase(pair.getKey())) {
                action = ACTION_MAP.get(pair.getKey());
            }
        }
        return action;
    }
}

