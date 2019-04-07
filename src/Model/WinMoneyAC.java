package Model;

import Controller.AbstractGame;

import java.util.List;

public class WinMoneyAC extends AbstractActionCard {
    private List<Transfer> winMoneyFrom;
    private double amountWin;

    public WinMoneyAC(String deckType, String message, Boolean holdable, List<Transfer> winFrom, double amount) {
        super(deckType, message, holdable);
        winMoneyFrom = winFrom;
        amountWin = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {

    }
}
