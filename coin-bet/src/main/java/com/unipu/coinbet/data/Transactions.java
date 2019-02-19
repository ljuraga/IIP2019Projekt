package com.unipu.coinbet.data;

/**
 * Klasa za podatke tablice transactions.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Transactions {

    private String user;
    private String transactionDate;
    private String transactionTime;
    private String gameDate;
    private String gameTime;
    private String game;
    private String league;
    private String oddCategory;
    private String oddValue;
    private int oddMultiplayer;
    private String result;
    private boolean paid;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

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

    public String getOddValue() {
        return oddValue;
    }

    public void setOddValue(String oddValue) {
        this.oddValue = oddValue;
    }

    public int getOddMultiplayer() {
        return oddMultiplayer;
    }

    public void setOddMultiplayer(int oddMultiplayer) {
        this.oddMultiplayer = oddMultiplayer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
