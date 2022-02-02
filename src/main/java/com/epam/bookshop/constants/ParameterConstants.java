package com.epam.bookshop.constants;

public class ParameterConstants {

    public final static String AUTHOR_ID = "authorId";
    public final static Integer DEFAULT_CART_ITEM_QUANTITY = 1;
    public final static Integer EMPTY_REQUEST_LENGTH = 0;
    public final static Integer LOCALE_ENGLISH_ID = 1;
    public final static Integer LOCALE_RUSSIAN_ID = 2;
    public final static Integer MAX_IMAGE_SIZE = 10 * 1024 * 1024;
    public final static Integer MIN_IMAGE_SIZE = 0;
    public final static Integer ORDER_STATUS_IN_PROCESSING = 1;
    public final static Integer ROLE_ADMIN_ID = 1;
    public final static Long DEFAULT_COVER_ID = 7L;
    public final static String AUTHOR_FIRST_NAME = "authorFirstName";
    public final static String AUTHOR_LAST_NAME = "authorLastName";
    public final static String AUTHOR_NAME_ERROR = "authorNameError";
    public final static String BOOK_BINDING = "bookBinding";
    public final static String BOOK_DESCRIPTION = "bookDescription";
    public final static String BOOK_IMAGE = "bookImage";
    public final static String BOOK_INFO = "bookInfo";
    public final static String BOOK_ISBN  = "bookISBN";
    public final static String BOOK_PAGES = "bookPages";
    public final static String BOOK_PRICE = "bookPrice";
    public final static String BOOK_RELEASE_DATE = "bookReleaseDate";
    public final static String BOOK_TITLE = "bookTitle";
    public final static String CART_ITEM_ID = "cartItemId";
    public final static String CHECKED_GENRE_IDS = "checkedGenreIds";
    public final static String CONTENT_TYPE_JPEG = "image/jpeg";
    public final static String CONTENT_TYPE_JPG = "image/jpg";
    public final static String CONTENT_TYPE_PNG = "image/png";
    public final static String EMPTY_FIELD_ERROR = "emptyFieldError";
    public final static String GENRES = "genres";
    public final static String GENRE_ID = "genreId";
    public final static String GENRE_LANGUAGE_ID = "genreLanguageId";
    public final static String GENRE_NAME = "genreName";
    public final static String GENRE_NAMES = "genreNames";
    public final static String HIDDEN_INPUT_ERROR = "hiddenInputError";
    public final static String IS_USER_BANNED = "isUserBanned";
    public final static String KEY_PRICE_ASC = "select.price.asc";
    public final static String KEY_PRICE_DESC = "select.price.desc";
    public final static String KEY_TITLE = "select.title";
    public final static String LANGUAGE_ID = "languageId";
    public final static String NOT_UNIQUE_AUTHOR_ERROR = "notUniqueAuthorError";
    public final static String NOT_UNIQUE_BOOK_AUTHOR_ERROR = "notUniqueBookAuthorError";
    public final static String NOT_UNIQUE_PUBLISHER_ERROR = "notUniquePublisherError";
    public final static String ORDER = "order";
    public final static String ORDERS = "orders";
    public final static String ORDER_ID = "orderId";
    public final static String ORDER_STATUSES = "orderStatuses";
    public final static String ORDER_STATUS_ID = "orderStatusId";
    public final static String PUBLISHER_HOUSE = "publisherHouse";
    public final static String REFERER = "referer";
    public final static String ROLE_ID = "roleId";
    public final static String SELECTED_SORT_TYPE = "selectedSortType";
    public final static String SORT_TYPE = "sortType";
    public final static String SORT_TYPES = "sortTypes";
    public final static String STATUS_ID = "statusId";
    public final static String USERS = "users";
    public final static String USER_ID = "userId";
    public final static String USER_ORDERS = "userOrders";
    public static Integer ACCESS_STATUS_ACTIVE_ID = 1;
    public static Integer ACCESS_STATUS_DELETED_ID = 2;
    public static Integer ROLE_USER_ID = 0;
    public static String EMAIL_PASSWORD_ERROR = "emailPasswordError";
    public static String KEY_ERROR_SIGN_IN = "small.error.sign.in";
    public static String KEY_ERROR_USER_NOT_EXISTS = "small.error.user.not.exists";
    public static String LOCALE = "locale";
    public static String LOCALE_ID = "localeId";
    public static String USER = "user";
    public static String USER_ADDRESS = "userAddress";
    public static String USER_EMAIL = "userEmail";
    public static String USER_LOGIN = "userLogin";
    public static String USER_NOT_EXISTS_ERROR = "userNotExistsError";
    public static String USER_PASSWORD = "userPassword";
    public static String USER_PHONE_NUMBER = "userPhoneNumber";
    public static String USER_POSTAL_CODE = "userPostalCode";
    public static final String ACTION = "action";
    public static final String BOOKS = "books";
    public static final String BOOK_ID = "bookId";
    public static final String CART_ITEMS = "cartItems";
    public static final String CART_TOTAL_PRICE = "cartTotalPrice";
    public static final String EMAIL_ERROR = "emailError";
    public static final String KEY_ERROR_EMAIL_FORMAT = "small.error.email.format";
    public static final String KEY_ERROR_LOGIN_FORMAT = "small.error.login.format";
    public static final String KEY_ERROR_PASSWORD_FORMAT = "small.error.password.format";
    public static final String KEY_ERROR_PHONE_NUMBER = "small.error.phone.format";
    public static final String KEY_ERROR_POSTAL_CODE_FORMAT = "small.error.postal.format";
    public static final String LOGIN_ERROR = "loginError";
    public static final String PASSWORD_ERROR = "passwordError";
    public static final String PHONE_NUMBER_ERROR = "phoneNumberError";
    public static final String POSTAL_CODE_ERROR = "postalCodeError";
    public static final String QUANTITY = "quantity";
    public static final String USER_FIRST_NAME = "userFirstName";
    public static final String USER_LAST_NAME = "userLastName";



    public Integer getRoleUserId() {
        return ROLE_USER_ID;
    }

    public Integer getRoleAdminId() {
        return ROLE_ADMIN_ID;
    }

    public Integer getAccessStatusActiveId() {
        return ACCESS_STATUS_ACTIVE_ID;
    }

    public Integer getAccessStatusDeletedId() {
        return ACCESS_STATUS_DELETED_ID;
    }

    public Integer getLocaleEnglishId() {
        return LOCALE_ENGLISH_ID;
    }

    public Integer getLocaleRussianId() {
        return LOCALE_RUSSIAN_ID;
    }
}
