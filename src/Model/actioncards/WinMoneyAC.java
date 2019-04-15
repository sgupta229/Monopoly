package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.Transfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinMoneyAC extends AbstractActionCard {
    //private List<Transfer> winMoneyFrom;
    private String winMoneyFrom;
    private double amountWin;

    public WinMoneyAC(DeckType deckType, String message, Boolean holdable, String winFrom, double amount) {
        super(deckType, message, holdable);
        winMoneyFrom = winFrom.toUpperCase();
        amountWin = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        if(winMoneyFrom.equalsIgnoreCase("ALL")){
            for(AbstractPlayer p : game.getPlayers()){
                if(!p.equals(curr)){
                    p.makePayment(amountWin, curr);
                }
            }
        }
        else{
            game.getBank().makePayment(amountWin, curr);
        }
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
