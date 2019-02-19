package com.unipu.coinbet.sql.other;

import com.unipu.coinbet.sql.ConnectToDatabase;

import java.sql.*;

/**
 * Klasa za rezultat.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Result {
    private static Connection conn = null;

    /**
     * Metoda za ažuriranje rezultata utakmica u transakcijama.
     */
    public static void updateGames() {
        try {
            conn = ConnectToDatabase.getConnection();
            String sql;
            Statement stmt = conn.createStatement();

            sql = "UPDATE games t1, transactions t2 SET t2.Result = 'Home' WHERE (t1.Date, t1.Time, t1.Game, t1.League) = (t2.GameDate, t2.GameTime, t2.Game, t2.League) AND (t1.ResultHome = 'X') = (t2.Result = 'No result')";
            stmt.executeUpdate(sql);
            sql = "UPDATE games t1, transactions t2 SET t2.Result = 'Draw' WHERE (t1.Date, t1.Time, t1.Game, t1.League) = (t2.GameDate, t2.GameTime, t2.Game, t2.League) AND (t1.ResultDraw = 'X') = (t2.Result = 'No result')";
            stmt.executeUpdate(sql);
            sql = "UPDATE games t1, transactions t2 SET t2.Result = 'Away' WHERE (t1.Date, t1.Time, t1.Game, t1.League) = (t2.GameDate, t2.GameTime, t2.Game, t2.League) AND (t1.ResultAway = 'X') = (t2.Result = 'No result')";
            stmt.executeUpdate(sql);

            conn.close();
        }catch (Exception e) {
            System.err.println("updateGames exception!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metoda za zaključavanje transakcija.
     */
    private static void lockTransaction(){
        try {
            String sql;
            conn = ConnectToDatabase.getConnection();
            Statement stmt = conn.createStatement();
            sql = "UPDATE transactions SET Paid = TRUE WHERE Paid = FALSE AND Result = 'Home';";
            stmt.executeUpdate(sql);
            sql = "UPDATE transactions SET Paid = TRUE WHERE Paid = FALSE AND Result = 'Draw';";
            stmt.executeUpdate(sql);
            sql = "UPDATE transactions SET Paid = TRUE WHERE Paid = FALSE AND Result = 'Away';";
            stmt.executeUpdate(sql);
            conn.close();
        }catch (Exception e) {
            System.err.println("lockTransaction exception!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metoda za isplatu svih transakcija koje imaju rezultat a nisu zaključane.
     */
    public static void checkForResults(){
        conn = ConnectToDatabase.getConnection();
        Statement stmt = null;
        String query = "SELECT User, OddCategory, OddValue, OddMultiplayer, Result, Paid FROM transactions";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String user;
            String oddCategory;
            float oddValue;
            int oddMultiplayer;
            String result;
            boolean paid;

            while(rs.next()){
                user = rs.getString("User");
                oddCategory = rs.getString("OddCategory");
                oddValue = rs.getFloat("OddValue");
                oddMultiplayer = rs.getInt("OddMultiplayer");
                result = rs.getString("Result");
                paid = rs.getBoolean("Paid");

                if (!paid){
                    float winnings = 0;
                    if (oddCategory.equals(result)) {
                        winnings = (oddValue * oddMultiplayer) * 2;
                        float balance = Balance.getBalance(user);
                        float newBalance = balance + winnings;
                        new Balance(newBalance, user);

                        lockTransaction();

                        if (!result.equals("No result")){
                            System.out.println(user + " earned " + winnings + " coins " + "and the transaction is locked:" + paid);
                        }
                    }else if (!oddCategory.equals(result)) {
                        lockTransaction();

                        if (!result.equals("No result")){
                            System.out.println(user + " lost " + winnings + " coins " + "and the transaction is locked:" + paid);
                        }
                    }
                }

            }
            stmt.close();
        }catch (SQLException e ) {
            System.err.println("checkForResults exception!");
            e.printStackTrace();
        }
    }
}
