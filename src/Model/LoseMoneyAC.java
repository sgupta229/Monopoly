package Model;

import Controller.AbstractGame;

import java.util.List;

public class LoseMoneyAC extends AbstractActionCard {
    //List<Transfer> loseMoneyTo;
    String loseMoneyTo;
    double amountLose;

    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String loseTo, double amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo;
        amountLose = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {

    }
}
