package Model.spaces;

import Controller.AbstractGame;
import Model.properties.Property;
import Model.spaces.AbstractSpace;

public class PropSpace extends AbstractSpace {


    Property myProperty;

    public PropSpace(int locationIndex, String spaceName){
        super(locationIndex, spaceName);

    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(AbstractGame game){
        if(game.getBank().propertyOwnedBy(myProperty)==null){
            //prompt front end button to purchase property, handler does the rest
        }
        game.endTurn();
    }

    public void linkSpaceToProperty(Property property){
        myProperty = property;
    }


}
