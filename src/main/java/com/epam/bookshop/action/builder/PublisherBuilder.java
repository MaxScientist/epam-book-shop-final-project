package com.epam.bookshop.action.builder;


import com.epam.bookshop.entity.Publisher;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.PUBLISHER_HOUSE;
import static com.epam.bookshop.constants.ParameterConstants.PUBLISHER_ID;

public class PublisherBuilder {

    private static PublisherBuilder instance = new PublisherBuilder();

    private PublisherBuilder() {
    }

    public static PublisherBuilder getInstance() {
        if (instance == null) {
            instance = new PublisherBuilder();
        }
        return instance;
    }

    public Publisher fillNewPublisher(HttpServletRequest request) {
        Publisher publisher = new Publisher();
        publisher.setPublishHouse(request.getParameter(PUBLISHER_HOUSE).trim());
        return publisher;
    }

    public Publisher fillToUpdate(HttpServletRequest request) {
        Publisher publisher = fillNewPublisher(request);
        publisher.setId(Long.valueOf(request.getParameter(PUBLISHER_ID)));
        return publisher;
    }
}
