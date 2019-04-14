package Model;

import Controller.Token;

import Model.properties.Property;

import Model.actioncards.AbstractActionCard;


import java.util.ArrayList;

public abstract class AbstractPlayer implements Transfer {
    private String name;

    private double funds;
    private ArrayList<Property> properties;
    private ArrayList<AbstractActionCard> actionCards;


    private Token token;
    private boolean inJail;

    public AbstractPlayer() {
        this.inJail = false;
        properties = new ArrayList<>();
        actionCards = new ArrayList<>();
    }

    public AbstractPlayer(String name) {
        this.name = name;
//        this.tokenImage = tokenImage;
        this.inJail = false;
        properties = new ArrayList<>();
        actionCards = new ArrayList<>();
    }

    @Override
    public void makePayment(double amount, Transfer receiver) {
        if(this.funds < amount) {
            throw new IllegalArgumentException("Not enough money to pay");
        }
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
        if(token.getCurrentLocation() > boardSize - 1) {
            token.setLocation(token.getCurrentLocation() - boardSize);
        }
        return token.getCurrentLocation();
    }

    public int moveTo(int newLocation, int boardSize) {
        if(newLocation > boardSize - 1) {
            throw new IllegalArgumentException("This is an invalid location");
        }
        return token.moveTo(newLocation);
    }

    public void proposeTrade(AbstractPlayer other) {

    }

    public void build() {

    }

    public abstract void doSpecialMove();

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

    public void addActionCard(AbstractActionCard c) {
        actionCards.add(c);
    }

}
