package Model;
import Controller.*;
import java.util.*;

/***
 * This will be the interface that represents all the different kind of spaces
 * that can be on the board (a property space, jail space, free parking, chance space, etc)
 * It will hold a list of which players (if any) are on itself
 */

public abstract class Space {
    //private int myLocation;
    //private int myName;
    List<Player> myOccupants = new ArrayList<>();

    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public abstract void doAction(Game game);

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

    public int getMyLocation();

    public String getMyName();

    public boolean equals(Space o);
    
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
