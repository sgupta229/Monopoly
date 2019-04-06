package Model;

import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class PropSpace implements Space{

    List<Player> myOccupants = new ArrayList<>();
    Property myProperty;

    public PropSpace(Property thisProperty){
        myProperty = thisProperty;
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(Game game){
        if(game.getBank().propertyOwnedBy(myProperty)==null){
            //prompt front end button to purchase property, handler does the rest
        }
    }

    /***
     * Getter method that gets all the players on the given space
     * @return the list of Occupants
     */
    public List<Player> getOccupants(){
        return myOccupants;
    }

    /***
     * adds a player to the list of players on the space
     * @param newOccupant the player that is now on the spot
     */
    public void addOccupant(Player newOccupant){
        if(!myOccupants.contains(newOccupant)){
            myOccupants.add(newOccupant);
        }
    }

    /***
     * removes a player to the list of players on the space
     * @param occupantToRemove the player that has left the spot
     */
    public void removeOccupant(Player occupantToRemove){
        if(myOccupants.contains(occupantToRemove)){
            myOccupants.remove(occupantToRemove);
        }
    }
}
