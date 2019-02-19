package com.unipu.coinbet.sql.other;

import com.unipu.coinbet.sql.ConnectToDatabase;

import java.sql.*;

/**
 * Klasa za postoječeg korisnika.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class ExistingUser {

    private static Connection conn = null;

    /**
     * Metoda za provjeru dali korisničko ime i lozinka postoje.
     *
     * @param username atribut za korisničko ime
     * @param password atribut za lozinku
     */
    public static boolean checkIfUsernameAndPasswordExists(String username, String password){
        conn = ConnectToDatabase.getConnection();
        Statement stmt = null;
        String query = "SELECT User, Password FROM users";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                String tableUsername = rs.getString("User");
                String tablePassword = rs.getString("Password");
                if (tableUsername.equals(username) && tablePassword.equals(password)){
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
}
