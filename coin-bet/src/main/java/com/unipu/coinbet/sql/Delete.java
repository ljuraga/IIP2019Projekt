package com.unipu.coinbet.sql;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Klasa za brisanje iz baze.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Delete {
    private static Connection conn = null;

    /**
     * Metoda za brisanje košarice korisnika.
     *
     * @param user atribut za korisničko ime
     */
    public static void cart(String user) {
        try {
            conn = ConnectToDatabase.getConnection();
            String sql;
            Statement stmt = conn.createStatement();

            sql = "DELETE FROM cart WHERE User = '" + user + "'";

            stmt.executeUpdate(sql);

            conn.close();
        }catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metoda za brisanje oklade iz košarice korisnika.
     *
     * @param user atribut za korisničko ime
     * @param gameDate atribut za datum utakmice
     * @param gameTime atribut za vrijeme utakmice
     * @param game atribut za ime utakmice
     * @param league atribut za ligu
     * @param oddCategory atribut za kategoriju oklade (home, draw, away)
     * @param oddValue atribut za vrijednost oklade
     */
    public static void cartItem(String user, String gameDate, String gameTime, String game, String league, String oddCategory, float oddValue) {
        try {
            conn = ConnectToDatabase.getConnection();
            String sql;
            Statement stmt = conn.createStatement();

            sql = "DELETE FROM cart WHERE User = '" + user + "' AND GameDate = '" + gameDate + "' AND GameTime = '" + gameTime + "' AND Game = '" + game + "' AND League = '" + league + "' AND OddCategory = '" + oddCategory + "' AND OddValue = " + oddValue + "";

            stmt.executeUpdate(sql);

            conn.close();
        }catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }
}
