package com.epam.bookshop.action.builder;

import com.epam.bookshop.database.dao.GenreDAO;
import com.epam.bookshop.database.dao.implementation.GenreDAOImpl;
import com.epam.bookshop.entity.Genre;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.bookshop.constants.ParameterConstants.*;

public class GenreBuilder {

    private static GenreBuilder instance = new GenreBuilder();
    private final GenreDAO genreDAO = new GenreDAOImpl();

    private GenreBuilder() {
    }

    private Integer getIdForNextGenre() throws SQLException {
        List<Integer> genreListForId = genreDAO.selectAll().stream().
                map(Genre::getId).collect(Collectors.toList());

        Integer idForNextGenre = 1;
        idForNextGenre += Collections.max(genreListForId);
        return idForNextGenre;
    }

    public List<Genre> fillNew(HttpServletRequest request) throws SQLException {
        Integer idForNextGenre = getIdForNextGenre();
        List<Integer> genreLanguageIds = Stream.of(request.getParameterValues(GENRE_LANGUAGE_ID)).
                map(Integer::valueOf).collect(Collectors.toList());
        List<String> genreNames = Stream.of(request.getParameterValues(GENRE_NAME)).
                map(String::valueOf).collect(Collectors.toList());
        List<Genre> genreList = new ArrayList<>();
        for (int i = 0; i < genreLanguageIds.size(); i++) {
            Genre genre = new Genre();
            genre.setId(idForNextGenre);
            genre.setLanguageId(genreLanguageIds.get(i));
            genre.setName(genreNames.get(i));
            genreList.add(genre);
        }
        return genreList;
    }

    public List<Genre> fillUpdate(HttpServletRequest request) throws SQLException {
        List<Genre> genres = fillNew(request);
        List<Integer> updatedGenreIds = Stream.of(request.getParameterValues(GENRE_ID)).
                map(Integer::valueOf).collect(Collectors.toList());
        for (int i = 0; i <genres.size() ; i++) {
            genres.get(i).setId(updatedGenreIds.get(i));
        }
        return genres;
    }

    public static GenreBuilder getInstance() {
        if (instance == null) {
            instance = new GenreBuilder();
        }
        return instance;
    }


}
