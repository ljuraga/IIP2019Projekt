package com.unipu.coinbet.sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Klasa za dodavanje u bazu.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Add {

    private static Connection conn = null;

    /**
     * Klasa za metodu koja dodaje novu okladu u košaricu.
     */
    public static class Cart {
        /**
         * Metoda za dodavanje nove oklade u košaricu.
         *
         * @param user atribut za korisničko ime
         * @param date atribut za datum utakmice
         * @param time atribut za vrijeme utakmice
         * @param game atribut za ime utakmice
         * @param league atribut za ligu
         * @param oddCategory atribut za kategoriju oklade (home, draw, away)
         * @param oddValue atribut za vrijednost oklade
         * @param oddMultiplayer atribut za Multiplayer
         */
        public Cart(String user, String date, String time, String game, String league, String oddCategory, String oddValue, int oddMultiplayer) {
            try {
                conn = ConnectToDatabase.getConnection();

                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO cart(User, GameDate, GameTime, Game, League, OddCategory, OddValue, OddMultiplayer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, user);
                pstmt.setString(2, date);
                pstmt.setString(3, time);
                pstmt.setString(4, game);
                pstmt.setString(5, league);
                pstmt.setString(6, oddCategory);
                pstmt.setString(7, oddValue);
                pstmt.setInt(8, oddMultiplayer);
                pstmt.executeUpdate();

                conn.close();
            }catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Klasa za metodu koja dodaje sve oklade iz košarice u transakcije.
     */
    public static class Transaction {
        /**
         * Metoda za dodavanje svih oklada iz košarice u transakcije.
         *
         * @param user atribut za korisničko ime
         */
        public Transaction(String user) {
            try {
                if (!user.equals("Guest")) {
                    conn = ConnectToDatabase.getConnection();

                    String transactionDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                    String transactionTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    Statement stmt = conn.createStatement();
                    String sql = "INSERT INTO transactions ( User, TransactionDate, TransactionTime, GameDate, GameTime, Game, League, OddCategory, OddValue, OddMultiplayer, Result, Paid ) SELECT DISTINCT User, '" + transactionDate + "', '" + transactionTime + "', GameDate, GameTime, Game, League, OddCategory, OddValue, OddMultiplayer, 'No result', FALSE FROM cart WHERE User='" + user + "'";
                    stmt.executeUpdate(sql);

                    Delete.cart(user);

                    conn.close();
                }
            }catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
    }
}
