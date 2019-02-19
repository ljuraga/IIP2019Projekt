package com.unipu.coinbet.sql;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * Klasa za spajanje sa bazom.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class ConnectToDatabase {
    private static String url = "jdbc:mysql://localhost:3306/coin-bet?useSSL=true";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "intelij";
    private static String password = "intelij";
    private static Connection con;

    /**
     * Metoda za dobivanje conekcije na bazu.
     */
    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        }catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }

    /**
     * Metoda za dobivanje SimpleDriverDataSource-a.
     */
    public static SimpleDriverDataSource getSimpleDriverDataSource() {
        SimpleDriverDataSource ds = new SimpleDriverDataSource();

        try {
            ds.setDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }

        ds.setUsername(username);
        ds.setPassword(password);
        ds.setUrl(url);

        return ds;
    }
}
