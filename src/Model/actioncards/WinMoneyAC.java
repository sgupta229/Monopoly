package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import java.util.List;

public class WinMoneyAC extends AbstractActionCard {
    //private List<Transfer> winMoneyFrom;
    private String winMoneyFrom;
    private double amountWin;

    public WinMoneyAC(DeckType deckType, String message, Boolean holdable, String winFrom, double amount) {
        super(deckType, message, holdable);
        winMoneyFrom = winFrom.toUpperCase();
        amountWin = amount;
    }

    @Deprecated
    public WinMoneyAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
        winMoneyFrom = extraString.toUpperCase();
        amountWin = extraDoubles.get(0);
    };

    public WinMoneyAC(DeckType deckType, String message, Boolean holdable, List<String> extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
        winMoneyFrom = extraString.get(0).toUpperCase();
        amountWin = extraDoubles.get(0);
    };

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
