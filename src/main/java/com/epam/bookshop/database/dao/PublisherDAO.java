package com.epam.bookshop.database.dao;

import com.epam.bookshop.entity.Publisher;

import java.sql.SQLException;
import java.util.List;

public interface PublisherDAO {

    List<Publisher> selectAll() throws SQLException;

    Long insert(Publisher publisher) throws SQLException;

    void update(Publisher publisher) throws SQLException;

    Publisher selectById(Long id) throws SQLException;

    Publisher selectByPublishHouse(String publishHouse) throws SQLException;
}
