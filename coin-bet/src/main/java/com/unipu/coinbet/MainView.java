package com.unipu.coinbet;

import com.unipu.coinbet.data.Cart;
import com.unipu.coinbet.data.Games;
import com.unipu.coinbet.data.Transactions;
import com.unipu.coinbet.other.*;
import com.unipu.coinbet.sql.Add;
import com.unipu.coinbet.sql.Delete;
import com.unipu.coinbet.sql.Read;
import com.unipu.coinbet.sql.other.*;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.time.LocalDate;
import java.util.*;

/**
 * Ova je glavna klasa aplikacije koja služi za definiranje sučelja.
 *
 * @author CoinBet tim
 * @version 1.0
 */
@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Viewport("width=device-width, height=device-height, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainView extends Div {
    private String adminUser = "admin";
    private String guestUser = "Guest";
    private Label labelBalance;

    private List<Games> gamesData;
    private Grid<Games> gamesGrid;

    private List<Cart> shopingCartData;
    private Grid<Cart> shopingCartGrid;

    private List<Transactions> transactionsData;
    private Grid<Transactions> transactionsGrid;
    private Grid.Column<Transactions> userColumnTransactions;

    private float cost = 0;
    private Label labelShopingCartTotal;
    private String balance;
    private float newBalance;
    private int cartItems;
    private Button shopingCartButton;
    private Button userControlPannelButton;
    private Div transactionsDiv;
    private int betCost = 10;

    private Dialog userControlPannelDialog;

    private Dialog loginDialog;
    private Button loginDialogButton;
    private TextField usernameTextField;
    private PasswordField passwordTextField;

    private Dialog registerDialog;
    private Button registerDialogButton;
    private TextField newUsernameTextField;
    private PasswordField newPasswordTextField;
    private PasswordField confirmNewPasswordTextField;

    private Button logoffButton;
    private Button transactionsButton;

    private Button buyCoinsButton;
    private Button cancelBuyCoinsButton;
    private TextField buyAmount;

    public MainView() {
        //Podešavanje trenutnog korisnika kao gosta
        new CurentUser(guestUser);



        //Provjera dali su rezultati ažurirani
        Timer checkResultTimer = new Timer();
        checkResultTimer.schedule(new checkResult(), 0, 5000); //Svakih 5 sekundi
//        checkResultTimer.schedule(new checkResult(), 0, 3600000); //Svakih sat vremena

        //Brisanje košarice nakon 5 minuta
        Timer clearCartTimer = new Timer();
        clearCartTimer.schedule(new clearCart(), 0, 300000); //Svakih 5 minuta



        //Label za logo
        Label labelLogo = new Label();
        labelLogo.setText("CoinBet");



        //Label za naslov grida
        Label labelGameList = new Label();
        labelGameList.setText("Games List:");



        //Grid sa utakmicama
        shopingCartButton = new Button(String.valueOf(cartItems), Icons.getIcon("CART_O"));
        gamesGrid = new Grid<>();

        refreshGameList();

        Grid.Column<Games> dateColumn = gamesGrid.addColumn(Games::getDate, "date").setHeader("Date");
        Grid.Column<Games> timeColumn = gamesGrid.addColumn(Games::getTime, "time").setHeader("Time");
        Grid.Column<Games> gameColumn = gamesGrid.addColumn(Games::getGame, "game").setHeader("Game");
        Grid.Column<Games> leagueColumn = gamesGrid.addColumn(Games::getLeague, "league").setHeader("League");

        Grid.Column<Games> oddHomeColumn = gamesGrid.addColumn(new NativeButtonRenderer<>(Games::getOddHome, item -> {
            oddMultiplayerDialog(item.getDate(), item.getTime(), item.getGame(), item.getLeague(), "Home", item.getOddHome());
        })).setWidth("100px").setFlexGrow(0).setHeader("Home Odd");

        Grid.Column<Games> oddDrawColumn = gamesGrid.addColumn(new NativeButtonRenderer<>(Games::getOddDraw, item -> {
            oddMultiplayerDialog(item.getDate(), item.getTime(), item.getGame(), item.getLeague(), "Draw", item.getOddDraw());
        })).setWidth("100px").setFlexGrow(0).setHeader("Draw Odd");

        Grid.Column<Games> oddAwayColumn = gamesGrid.addColumn(new NativeButtonRenderer<>(Games::getOddAway, item -> {
            oddMultiplayerDialog(item.getDate(), item.getTime(), item.getGame(), item.getLeague(), "Away", item.getOddAway());
        })).setWidth("100px").setFlexGrow(0).setHeader("Away Odd");

        Grid.Column<Games> resultHomeColumn = gamesGrid.addColumn(Games::getResultHome).setWidth("100px").setFlexGrow(0).setHeader("Home");
        Grid.Column<Games> resultDrawColumn = gamesGrid.addColumn(Games::getResultDraw).setWidth("100px").setFlexGrow(0).setHeader("Draw");
        Grid.Column<Games> resultAwayColumn = gamesGrid.addColumn(Games::getResultAway).setWidth("100px").setFlexGrow(0).setHeader("Away");

        //Definiranje veličine grida sa utakmicama
        gamesGrid.setWidth("100%");
        gamesGrid.setHeightByRows(true);



        //Header za filtere grid sa utakmicama
        HeaderRow filteringHeader = gamesGrid.appendHeaderRow();

        //Filter za datum grida sa utakmicama
        DatePicker dateFilter = new DatePicker();
        dateFilter.addValueChangeListener(event -> {
            ListDataProvider<Games> dataProvider = (ListDataProvider<Games>) gamesGrid.getDataProvider();
            dataProvider.setFilter(Games::getDate, dateFilterEvent -> {
                try{
                    String dateLower = dateFilterEvent.toLowerCase(Locale.ENGLISH);
                    String filterLower = "";
                    if (dateFilterEvent == null) {
                            return false;
                    }
                    LocalDate selectedDate = dateFilter.getValue();
                    filterLower = selectedDate.getYear() + "/" + selectedDate.getMonthValue() + "/" + selectedDate.getDayOfMonth();
                    return dateLower.contains(filterLower);
                }catch (NullPointerException e) {

                }
                return true;
            });
        });
        dateFilter.setPlaceholder("Select date");
        dateFilter.setWidth("100%");

        //Filter za vrijeme grida sa utakmicama
        TextField timeFilter = new TextField();
        timeFilter.addValueChangeListener(event -> {
            ListDataProvider<Games> dataProvider = (ListDataProvider<Games>) gamesGrid.getDataProvider();
            dataProvider.setFilter(Games::getTime, timeFilterEvent -> {
                if (timeFilterEvent == null) {
                    return false;
                }
                String timeLower = timeFilterEvent.toLowerCase(Locale.ENGLISH);
                String filterLower = event.getValue().toLowerCase(Locale.ENGLISH);
                return timeLower.contains(filterLower);
            });
        });
        timeFilter.setPlaceholder("Enter time");
        timeFilter.setWidth("100%");

        //Filter za utakmicu grida sa utakmicama
        TextField gameFilter = new TextField();
        gameFilter.addValueChangeListener(event -> {
            ListDataProvider<Games> dataProvider = (ListDataProvider<Games>) gamesGrid.getDataProvider();
            dataProvider.setFilter(Games::getGame, gameFilterEvent -> {
                if (gameFilterEvent == null) {
                    return false;
                }
                String gameLower = gameFilterEvent.toLowerCase(Locale.ENGLISH);
                String filterLower = event.getValue().toLowerCase(Locale.ENGLISH);
                return gameLower.contains(filterLower);
            });
        });
        gameFilter.setPlaceholder("Enter game");
        gameFilter.setWidth("100%");

        //Filter za ligu grida sa utakmicama
        ComboBox<String> leagueFilter = new ComboBox<>();
        leagueFilter.setItems(Leagues.getLeagues());
        leagueFilter.addValueChangeListener(event -> {
            ListDataProvider<Games> dataProvider = (ListDataProvider<Games>) gamesGrid.getDataProvider();
            dataProvider.setFilter(Games::getLeague, leagueFilterEvent -> {
                try{
                    if (leagueFilterEvent == null) {
                        return false;
                    }
                    String leagueLower = leagueFilterEvent.toLowerCase(Locale.ENGLISH);
                    String filterLower = event.getValue().toLowerCase(Locale.ENGLISH);

                    return leagueLower.contains(filterLower);
                }catch (NullPointerException e) {

                }
                return true;
            });
        });
        leagueFilter.setPlaceholder("Select league");
        leagueFilter.setWidth("100%");

        filteringHeader.getCell(dateColumn).setComponent(dateFilter);
        filteringHeader.getCell(timeColumn).setComponent(timeFilter);
        filteringHeader.getCell(gameColumn).setComponent(gameFilter);
        filteringHeader.getCell(leagueColumn).setComponent(leagueFilter);
        timeFilter.setValueChangeMode(ValueChangeMode.EAGER);
        gameFilter.setValueChangeMode(ValueChangeMode.EAGER);



        //Header grida sa utakmicama
        HeaderRow labelRow = gamesGrid.prependHeaderRow();

        //Label osnovnih informacija grida sa utakmicama
        HeaderRow.HeaderCell infoCell = labelRow.join(dateColumn, timeColumn, gameColumn, leagueColumn);
        infoCell.setText("Basic Information:");

        //Label koeficjenti grida sa utakmicama
        HeaderRow.HeaderCell oddsCell = labelRow.join(oddHomeColumn, oddDrawColumn, oddAwayColumn);
        oddsCell.setText("Ods:");

        //Label rezultati grida sa utakmicama
        HeaderRow.HeaderCell resultCell = labelRow.join(resultHomeColumn, resultDrawColumn, resultAwayColumn);
        resultCell.setText("Results:");



        //Dialog košarice
        Dialog shopingCartDialog = new Dialog();

        //Div košarice
        Div shopingCartDiv = new Div();
        shopingCartDiv.addClassName("cart-style");
        shopingCartDiv.setText("This items are in the Shoping Cart:");

        //Gumb za otvaranje dialoga košarice
        shopingCartButton.addClickListener(event -> shopingCartDialog.open());

        //Grid košarice
        shopingCartGrid = new Grid<>();
        refreshShopingCart();
        setCartIconAndText();

        Grid.Column<Cart> dateColumnShopingCart = shopingCartGrid.addColumn(Cart::getGameDate).setHeader("Date");
        Grid.Column<Cart> timeColumnShopingCart = shopingCartGrid.addColumn(Cart::getGameTime).setHeader("Time");
        Grid.Column<Cart> gameColumnShopingCart = shopingCartGrid.addColumn(Cart::getGame).setHeader("Game");
        Grid.Column<Cart> leagueColumnShopingCart = shopingCartGrid.addColumn(Cart::getLeague).setHeader("League");
        Grid.Column<Cart> oddCategoryColumnShopingCart = shopingCartGrid.addColumn(Cart::getOddCategory).setHeader("Odd Category");
        Grid.Column<Cart> oddValueColumnShopingCart = shopingCartGrid.addColumn(Cart::getOddValue).setHeader("Odd Value");
        Grid.Column<Cart> oddMultiplayerColumnShopingCart = shopingCartGrid.addColumn(Cart::getOddMultiplayer).setHeader("Odd Multiplayer");
        Grid.Column<Cart> removeColumnShopingCart = shopingCartGrid.addColumn(new NativeButtonRenderer<>("Remove", item -> {
            System.err.println("to delete: " + CurentUser.getUser() + item.getGameDate() + item.getGameTime() + item.getGame() + item.getLeague() + item.getOddCategory() + item.getOddValue());
            Delete.cartItem(CurentUser.getUser(), item.getGameDate(), item.getGameTime(), item.getGame(), item.getLeague(), item.getOddCategory(), item.getOddValue());
            setCartIconAndText();
            refreshShopingCart();
            System.err.println("delated: " + CurentUser.getUser() + item.getGameDate() + item.getGameTime() + item.getGame() + item.getLeague() + item.getOddCategory() + item.getOddValue());
        })).setWidth("200px").setFlexGrow(0).setHeader("");

        //Definiranje veličine grida košarice
        shopingCartGrid.setWidth("100%");
        shopingCartGrid.setHeight("700px");

        //Label za ukupni trosak košarice
        labelShopingCartTotal = new Label();
        labelShopingCartTotal.setText("Cost: " + cost);

        //Gumb za kupnju stavki u košarici
        Button buyShopingCartButton = new Button("Buy", Icons.getIcon("CASH"));
        buyShopingCartButton.addClickListener(event -> {
            buy();
        });

        //Gumb za brisanje svih stavki u košarici
        Button clearShopingCart = new Button("Clear", Icons.getIcon("TRASH"), event -> {
            Delete.cart(CurentUser.getUser());
            refreshShopingCart();
            cost = 0;
            labelShopingCartTotal.setText("Cost: " + cost);

            setCartIconAndText();
        });

        //Gumb za osvježavanje stavki u gridu košarice
        Button refreshShopingCart = new Button("Refresh Cart", Icons.getIcon("REFRESH"), event -> {
            refreshShopingCart();
        });

        //Gumb za zatvaranje dialoga košarice
        Button closeShopingCartButton = new Button(Icons.getIcon("CLOSE"));
        closeShopingCartButton.addClickListener(event -> shopingCartDialog.close());

        //Dodavanje svih elemenata u dialog košarice
        shopingCartDialog.add(shopingCartDiv, shopingCartGrid, labelShopingCartTotal, buyShopingCartButton, clearShopingCart, refreshShopingCart, closeShopingCartButton);

        //Definiranje veličine dialoga košarice
        shopingCartDialog.setWidth("1024px");
        shopingCartDialog.setHeight("768px");



        //Dialog transakcija
        Dialog transactionsDialog = new Dialog();

        //Dialog korisničkog i administrativnog sučelja
        userControlPannelDialog = new Dialog();

        //Div transakcija
        transactionsDiv = new Div();
        transactionsDiv.addClassName("trans-style");

        //Gumb za otvaranje dialoga transakcija
        transactionsButton = new Button("Transactions", Icons.getIcon("ARCHIVE"));
        transactionsButton.addClickListener(event -> {
            refreshTransactions();
            try {
                transactionsGrid.removeColumn(userColumnTransactions);
            }catch (NullPointerException e){

            }catch (IllegalArgumentException e){

            }

            if (CurentUser.getUser().equals(adminUser)){
                userColumnTransactions = transactionsGrid.addColumn(Transactions::getUser).setHeader("User");
            }

            transactionsDialog.open();
            userControlPannelDialog.close();
        });

        //Grid transakcija
        transactionsGrid = new Grid<>();

        refreshTransactions();

        Grid.Column<Transactions> transactionDateColumnTransactions = transactionsGrid.addColumn(Transactions::getTransactionDate).setHeader("Transaction Date");
        Grid.Column<Transactions> transactionTimeColumnTransactions = transactionsGrid.addColumn(Transactions::getTransactionTime).setHeader("Transaction Time");
        Grid.Column<Transactions> gameDateColumnTransactions = transactionsGrid.addColumn(Transactions::getGameDate).setHeader("Game Date");
        Grid.Column<Transactions> gameTimeColumnTransactions = transactionsGrid.addColumn(Transactions::getGameTime).setHeader("Game Time");
        Grid.Column<Transactions> gameColumnTransactions = transactionsGrid.addColumn(Transactions::getGame).setHeader("Game");
        Grid.Column<Transactions> leagueColumnTransactions = transactionsGrid.addColumn(Transactions::getLeague).setHeader("League");
        Grid.Column<Transactions> oddCategoryColumnTransactions = transactionsGrid.addColumn(Transactions::getOddCategory).setHeader("Odd Category");
        Grid.Column<Transactions> oddValueColumnTransactions = transactionsGrid.addColumn(Transactions::getOddValue).setHeader("Odd Value");
        Grid.Column<Transactions> oddMultiplayerColumnTransactions = transactionsGrid.addColumn(Transactions::getOddMultiplayer).setHeader("Odd Multiplayer");
        Grid.Column<Transactions> resultColumnTransactions = transactionsGrid.addColumn(Transactions::getResult).setHeader("Winner");
        Grid.Column<Transactions> paidColumnTransactions = transactionsGrid.addColumn(Transactions::getPaid).setHeader("Paid to User");

        //Definiranje veličine grida transakcija
        transactionsGrid.setWidth("100%");
        transactionsGrid.setHeight("700px");

        //Gumb za osvježavanje grida transakcija
        Button refreshTransactions = new Button("Refresh Transactions List", Icons.getIcon("REFRESH"), event -> {
            refreshTransactions();
        });

        //Gumb za zatvaranje dialoga transakcija
        Button closeTransactionsButton = new Button(Icons.getIcon("CLOSE"));
        closeTransactionsButton.addClickListener(event -> {
            transactionsDialog.close();
            userControlPannelDialog.open();
        });

        //Dodavanje svih elemenata u dialog transakcija
        transactionsDialog.add(transactionsDiv, transactionsGrid, refreshTransactions, closeTransactionsButton);

        //Definiranje veličine dialoga transakcija
        transactionsDialog.setWidth("1200px");
        transactionsDialog.setHeight("768px");



        //Dialog prijave
        loginDialog = new Dialog();

        //Dialog registracije
        registerDialog = new Dialog();

        //Div korisničkog i administrativnog sučelja
        Div userControlPannelDiv = new Div();
        userControlPannelDiv.addClassName("ucp-style");
        userControlPannelDiv.setText("Select user:");

        //Gumb za otvaranje dialoga prijave
        loginDialogButton = new Button("Log in", Icons.getIcon("SIGN_IN"));
        loginDialogButton.addClickListener(event -> {
            loginDialog.open();
            userControlPannelDialog.close();
        });

        //Gumb za otvaranje dialoga registracije
        registerDialogButton = new Button("Register", Icons.getIcon("USER"));
        registerDialogButton.addClickListener(event -> {
            registerDialog.open();
            userControlPannelDialog.close();
        });

        //Gumb za odjavu
        logoffButton = new Button("Log off", Icons.getIcon("SIGN_OUT"));
        logoffButton.addClickListener(event -> {
            System.err.println("User " + CurentUser.getUser() + " loged off");

            setUsername(guestUser);
            setLoginButtonsToLogin();
        });

        //Gumb za otvaranje dialoga korisničkog i administrativnog sučelja
        userControlPannelButton = new Button(CurentUser.getUser(), Icons.getIcon("USER"));
        userControlPannelButton.addClickListener(event -> {
            if (CurentUser.getUser().equals(guestUser)){
                setLoginButtonsToLogin();
            }else if (!CurentUser.getUser().equals(guestUser)){
                setLoginButtonsToLogout();
            }
            userControlPannelDialog.open();
        });

        //Label za stanje računa korisnika
        labelBalance = new Label();
        labelBalance.setText(getBalanceString());
        labelBalance.setVisible(false);

        //TextField za upis količine novčiča za kupnju
        buyAmount = new TextField();
        buyAmount.setVisible(false);
        buyAmount.setPlaceholder("Enter amount");
        buyAmount.setPattern("[0-9]*");
        buyAmount.setPreventInvalidInput(true);
        buyAmount.setSuffixComponent(new Span("BETCOIN"));

        //Gumb za kupnju novčiča
        buyCoinsButton = new Button("Buy coins", Icons.getIcon("MONEY"));

        //Gumb za prekid kupovine novčiča
        cancelBuyCoinsButton = new Button("Cancel buy", Icons.getIcon("CLOSE"));
        cancelBuyCoinsButton.setVisible(false);

        //Listener za gumb kupnje novčiča
        buyCoinsButton.addClickListener(event -> {
            if (!CurentUser.getUser().equals(adminUser)){
                buyAmount.setVisible(true);
                cancelBuyCoinsButton.setVisible(true);
                buyCoinsButton.setVisible(false);

                userControlPannelDialog.setWidth("620px");
                userControlPannelDialog.setHeight("70px");
            }else if (CurentUser.getUser().equals(adminUser)){
                Notification notification = new Notification(
                        "Admin can't buy coins!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }
        });

        //Listener za gumb prekida kupnje novčiča
        cancelBuyCoinsButton.addClickListener(event -> {
            buyAmount.setVisible(false);
            cancelBuyCoinsButton.setVisible(false);
            buyCoinsButton.setVisible(true);

            userControlPannelDialog.setWidth("410px");
            userControlPannelDialog.setHeight("70px");
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa količine novčiča za kupnju
        buyAmount.addKeyPressListener(Key.ENTER, event -> {
            try{
                newBalance = Balance.getBalance(CurentUser.getUser()) + Float.parseFloat(buyAmount.getValue());
                new Balance(newBalance, CurentUser.getUser());
                labelBalance.setText(getBalanceString());

                buyAmount.setValue("");

                String buyAmountValue = "Sucesfully bought " + buyAmount.getValue() + " BETCOIN coins!";

                Notification notification = new Notification(
                        buyAmountValue, 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();

                buyAmount.setVisible(false);
                cancelBuyCoinsButton.setVisible(false);
                buyCoinsButton.setVisible(true);
            }catch (NumberFormatException e){

            }
        });

        //Gumb za zatvaranje dialoga korisničkog i administrativnog sučelja
        Button closeUserControlPannelButton = new Button(Icons.getIcon("CLOSE"));
        closeUserControlPannelButton.addClickListener(event -> {
            userControlPannelDialog.close();
        });

        //Dodavanje svih elemenata u dialog korisničkog i administrativnog sučelja
        userControlPannelDialog.add(userControlPannelDiv, loginDialogButton, registerDialogButton, logoffButton, labelBalance, transactionsButton, buyCoinsButton, cancelBuyCoinsButton, buyAmount, closeUserControlPannelButton);

        //Definiranje veličine dialoga korisničkog i administrativnog sučelja
        userControlPannelDialog.setWidth("250px");
        userControlPannelDialog.setHeight("70px");



        //Div prijave
        Div loginDiv = new Div();
        loginDiv.setText("Login Dialog:");

        //TextField za upis korisničkog imena
        usernameTextField = new TextField();
        usernameTextField.setPlaceholder("Enter username");
        usernameTextField.addValueChangeListener(event -> event.getValue());

        //TextField za upis lozinke
        passwordTextField = new PasswordField();
        passwordTextField.setPlaceholder("Enter password");
        passwordTextField.addValueChangeListener(event -> event.getValue());

        //Gumb za prijavu
        Button loginButton = new Button("Log in", Icons.getIcon("SIGN_IN"));
        loginButton.addClickListener(event -> {
            login(usernameTextField.getValue(), passwordTextField.getValue());
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa korisničkog imena
        usernameTextField.addKeyPressListener(Key.ENTER, event -> {
            login(usernameTextField.getValue(), passwordTextField.getValue());
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa lozinke
        passwordTextField.addKeyPressListener(Key.ENTER, event -> {
            login(usernameTextField.getValue(), passwordTextField.getValue());
        });

        //Gumb za zatvaranje dialoga prijave
        Button closeLoginButton = new Button(Icons.getIcon("CLOSE"));
        closeLoginButton.addClickListener(event -> {
            loginDialog.close();
            userControlPannelDialog.open();
        });

        //Dodavanje svih elemenata u dialog prijave
        loginDialog.add(loginDiv, usernameTextField, passwordTextField, loginButton, closeLoginButton);

        //Definiranje veličine dialoga prijave
        loginDialog.setWidth("190px");
        loginDialog.setHeight("155px");



        //Div registracije
        Div registerDiv = new Div();
        registerDiv.addClassName("register-style");
        registerDiv.setText("Register Dialog:");

        //TextField za upis novog korisničkog imena
        newUsernameTextField = new TextField();
        newUsernameTextField.setPlaceholder("Enter username");
        newUsernameTextField.addValueChangeListener(event -> event.getValue());

        //TextField za upis nove lozinke
        newPasswordTextField = new PasswordField();
        newPasswordTextField.setPlaceholder("Enter password");
        newPasswordTextField.addValueChangeListener(event -> event.getValue());

        //TextField za potgvrdu nove lozinke
        confirmNewPasswordTextField = new PasswordField();
        confirmNewPasswordTextField.setPlaceholder("Enter password again");
        confirmNewPasswordTextField.addValueChangeListener(event -> event.getValue());

        //Gumb za registraciju
        Button registerButton = new Button("Register", Icons.getIcon("ENTER_ARROW"));
        registerButton.addClickListener(event -> {
            register(newUsernameTextField.getValue(), newPasswordTextField.getValue(), confirmNewPasswordTextField.getValue());
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa novog korisničkog imena
        newUsernameTextField.addKeyPressListener(Key.ENTER, event -> {
            register(newUsernameTextField.getValue(), newPasswordTextField.getValue(), confirmNewPasswordTextField.getValue());
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa nove lozinke
        newPasswordTextField.addKeyPressListener(Key.ENTER, event -> {
            register(newUsernameTextField.getValue(), newPasswordTextField.getValue(), confirmNewPasswordTextField.getValue());
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa nove lozinke
        confirmNewPasswordTextField.addKeyPressListener(Key.ENTER, event -> {
            register(newUsernameTextField.getValue(), newPasswordTextField.getValue(), confirmNewPasswordTextField.getValue());
        });

        //Gumb za zatvaranje dialoga registracije
        Button closeRegisterButton = new Button(Icons.getIcon("CLOSE"));
        closeRegisterButton.addClickListener(event -> {
            registerDialog.close();
            userControlPannelDialog.open();
        });

        //Dodavanje svih elemenata u dialog registracije
        registerDialog.add(registerDiv, newUsernameTextField, newPasswordTextField, confirmNewPasswordTextField, registerButton, closeRegisterButton);

        //Definiranje veličine dialoga registracije
        registerDialog.setWidth("190px");
        registerDialog.setHeight("200px");



        //Definiranje svih layouteva stranice
        VerticalLayout layout = new VerticalLayout();
        layout.addClassName("layout-style");


        HorizontalLayout layoutMenuFill = new HorizontalLayout();
        layoutMenuFill.addClassName("layoutMenu-style");

        layoutMenuFill.setWidth("100%");
        layoutMenuFill.setHeight("88px");


        HorizontalLayout layoutMenu = new HorizontalLayout();
        layoutMenu.addClassName("layoutMenu-style");

        layoutMenu.setAlignItems(FlexComponent.Alignment.CENTER);

        layoutMenu.setWidth("1000px");
        layoutMenu.setHeight("88px");


        HorizontalLayout layoutMenuLogo = new HorizontalLayout();
        layoutMenuLogo.addClassName("layoutMenuLogo-style");


        HorizontalLayout layoutMenuButtons = new HorizontalLayout();
        layoutMenuButtons.addClassName("layoutMenuButtons-style");


        HorizontalLayout layoutBanner = new HorizontalLayout();
        layoutBanner.addClassName("layoutBanner-style");


        layoutBanner.setWidth("100%");
        layoutBanner.setHeight("400px");


        VerticalLayout layoutGamesList = new VerticalLayout();
        layoutGamesList.addClassName("layoutGamesList-style");
        gamesGrid.addClassName("layoutGamesList-style");


        //Definiranje CSS koda stranice
        UI.getCurrent().getPage().addStyleSheet("/frontend/styles/default.css");


        //Dodavanje svih elemenata u layoute
        layoutGamesList.add(labelGameList, gamesGrid);
        layoutMenuLogo.add(labelLogo);
        layoutMenuButtons.add(labelBalance, shopingCartButton, userControlPannelButton);
        layoutMenuFill.add(layoutMenu);
        layoutMenu.add(layoutMenuLogo, layoutMenuButtons);
        layout.add(layoutMenuFill, layoutBanner, layoutGamesList);
        add(layout);



        //Podešavanje trenutnog korisnika kao debug usera
//        login("debugUser", "debugUser");
//        userControlPannelDialog.close();
    }

    /**
     * Metoda za kupnju stavki u košarici
     */
    public void buy(){
        if (CurentUser.getUser().equals(guestUser)){
            Notification notification = new Notification(
                    "Login or register to be able to buy!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();

            Delete.cart(CurentUser.getUser());
            refreshShopingCart();

            setCartIconAndText();
        }else if (CurentUser.getUser().equals(adminUser)){
            Notification notification = new Notification(
                    "Admin isn't able to buy!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();

            Delete.cart(CurentUser.getUser());
            refreshShopingCart();

            setCartIconAndText();
        }else if (!CurentUser.getUser().equals(guestUser) && !CurentUser.getUser().equals(adminUser)){
            if (Balance.getBalance(CurentUser.getUser()) >= cost){
                calculateNewBalance(betCost);
                new Balance(newBalance, CurentUser.getUser());

                new Add.Transaction(CurentUser.getUser());

                cartItems = 0;


                refreshShopingCart();

                labelBalance.setText(getBalanceString());

                labelShopingCartTotal.setText("Cost: " + 0);

                setCartIconAndText();

                betCost = 10;
                Notification notification = new Notification(
                        "Cart items sucesfuly bought!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }else if (Balance.getBalance(CurentUser.getUser()) < cost){
                Notification notification = new Notification(
                        "Not enough CoinBet coins to be able to buy!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }
        }
    }

    /**
     * Klasa sa metodom koja se izvršava kod provjere dali su rezultati ažurirani.
     */
    private class checkResult extends TimerTask {
        public void run() {
            Result.updateGames();
            Result.checkForResults();
            System.out.println("checkResult");
        }
    }

    /**
     * Klasa sa metodom koja se izvršava kod brisanja košarice nakon 5 minuta.
     */
    private class clearCart extends TimerTask {
        public void run() {
            Delete.cart(CurentUser.getUser());
            System.out.println("clearCart");
        }
    }

    /**
     * Metoda za izračun novog stanja korisnikovog računa s obzirom na dodane oklade
     *
     * @param betCost atribut za Multiplayer
     */
    private void calculateNewBalance(int betCost){
        ListDataProvider<Cart> s=(ListDataProvider<Cart>) shopingCartGrid.getDataProvider();
        ArrayList<Cart> s1 = (ArrayList<Cart>) s.getItems();

        cost = 0;

        float balance = Balance.getBalance(CurentUser.getUser());

        for(Cart cart : s1) {
            System.out.println(cart.getOddValue());
            cost = cost + (cart.getOddValue() * betCost);
        }

        newBalance = balance - cost;
    }

    /**
     * Metoda za dobivanje trenutnog stanja korisnikovog računa.
     */
    private String getBalanceString(){
        balance = "Balance: " + Balance.getBalance(CurentUser.getUser()) + " BETCOINS";
        return balance;
    }

    /**
     * Metoda za podešavanje ikone i teksta gumba za otvaranje košarice.
     */
    private void setCartIconAndText(){
        try{
            cartItems = shopingCartData.size();
            shopingCartButton.setText(String.valueOf(cartItems));
            if (cartItems == 0){
                shopingCartButton.setIcon(Icons.getIcon("CART_O"));
            }else if (cartItems != 0){
                shopingCartButton.setIcon(Icons.getIcon("CART"));
            }
            System.err.println("cartItems:" + cartItems);
        }catch (NullPointerException e) {

        }
    }

    /**
     * Metoda za dialog Multiplayer-a.
     *
     * @param gameDate atribut za datum utakmice
     * @param gameTime atribut za vrijeme utakmice
     * @param game atribut za ime utakmice
     * @param league atribut za ligu
     * @param oddCategory atribut za kategoriju oklade (home, draw, away)
     * @param oddValue atribut za vrijednost oklade
     */
    private void oddMultiplayerDialog(String gameDate, String gameTime, String game, String league, String oddCategory, String oddValue){
        //Dialog Multiplayer-a
        Dialog oddMultiplayerDialog = new Dialog();

        //Div Multiplayer-a
        Div oddMultiplayerDiv = new Div();
        oddMultiplayerDiv.addClassName("oddMultiplayer-style");
        oddMultiplayerDiv.setText("Odd Multiplayer Dialog:");

        //TextField za upis Multiplayer-a
        TextField oddMultiplayerTextField = new TextField();
        oddMultiplayerTextField.setPlaceholder("Enter odd multiplayer");
        oddMultiplayerTextField.addValueChangeListener(event -> event.getValue());

        //Gumb za potvrdu Multiplayer-a
        Button oddMultiplayerConfirmButton = new Button("Confirm", Icons.getIcon("ENTER_ARROW"));
        oddMultiplayerConfirmButton.addClickListener(event -> {
            betCost = Integer.parseInt(oddMultiplayerTextField.getValue());
            addBet(gameDate, gameTime, game, league, oddCategory, oddValue);
            oddMultiplayerDialog.close();
        });

        //Listener za pritisak tipke ENTER na tipkovnici za TextField upisa Multiplayer-a
        oddMultiplayerTextField.addKeyPressListener(Key.ENTER, event -> {
            betCost = Integer.parseInt(oddMultiplayerTextField.getValue());
            addBet(gameDate, gameTime, game, league, oddCategory, oddValue);
            oddMultiplayerDialog.close();
        });

        //Gumb za zatvaranje dialoga Multiplayer-a
        Button closeoddMultiplayerButton = new Button(Icons.getIcon("CLOSE"));
        closeoddMultiplayerButton.addClickListener(event -> {
            oddMultiplayerDialog.close();
        });

        //Dodavanje svih elemenata u dialog Multiplayer-a
        oddMultiplayerDialog.add(oddMultiplayerDiv, oddMultiplayerTextField, oddMultiplayerConfirmButton, closeoddMultiplayerButton);

        //Definiranje veličine dialoga Multiplayer-a
        oddMultiplayerDialog.setWidth("170px");
        oddMultiplayerDialog.setHeight("120px");

        oddMultiplayerDialog.open();
    }

    /**
     * Metoda za dodavanje oklade u košaricu.
     *
     * @param gameDate atribut za datum utakmice
     * @param gameTime atribut za vrijeme utakmice
     * @param game atribut za ime utakmice
     * @param league atribut za ligu
     * @param oddCategory atribut za kategoriju oklade (home, draw, away)
     * @param oddValue atribut za vrijednost oklade
     */
    private void addBet(String gameDate, String gameTime, String game, String league, String oddCategory, String oddValue){
        if (!Bet.transacrtionExist(CurentUser.getUser(), gameDate, gameTime, game, league, oddCategory, Float.parseFloat(oddValue))){
            if (!Bet.existsInCart(CurentUser.getUser(), gameDate, gameTime, game, league, oddCategory, Float.parseFloat(oddValue))){
                new Add.Cart(CurentUser.getUser(), gameDate, gameTime, game, league, oddCategory, oddValue, betCost);
                refreshShopingCart();
                calculateNewBalance(betCost);
                labelShopingCartTotal.setText("Cost: " + cost);

                setCartIconAndText();

                Notification notification = new Notification(
                        "Bet added!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }else if (Bet.existsInCart(CurentUser.getUser(), gameDate, gameTime, game, league, oddCategory, Float.parseFloat(oddValue))){
                Notification notification = new Notification(
                        "You already added this bet to cart!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }
        }else if (Bet.transacrtionExist(CurentUser.getUser(), gameDate, gameTime, game, league, oddCategory, Float.parseFloat(oddValue))){
            Notification notification = new Notification(
                    "You already made this bet!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }
    }

    /**
     * Metoda za podešavanje trenutnog korisnika.
     *
     * @param username atribut za korisničko ime
     */
    private void setUsername(String username) {
        new CurentUser(username);
        userControlPannelButton.setText(CurentUser.getUser());
        refreshTransactions();
        refreshShopingCart();
        labelBalance.setText(getBalanceString());

        cost = 0;
        labelShopingCartTotal.setText("Cost: " + cost);

        if (username.equals(adminUser)){
            transactionsDiv.setText("This is the history of all transaction:");
        }else if (!username.equals(adminUser)){
            transactionsDiv.setText("This is the history of your transaction:");
        }

        setCartIconAndText();
    }

    /**
     * Metoda za osvježavanje stavki u gridu transakcija.
     */
    private void refreshTransactions() {
        transactionsData = Read.readTransactionsUser(CurentUser.getUser());
        transactionsGrid.setItems(transactionsData);
        if (CurentUser.getUser().equals(adminUser)){
            transactionsData = Read.readTransactions();
            transactionsGrid.setItems(transactionsData);
        }
    }

    /**
     * Metoda za osvježavanje stavki u gridu košarice.
     */
    private void refreshShopingCart() {
        shopingCartData = Read.readCart(CurentUser.getUser());
        shopingCartGrid.setItems(shopingCartData);
    }

    /**
     * Metoda za osvježavanje stavki u gridu utakmica.
     */
    private void refreshGameList() {
        gamesData = Read.readGames();
        gamesGrid.setItems(gamesData);
    }

    /**
     * Metoda za prijavu korisnika.
     *
     * @param username atribut za korisničko ime
     * @param password atribut za lozinku
     */
    private void login(String username, String password) {
        if (username.toLowerCase().equals("guest")){
            Notification notification = new Notification(
                    "You can't login as a Guest!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (username.isEmpty()){
            Notification notification = new Notification(
                    "Username is empty!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (password.isEmpty()){
            Notification notification = new Notification(
                    "Password is empty!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (!username.isEmpty() && !password.isEmpty()){
            if (ExistingUser.checkIfUsernameAndPasswordExists(Hash.hash_to_SHA_512(username), Hash.hash_to_SHA_512(password))) {
                setUsername(username);

                setLoginButtonsToLogout();

                usernameTextField.setValue("");
                passwordTextField.setValue("");
                loginDialog.close();
                userControlPannelDialog.open();
                System.err.println("User " + username + " loged in");
            }else if (!ExistingUser.checkIfUsernameAndPasswordExists(Hash.hash_to_SHA_512(username), Hash.hash_to_SHA_512(password))) {
                Notification notification = new Notification(
                        "Username or password is worng or the user does not exist!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }
        }
    }

    /**
     * Metoda za podesavanje elementa u stanje za prijavu.
     */
    private void setLoginButtonsToLogin() {
        labelBalance.setVisible(false);
        loginDialogButton.setVisible(true);
        registerDialogButton.setVisible(true);
        logoffButton.setVisible(false);
        transactionsButton.setVisible(false);
        buyCoinsButton.setVisible(false);

        userControlPannelDialog.setWidth("250px");
        userControlPannelDialog.setHeight("70px");
    }

    /**
     * Metoda za podesavanje elementa u stanje za odjavu.
     */
    private void setLoginButtonsToLogout() {
        labelBalance.setVisible(true);
        loginDialogButton.setVisible(false);
        registerDialogButton.setVisible(false);
        logoffButton.setVisible(true);
        transactionsButton.setVisible(true);
        buyCoinsButton.setVisible(true);
        cancelBuyCoinsButton.setVisible(false);
        buyAmount.setVisible(false);

        userControlPannelDialog.setWidth("410px");
        userControlPannelDialog.setHeight("70px");
    }

    /**
     * Metoda za registraciju korisnika.
     *
     * @param username atribut za korisničko ime
     * @param password atribut za lozinku
     * @param passwordConfirmation atribut za potvrdu lozinke
     */
    private void register(String username, String password, String passwordConfirmation) {
        if (username.toLowerCase().equals("guest")){
            Notification notification = new Notification(
                    "You can't register as a Guest!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (username.toLowerCase().equals("admin")){
            Notification notification = new Notification(
                    "You can't register as a Admin!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (username.isEmpty()){
            Notification notification = new Notification(
                    "Username is empty!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (password.isEmpty()){
            Notification notification = new Notification(
                    "Password is empty!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (passwordConfirmation.isEmpty()){
            Notification notification = new Notification(
                    "Password confirmation is empty!", 3000,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        }else if (!username.isEmpty() && !password.isEmpty() && !passwordConfirmation.isEmpty()){
            if (password.equals(passwordConfirmation)){
                if (!NewUser.checkIfUsernameExists(username)) {
                    new NewUser.registerNewUser(Hash.hash_to_SHA_512(username), Hash.hash_to_SHA_512(password));
                    login(username, password);

                    newUsernameTextField.setValue("");
                    newPasswordTextField.setValue("");
                    registerDialog.close();
                    userControlPannelDialog.open();
                    System.err.println("User " + username + " registered");
                }else if (NewUser.checkIfUsernameExists(username)) {
                    Notification notification = new Notification(
                            "Username " + username + " is taken enter a new username!", 3000,
                            Notification.Position.BOTTOM_CENTER);
                    notification.open();
                }
            }else if (!password.equals(passwordConfirmation)){
                Notification notification = new Notification(
                        "The password is not the same!", 3000,
                        Notification.Position.BOTTOM_CENTER);
                notification.open();
            }
        }
    }
}