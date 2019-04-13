package Model.actioncards;

import Controller.AbstractGame;
import Model.DeckType;

public class WinMoneyAC extends AbstractActionCard {
    //private List<Transfer> winMoneyFrom;
    private String winMoneyFrom;
    private double amountWin;

    public WinMoneyAC(DeckType deckType, String message, Boolean holdable, String winFrom, double amount) {
        super(deckType, message, holdable);
        winMoneyFrom = winFrom;
        amountWin = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
