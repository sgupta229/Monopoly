package Model;

import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class CommunityChestSpace extends Space{

    List<Player> myOccupants = new ArrayList<>();
    Deck myDeck;

    public CommunityChestSpace(Deck deckType){
        myDeck = deckType;
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(Game game){
        ActionCard cardDrawn = myDeck.drawCard();
        cardDrawn.doAction(game);
        myDeck.discardCard(cardDrawn);
    }


}
