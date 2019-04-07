package Model;

import java.util.ArrayList;

public class AbstractPlayer implements Transfer {
    private double funds;
    private ArrayList<Property> properties;
    private ArrayList<ActionCard> actionCards;


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

}
