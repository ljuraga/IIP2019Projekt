package com.unipu.coinbet.sql.other;

import com.unipu.coinbet.sql.ConnectToDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa za lige.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Leagues {

    private static List leagues;

    /**
     * Metoda za dohvačanje naziva svih liga.
     */
    public static List getLeagues(){
        Connection conn = ConnectToDatabase.getConnection();
        Statement stmt = null;
        String query = "SELECT DISTINCT League FROM games";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            String league;

            leagues = new ArrayList();

            while(rs.next()){
                league = rs.getString("League");
                leagues.add(league);
            }
        } catch (SQLException e ) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return leagues;
    }
}
