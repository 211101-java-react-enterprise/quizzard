package com.revature.quizzard.util.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConnectionFactory {

    private static final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final Properties props = new Properties();
    private List<Connection> connectionPool;
    private List<Connection> usedConnections;
    private final int poolSize = 10;

    private ConnectionFactory() {
        try {
            Class.forName("org.postgresql.Driver");
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            props.load(loader.getResourceAsStream("db.properties"));
            initalizePool();
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Error(t);
        }
    }

    public static ConnectionFactory getInstance() {
        return connectionFactory;
    }

    public Connection getConnection() {
        Connection conn = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(conn);
        return conn;
    }

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("username"), props.getProperty("password"));
    }

    public void releaseConnection(Connection conn) {
        usedConnections.remove(conn);
        connectionPool.add(conn);
    }

    private void initalizePool() throws SQLException {

        connectionPool = new ArrayList<>();
        usedConnections = new ArrayList<>();

        for (int i = 0; i < poolSize; i++) {
            connectionPool.add(createConnection());
        }

    }

}
