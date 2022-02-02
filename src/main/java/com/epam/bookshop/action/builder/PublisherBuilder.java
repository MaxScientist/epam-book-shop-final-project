package com.epam.bookshop.action.builder;

import com.epam.bookshop.entity.Publisher;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.PUBLISHER_HOUSE;

public class PublisherBuilder {  // need to delete

    private static PublisherBuilder instance = new PublisherBuilder();

    public Publisher fillNewPublisher(HttpServletRequest request) {
        Publisher publisher = new Publisher();
        publisher.setPublishHouse(request.getParameter(PUBLISHER_HOUSE));
        return publisher;
    }

    public static PublisherBuilder getInstance() {
        if (instance == null) {
            instance = new PublisherBuilder();
        }
        return instance;
    }
}
