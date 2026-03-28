package com.udhartracker.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseManager is responsible for creating the database tables
 * on the very first run. If tables already exist, it does nothing.
 */
public class DatabaseManager {

    /**
     * Call this once at startup. Creates both tables if they don't exist.
     */
    public static void initialize() {
        createCustomerTable();
        createTransactionTable();
        System.out.println("Database ready.");
    }

    private static void createCustomerTable() {
        // id         : auto-incremented primary key
        // name       : customer's name (required)
        // phone      : contact number (optional)
        // created_at : timestamp auto-filled by SQLite
        String sql = "CREATE TABLE IF NOT EXISTS customers ("
                + "id         INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name       TEXT    NOT NULL,"
                + "phone      TEXT,"
                + "created_at TEXT    DEFAULT (datetime('now','localtime'))"
                + ");";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating customers table: " + e.getMessage());
        }
    }

    private static void createTransactionTable() {
        // type is either "UDHAR" (credit given) or "PAYMENT" (money received back)
        // customer_id links back to the customers table via foreign key
        String sql = "CREATE TABLE IF NOT EXISTS transactions ("
                + "id          INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "customer_id INTEGER NOT NULL,"
                + "amount      REAL    NOT NULL,"
                + "type        TEXT    NOT NULL,"
                + "note        TEXT,"
                + "date        TEXT    DEFAULT (datetime('now','localtime')),"
                + "FOREIGN KEY (customer_id) REFERENCES customers(id)"
                + ");";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating transactions table: " + e.getMessage());
        }
    }
}
