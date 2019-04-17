package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;

import java.util.List;

public class GetOutJailAC extends AbstractActionCard {
    public GetOutJailAC(DeckType deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    public GetOutJailAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
    }

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        if(curr.isInJail()){
            curr.setJail(false);
            ActionDeck d = this.getMyDeck();
            d.discardCard(this);
        }
        else{
            curr.addActionCard(this);
        }
    }
}
