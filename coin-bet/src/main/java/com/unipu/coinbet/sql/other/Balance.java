package com.unipu.coinbet.sql.other;

import com.unipu.coinbet.other.Hash;
import com.unipu.coinbet.sql.ConnectToDatabase;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Klasa za stanje računa korisnika.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Balance {

    private static Connection conn = null;
    private static float balance;

    /**
     * Metoda za ažuriranje stanja računa korisnika.
     *
     * @param balance atribut za stanje računa
     * @param user atribut za korisničko ime
     */
    public Balance(float balance, String user) {
        this.balance = balance;

        try {
            conn = ConnectToDatabase.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "UPDATE users SET Balance = " + balance + " WHERE User = '" + Hash.hash_to_SHA_512(user) + "';";
            stmt.executeUpdate(sql);
            conn.close();
        }catch (Exception e) {
            System.err.println("Balance exception!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metoda za dobavljanje stanja računa korisnika.
     *
     * @param user atribut za korisničko ime
     */
    public static float getBalance(String user) {
        user = Hash.hash_to_SHA_512(user);
        try {
            SimpleDriverDataSource ds = ConnectToDatabase.getSimpleDriverDataSource();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            String sql = "SELECT Balance FROM users WHERE User = ?";
            balance = (float) jdbcTemplate.queryForObject(sql, new Object[] { user }, Float.class);
        }catch (Exception e) {
            System.err.println("getBalance exception!");
            System.err.println(e.getMessage());
        }
        return balance;
    }
}
