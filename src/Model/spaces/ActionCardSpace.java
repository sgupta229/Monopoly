package Model.spaces;

import Controller.AbstractGame;

import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.actioncards.DeckType;
import Model.properties.Property;


import java.util.ArrayList;
import java.util.List;

public class ActionCardSpace extends AbstractSpace {

    private DeckType myDeckType;
    private static final String myPopString = "ActionCard";

    public ActionCardSpace(int locationIndex, String spaceName, String spaceGroup,
                           String jumpToSpace, List<Double> taxNums, Property myProp){
        super(locationIndex, spaceName, spaceGroup, jumpToSpace, taxNums, myProp);
        myDeckType = DeckType.valueOf(spaceName);
        setPopString(myPopString);
    }

    public ActionCardSpace(int locationIndex, String deckType){
        super(locationIndex, deckType);
        myDeckType = DeckType.valueOf(deckType);
    }

    public List getInfo(){
        return new ArrayList();
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game, int userChoice){
        AbstractActionCard cardDrawn;
        List<ActionDeck> tempDecks = game.getMyActionDecks();
        for(ActionDeck d : tempDecks) {
            if (d.getMyDeckType() == myDeckType) {
                cardDrawn = d.drawCard();
                //cardDrawn.doCardAction(game);
                game.setCurrentActionCard(cardDrawn);
            }
        }
    }

    public String getPopString(AbstractGame game){
        return myPopString;
    }


}
