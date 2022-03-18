package com.epam.bookshop.database.dao.implementation;

import com.epam.bookshop.database.connection.ConnectionPool;
import com.epam.bookshop.database.dao.BookDAO;
import com.epam.bookshop.entity.Book;
import com.epam.bookshop.util.ImageUtil;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {

    private ConnectionPool connectionPool;
    private Connection connection;

    private static final String bookAdded = "New book has been added %s.";
    private static final String bookDeleted = "%s has been updated.";

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private static final String SELECT_ALL_BOOK = "SELECT * FROM public.book";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM public.book where id=?";
    private static final String UPDATE_BOOK = "UPDATE public.book SET title=?, access_status_id=?, genre_id=?, image = ?, language_id=? WHERE id = ?";
    private static final String INSERT_BOOK = "INSERT INTO public.book(title, access_status_id, genre_id, image, language_id) VALUES(?,?,?,?,?)";
    private static final String SELECT_ALL_BY_FILTER = "SELECT * FROM public.book b INNER JOIN public.genre g ON b.genre_id = g.id where g.id = ? AND g.language_id = ?";
    private static final String SELECT_BOOK_BY_TITLE = "SELECT * FROM public.book WHERE title = ?";

    private Book getBookByResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAccessStatusId(resultSet.getInt("access_status_id"));
        book.setGenreId(resultSet.getLong("genre_id"));
        InputStream imageBinary = resultSet.getBinaryStream("image");
        String base64Image = ImageUtil.imageToBase(imageBinary);
        book.setBookImage(base64Image);
        book.setLanguageId(resultSet.getInt("language_id"));
        return book;
    }


    @Override
    public Long insert(Book book) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        Long generatedId = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAccessStatusId());
            preparedStatement.setLong(3, book.getGenreId());
            InputStream inputStreamImage = ImageUtil.baseToImage(book.getBookImage());
            preparedStatement.setBinaryStream(4, inputStreamImage);
            preparedStatement.setInt(5, book.getLanguageId());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    generatedId = resultSet.getLong(1);
                }
            }
        } finally {
            LOGGER.info(String.format(bookAdded,book));
            connectionPool.returnConnection(connection);
        }
        return generatedId;
    }


    @Override
    public Book selectById(Long bookId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        Book book = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)) {
            preparedStatement.setLong(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = getBookByResultSet(resultSet);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return book;
    }

    @Override
    public void update(Book book) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setInt(2, book.getAccessStatusId());
            preparedStatement.setLong(3, book.getGenreId());
            preparedStatement.setBinaryStream(4, ImageUtil.baseToImage(book.getBookImage()));
            preparedStatement.setInt(5, book.getLanguageId());
            preparedStatement.setLong(6, book.getId());
            preparedStatement.executeUpdate();
        } finally {
            LOGGER.info(String.format(bookDeleted, book));
            connectionPool.returnConnection(connection);
        }
    }

    @Override
    public List<Book> selectAllBooks() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();

        List<Book> bookList = new ArrayList<>();
        Book book;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BOOK)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    book = getBookByResultSet(resultSet);
                    bookList.add(book);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return bookList;
    }

    @Override
    public List<Book> selectAllByFilter(Integer genreId, Integer localeId) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Book> bookList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_FILTER)) {
            preparedStatement.setInt(1, genreId);
            preparedStatement.setInt(2, localeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = getBookByResultSet(resultSet);
                    bookList.add(book);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return bookList;
    }

    @Override
    public boolean isBookExists(String title) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        boolean isExists;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_TITLE)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                isExists = resultSet.next();
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return isExists;
    }

    @Override
    public List<Book> selectByTitle(String title) throws SQLException {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.takeConnection();
        List<Book> books = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_TITLE)) {
            preparedStatement.setString(1, title);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Book book = getBookByResultSet(resultSet);
                    books.add(book);
                }
            }
        } finally {
            connectionPool.returnConnection(connection);
        }
        return books;
    }
}
