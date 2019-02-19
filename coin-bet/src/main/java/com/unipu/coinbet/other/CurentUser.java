package com.unipu.coinbet.other;

/**
 * Klasa za pohranu trenutnog korisnika.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class CurentUser {

    private static String user;

    public CurentUser(String user) {
        this.user = user;
    }

    public static String getUser() {
        return user;
    }
}
