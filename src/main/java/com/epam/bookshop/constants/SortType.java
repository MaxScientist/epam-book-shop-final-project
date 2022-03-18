package com.epam.bookshop.constants;

import static com.epam.bookshop.constants.ParameterConstants.*;

public enum SortType {

    TITLE_ASC(KEY_TITLE_ASCENDING),
    TITLE_DESC(KEY_TITLE_DESCENDING),
    PRICE_ASC(KEY_PRICE_ASC),
    PRICE_DESC(KEY_PRICE_DESC);

    private final String key;

    SortType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
