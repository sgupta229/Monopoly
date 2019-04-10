package Model;

import Controller.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class ActionCardSpace extends AbstractSpace {

    List<AbstractPlayer> myOccupants = new ArrayList<>();
    DeckType myDeckType;

    public ActionCardSpace(int locationIndex, String deckType){
        super(locationIndex, deckType);
        myDeckType = DeckType.valueOf(deckType);
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game){
        AbstractActionCard cardDrawn;
        List<ActionDeck> tempDecks = game.getMyActionDecks();
        for(ActionDeck d : tempDecks) {
            if (d.getMyDeckType() == myDeckType) {
                cardDrawn = d.drawCard();
                cardDrawn.doCardAction(game);
            }
        }
    }
}
