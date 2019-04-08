package Model;
import Controller.*;
import java.util.*;

/***
 * This will be the interface that represents all the different kind of spaces
 * that can be on the board (a property space, jail space, free parking, chance space, etc)
 * It will hold a list of which players (if any) are on itself
 */

public abstract class AbstractSpace {
    private int myLocation;
    private String myName;
    List<AbstractPlayer> myOccupants = new ArrayList<>();

    public AbstractSpace(int locationIndex, String spaceName){
        myLocation = locationIndex;
        myName = spaceName;
    }

    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public abstract void doAction(AbstractGame game);

    /***
     * Getter method that gets all the players on the given space
     * @return the list of Occupants
     */
    public List<AbstractPlayer> getOccupants(){
        return myOccupants;
    }

    /***
     * adds a player to the list of players on the space
     * @param newOccupant the player that is now on the spot
     */
    public void addOccupant(AbstractPlayer newOccupant){
        if(!myOccupants.contains(newOccupant)){
            myOccupants.add(newOccupant);
        }
    }

    public int getMyLocation(){
        return myLocation;
    }

    public String getMyName(){
        return myName;
    }

    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o==null || o.getClass()!=this.getClass()){
            return false;
        }

        AbstractSpace space = (AbstractSpace) o;
        return (space.getMyName().equals(this.getMyName()) && space.getMyLocation() == this.getMyLocation());
    }

    @Override
    public int hashCode(){
        return this.getMyLocation();
    }

    /***
     * removes a player to the list of players on the space
     * @param occupantToRemove the player that has left the spot
     */
    public void removeOccupant(AbstractPlayer occupantToRemove){
        if(myOccupants.contains(occupantToRemove)){
            myOccupants.remove(occupantToRemove);
        }
    }
}
