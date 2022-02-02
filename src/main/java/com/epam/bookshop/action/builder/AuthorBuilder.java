package com.epam.bookshop.action.builder;

import com.epam.bookshop.entity.Author;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.AUTHOR_FIRST_NAME;
import static com.epam.bookshop.constants.ParameterConstants.AUTHOR_LAST_NAME;


public class AuthorBuilder {

    private static AuthorBuilder instance = new AuthorBuilder();


    public Author fillNewAuthor(HttpServletRequest request) {
        Author author = new Author();
        author.setFirstName(request.getParameter(AUTHOR_FIRST_NAME).trim());
        author.setLastName(request.getParameter(AUTHOR_LAST_NAME).trim());
        return author;
    }


    public static AuthorBuilder getInstance() {
        if (instance == null) {
            instance = new AuthorBuilder();
        }
        return instance;
    }
}
