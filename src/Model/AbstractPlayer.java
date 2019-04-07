package Model;

import java.util.ArrayList;

public class AbstractPlayer implements Transfer {
    private double funds;
    private ArrayList<Property> properties;
    private ArrayList<ActionCard> actionCards;
    private Token token;

    public AbstractPlayer(double funds, Token token) {
        this.funds = funds;
        this.token = token;
    }


    @Override
    public void makePayment(float amount, Transfer receiver) {
        this.funds = this.funds - amount;
        receiver.receivePayment(amount);
    }

    @Override
    public void receivePayment(float amount) {
        this.funds = this.funds + amount;
    }

    public boolean checkMonopoly() {
        return false;
    }

    public void move(int moveSpaces) {
        token.move(moveSpaces);
    }

    public void proposeTrade(Player other) {

    }

}
