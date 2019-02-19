package com.unipu.coinbet.sql;

import com.unipu.coinbet.data.Cart;
import com.unipu.coinbet.data.Games;
import com.unipu.coinbet.data.Transactions;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * Klasa za čitanje iz baze.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Read {

    /**
     * Metoda za čitanje tablice games.
     */
    public static List<Games> readGames()  {

        SimpleDriverDataSource ds = ConnectToDatabase.getSimpleDriverDataSource();

        String sql = "SELECT * FROM games ORDER BY Id DESC";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Games> dataGames = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Games.class));

        return dataGames;
    }

    /**
     * Metoda za čitanje tablice cart.
     *
     * @param username atribut za korisničko ime
     */
    public static List<Cart> readCart(String username)  {

        SimpleDriverDataSource ds = ConnectToDatabase.getSimpleDriverDataSource();

        String sql = "SELECT * FROM cart WHERE  User = '" + username + "' ORDER BY Id";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Cart> dataCart = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Cart.class));

        return dataCart;
    }

    /**
     * Metoda za čitanje tablice transactions.
     */
    public static List<Transactions> readTransactions()  {

        SimpleDriverDataSource ds = ConnectToDatabase.getSimpleDriverDataSource();

        String sql = "SELECT * FROM transactions ORDER BY Id DESC";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Transactions> dataTransactions = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Transactions.class));

        return dataTransactions;
    }

    /**
     * Metoda za čitanje tablice transactions za određenog korisnika.
     *
     * @param username atribut za korisničko ime
     */
    public static List<Transactions> readTransactionsUser(String username)  {

        SimpleDriverDataSource ds = ConnectToDatabase.getSimpleDriverDataSource();

        String sql = "SELECT * FROM transactions WHERE  User = '" + username + "' ORDER BY Id DESC";

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        List<Transactions> dataTransactionsUser = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Transactions.class));

        return dataTransactionsUser;
    }
}