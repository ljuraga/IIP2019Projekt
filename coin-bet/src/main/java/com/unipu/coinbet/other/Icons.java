package com.unipu.coinbet.other;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Klasa za odabir ikone.
 *
 * @author CoinBet tim
 * @version 1.0
 */
public class Icons {

    static Icon icon;

    public static Icon getIcon(String iconName) {
        if (iconName.equals("CART_O")){
            //Ikona za praznu košaricu
            icon = new Icon(VaadinIcon.CART_O);
        }else if (iconName.equals("CART")){
            //Ikona za punu košaricu
            icon = new Icon(VaadinIcon.CART);
        }else if (iconName.equals("CASH")){
            //Ikona za gotovinu
            icon = new Icon(VaadinIcon.CASH);
        }else if (iconName.equals("ARCHIVE")){
            //Ikona za arhivu
            icon = new Icon(VaadinIcon.ARCHIVE);
        }else if (iconName.equals("USER")){
            //Ikona za korisnika
            icon = new Icon(VaadinIcon.USER);
        }else if (iconName.equals("MONEY")){
            //Ikona za novac
            icon = new Icon(VaadinIcon.MONEY);
        }else if (iconName.equals("REFRESH")){
            //Ikona za osvježi
            icon = new Icon(VaadinIcon.REFRESH);
        }else if (iconName.equals("SIGN_IN")){
            //Ikona za ulogiranje
            icon = new Icon(VaadinIcon.SIGN_IN);
        }else if (iconName.equals("SIGN_OUT")){
            //Ikona za izlogiranje
            icon = new Icon(VaadinIcon.SIGN_OUT);
        }else if (iconName.equals("CLOSE")){
            //Ikona za zatvaranje
            icon = new Icon(VaadinIcon.CLOSE);
        }else if (iconName.equals("TRASH")){
            //Ikona za smeče
            icon = new Icon(VaadinIcon.TRASH);
        }else if (iconName.equals("ENTER_ARROW")){
            //Ikona za enter
            icon = new Icon(VaadinIcon.ENTER_ARROW);
        }
        return icon;
    }
}
