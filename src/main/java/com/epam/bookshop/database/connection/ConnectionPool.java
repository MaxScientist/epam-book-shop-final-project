package com.epam.bookshop.database.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public final class ConnectionPool {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    private final int POOL_SIZE = DataBaseSupplier.POOL_SIZE;
    private static volatile ConnectionPool instance;
    private final BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);

    private ConnectionPool() {
        loadDrivers();
        initPoolData();

    }

    private void loadDrivers() {
        try {
            Class.forName(DataBaseSupplier.DB_DRIVER);
        } catch (ClassCastException | ClassNotFoundException e) {
           LOGGER.error(e);
           e.printStackTrace();
        }
    }

    private void initPoolData() {
        Connection connection;

        while (connectionQueue.size() < POOL_SIZE) {
            try {
                connection = DriverManager.getConnection(DataBaseSupplier.DB_URL, DataBaseSupplier.DB_USER, DataBaseSupplier.DB_PASSWORD);

                connectionQueue.put(connection);
            } catch (InterruptedException | SQLException e) {
                LOGGER.warn(e);
                e.printStackTrace();
            }
        }
    }


    public static ConnectionPool getInstance() {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                }
            }
        }
        return localInstance;
    }

    public synchronized Connection takeConnection() {
        Connection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
        return connection;
    }

    public synchronized void returnConnection(Connection connection) {
        if ((connection != null) && (connectionQueue.size() <= POOL_SIZE)) {
            try {
                connectionQueue.put(connection);
            } catch (InterruptedException e) {
                LOGGER.error(e);
                e.printStackTrace();
            }
        }
    }
}
