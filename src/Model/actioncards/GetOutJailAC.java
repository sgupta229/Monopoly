package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;

import java.util.List;

public class GetOutJailAC extends AbstractActionCard {
    public GetOutJailAC(DeckType deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Deprecated
    public GetOutJailAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
    }

    public GetOutJailAC(DeckType deckType, String message, Boolean holdable, List<String> extraStrings, List<Double> extraDoubles){
        super(deckType, message, holdable);
    }

    /**
     * If player is currently in jail and this is used, sets jail to false
     * If player is on chance/community chest space and draws this (therefore not in jail), it is added to their action cards
     * @param game takes in game to find current player
     */
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
