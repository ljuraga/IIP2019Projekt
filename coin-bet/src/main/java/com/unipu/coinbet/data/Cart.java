package com.unipu.coinbet.data;

/**
 * Klasa za podatke tablice cart.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Cart {
    private String gameDate;
    private String gameTime;
    private String game;
    private String league;
    private String oddCategory;
    private float oddValue;
    private int oddMultiplayer;

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getGameTime() {
        return gameTime;
    }

    public void setGameTime(String gameTime) {
        this.gameTime = gameTime;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getOddCategory() {
        return oddCategory;
    }

    public void setOddCategory(String oddCategory) {
        this.oddCategory = oddCategory;
    }

    public float getOddValue() {
        return oddValue;
    }

    public void setOddValue(float oddValue) {
        this.oddValue = oddValue;
    }

    public int getOddMultiplayer() {
        return oddMultiplayer;
    }

    public void setOddMultiplayer(int oddMultiplayer) {
        this.oddMultiplayer = oddMultiplayer;
    }
}
