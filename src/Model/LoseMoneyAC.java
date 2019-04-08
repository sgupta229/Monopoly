package Model;

import Controller.AbstractGame;

import java.util.List;

public class LoseMoneyAC extends AbstractActionCard {
    List<Transfer> loseMoneyTo;
    double amountLose;

    public LoseMoneyAC(String deckType, String message, Boolean holdable, List<Transfer> loseTo, double amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo;
        amountLose = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {

    }
}
