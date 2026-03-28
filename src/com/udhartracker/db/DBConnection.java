package com.udhartracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection manages a single shared connection to the SQLite database.
 * This is a Singleton pattern — only one connection is ever open at a time.
 * The DB file lives at data/udhar.db — created automatically on first run.
 */
public class DBConnection {

    // Path to the SQLite file — relative to where you run the program from
    private static final String DB_URL = "jdbc:sqlite:data/udhar.db";

    // Single shared connection object
    private static Connection connection = null;

    /**
     * Returns the active connection, or opens a new one if none exists.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
    }

    /**
     * Cleanly closes the connection when the app exits.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}