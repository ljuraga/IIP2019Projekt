package com.unipu.coinbet.sql.other;

import com.unipu.coinbet.sql.ConnectToDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa za okalde.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Bet {

    private static List<String> temp;

    /**
     * Metoda za provjeru dali je oklada več u košarici.
     *
     * @param username atribut za korisničko ime
     * @param gamesDate atribut za datum utakmice
     * @param gameTime atribut za vrijeme utakmice
     * @param game atribut za ime utakmice
     * @param league atribut za ligu
     * @param oddCategory atribut za kategoriju oklade (home, draw, away)
     * @param oddValue atribut za vrijednost oklade
     */
    public static boolean existsInCart(String username, String gamesDate, String gameTime, String game, String league, String oddCategory, float oddValue){
        String search = gamesDate + " " + gameTime + " " + game + " " + league + " " + oddCategory + " " + oddValue;

        Connection conn = ConnectToDatabase.getConnection();
        Statement stmt = null;
        String query = "SELECT GameDate, GameTime, Game, League, OddCategory, OddValue FROM cart WHERE User = '" + username + "'";

        return compare(search, conn, stmt, query);
    }

    /**
     * Metoda za provjeru dali je transakcija več postoji.
     *
     * @param username atribut za korisničko ime
     * @param gamesDate atribut za datum utakmice
     * @param gameTime atribut za vrijeme utakmice
     * @param game atribut za ime utakmice
     * @param league atribut za ligu
     * @param oddCategory atribut za kategoriju oklade (home, draw, away)
     * @param oddValue atribut za vrijednost oklade
     */
    public static boolean transacrtionExist(String username, String gamesDate, String gameTime, String game, String league, String oddCategory, float oddValue){
        String search = gamesDate + " " + gameTime + " " + game + " " + league + " " + oddCategory + " " + oddValue;

        Connection conn = ConnectToDatabase.getConnection();
        Statement stmt = null;
        String query = "SELECT GameDate, GameTime, Game, League, OddCategory, OddValue FROM transactions WHERE User = '" + username + "'";

        return compare(search, conn, stmt, query);
    }

    /**
     * Metoda za provjeru oklade ili transakcije.
     *
     * @param search atribut za korisničko ime
     * @param conn atribut za konekciju s bazom
     * @param stmt atribut za Statement
     * @param query atribut za ime query
     */
    private static boolean compare(String search, Connection conn, Statement stmt, String query) {
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String existingGameDate;
            String existingGameTime;
            String existingGame;
            String existingLeague;
            String existingOddCategory;
            float existingOddValue;

            temp = new ArrayList();

            while(rs.next()){
                existingGameDate = rs.getString("GameDate");
                existingGameTime = rs.getString("GameTime");
                existingGame = rs.getString("Game");
                existingLeague = rs.getString("League");
                existingOddCategory = rs.getString("OddCategory");
                existingOddValue = rs.getFloat("OddValue");
                temp.add(existingGameDate + " " + existingGameTime + " " + existingGame + " " + existingLeague + " " + existingOddCategory + " " + existingOddValue);
            }


            for(String str: temp) {
                if(str.trim().contains(search))
                    return true;
            }
        } catch (SQLException e ) {
            System.err.println("compare exception!");
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("compare finally exception!");
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
