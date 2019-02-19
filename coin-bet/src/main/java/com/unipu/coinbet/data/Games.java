package com.unipu.coinbet.data;

/**
 * Klasa za podatke tablice games.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Games {

    private String date;
    private String time;
    private String game;
    private String league;
    private String oddHome;
    private String oddDraw;
    private String oddAway;
    private String resultHome;
    private String resultDraw;
    private String resultAway;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getOddHome() {
        return oddHome;
    }

    public void setOddHome(String oddHome) {
        this.oddHome = oddHome;
    }

    public String getOddDraw() {
        return oddDraw;
    }

    public void setOddDraw(String oddDraw) {
        this.oddDraw = oddDraw;
    }

    public String getOddAway() {
        return oddAway;
    }

    public void setOddAway(String oddAway) {
        this.oddAway = oddAway;
    }

    public String getResultHome() {
        return resultHome;
    }

    public void setResultHome(String resultHome) {
        this.resultHome = resultHome;
    }

    public String getResultDraw() {
        return resultDraw;
    }

    public void setResultDraw(String resultDraw) {
        this.resultDraw = resultDraw;
    }

    public String getResultAway() {
        return resultAway;
    }

    public void setResultAway(String resultAway) {
        this.resultAway = resultAway;
    }
}