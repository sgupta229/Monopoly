package Model;

import Controller.Token;

import java.util.ArrayList;

public abstract class AbstractPlayer implements Transfer {
    private String name;
    private String tokenImage;

    private double funds;
    private ArrayList<Property> properties;
    private ArrayList<AbstractActionCard> actionCards;
    private Token token;
    private boolean inJail;

    public AbstractPlayer(double funds, Token token) {
        this.funds = funds;
        this.token = token;
    }

    public AbstractPlayer() {

    }

    public AbstractPlayer(String name, String tokenImage) {
        this.name = name;
        this.tokenImage = tokenImage;
    }

    @Override
    public void makePayment(double amount, Transfer receiver) {
        this.funds = this.funds - amount;
        receiver.receivePayment(amount);
    }

    @Override
    public void receivePayment(double amount) {
        this.funds = this.funds + amount;
    }

    public boolean checkMonopoly() {
        return false;
    }

    public double getFunds() {
        return funds;
    }

    public Token getToken() {
        return token;
    }

    //finish this method

    public int move(int moveSpaces, int boardSize) {
        token.move(moveSpaces);
        if(token.getCurrentLocation() > boardSize) {
            token.setLocation(token.getCurrentLocation() - boardSize - 1);
        }
        return token.getCurrentLocation();
    }

    public int moveTo(int newLocation) {
        return token.moveTo(newLocation);
    }

    public void proposeTrade(AbstractPlayer other) {

    }

    public void setJail(boolean set) {
        inJail = set;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public boolean isInJail() {
        return inJail;
    }

    public int getCurrentLocation() {
        return token.getCurrentLocation();
    }

    public String getName() {
        return this.name;
    }

    public String getTokenImage() {
        return this.tokenImage;
    }
}
