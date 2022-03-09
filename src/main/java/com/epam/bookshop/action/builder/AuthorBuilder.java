package com.epam.bookshop.action.builder;

import com.epam.bookshop.entity.Author;

import javax.servlet.http.HttpServletRequest;

import static com.epam.bookshop.constants.ParameterConstants.*;


public class AuthorBuilder {

    private static AuthorBuilder instance = new AuthorBuilder();

    private AuthorBuilder() {
    }

    public Author fillNewAuthor(HttpServletRequest request) {
        Author author = new Author();
        author.setFirstName(request.getParameter(AUTHOR_FIRST_NAME).trim());
        author.setLastName(request.getParameter(AUTHOR_LAST_NAME).trim());
        return author;
    }

    public Author fillToUpdate(HttpServletRequest request) {
        Author author = fillNewAuthor(request);
        author.setId(Long.valueOf(request.getParameter(AUTHOR_ID)));
        return author;
    }

    public static AuthorBuilder getInstance() {
        if (instance == null) {
            instance = new AuthorBuilder();
        }
        return instance;
    }


}
