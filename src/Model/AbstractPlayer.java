package Model;

import Controller.Token;

import java.util.ArrayList;

public abstract class AbstractPlayer implements Transfer {
    private double funds;
    private ArrayList<Property> properties;
    private ArrayList<ActionCard> actionCards;
    private Token token;

    public AbstractPlayer(double funds, Token token) {
        this.funds = funds;
        this.token = token;
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

    public void move(int moveSpaces) {
        token.move(moveSpaces);
    }

    public void proposeTrade(AbstractPlayer other) {

    }

    public void setJail(boolean set) {

    }

}
