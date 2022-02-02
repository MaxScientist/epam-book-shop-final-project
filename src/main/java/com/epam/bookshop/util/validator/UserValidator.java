package com.epam.bookshop.util.validator;

import java.util.regex.Pattern;

public class UserValidator {

    private final static String EMAIL_FORMAT_REGEX = "([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)";
    private final static String NAME_FORMAT_REGEX = "[a-zA-Z0-9]+";
    private final static String PASSWORD_FORMAT_REGEX = "(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}";
    private final static String PHONE_FORMAT_REGEX = "[0-9]{10}";
    private final static String POSTAL_CODE_FORMAT_REGEX = "[0-9]{6}";

    private UserValidator() { throw new UnsupportedOperationException();}

    public static boolean isEmailFormatCorrect(String email) {
        return Pattern.matches(EMAIL_FORMAT_REGEX, email);
    }

    public static boolean isLoginFormatCorrect(String name) {
        return Pattern.matches(NAME_FORMAT_REGEX, name);
    }

    public static boolean isPasswordFormatCorrect(String password) {
        return Pattern.matches(PASSWORD_FORMAT_REGEX, password);
    }

    public static boolean isPhoneFormatCorrect(String phone) {
        return Pattern.matches(PHONE_FORMAT_REGEX, phone);
    }

    public static boolean isPostalCodeFormatCorrect(String postalCode) {
        return Pattern.matches(POSTAL_CODE_FORMAT_REGEX, postalCode);
    }

}
