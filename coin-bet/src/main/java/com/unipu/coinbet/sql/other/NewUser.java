package com.unipu.coinbet.sql.other;

import com.unipu.coinbet.sql.ConnectToDatabase;

import java.sql.*;

/**
 * Klasa za novog korisnika.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class NewUser {

    private static Connection conn = null;

    /**
     * Metoda za provijeru dali korisničko ime već postoji.
     *
     * @param username atribut za korisničko ime
     */
    public static boolean checkIfUsernameExists(String username){
        Connection conn = ConnectToDatabase.getConnection();
        Statement stmt = null;
        String query = "SELECT User FROM users";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                String tableUsername = rs.getString("User");
                if (tableUsername.equals(username)){
                    return true;
                }
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

        return false;
    }

    /**
     * Klasa sa metodom za registraciju novog korisnika.
     */
    public static class registerNewUser {
        /**
         * Metoda za za registraciju novog korisnika.
         *
         * @param username atribut za korisničko ime
         * @param password atribut za lozinku
         */
        public registerNewUser(String username, String password) {
            try {
                conn = ConnectToDatabase.getConnection();

                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO users ( User, Password, Balance ) VALUES ('" + username + "', '" + password + "', 0)";
                stmt.executeUpdate(sql);

                conn.close();
            }catch (Exception e) {
                System.err.println("Got an exception!");
                System.err.println(e.getMessage());
            }
        }
    }
}
