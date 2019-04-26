package Model.spaces;
import Controller.*;
import Model.AbstractPlayer;
import Model.properties.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/***
 * This will be the interface that represents all the different kind of spaces
 * that can be on the board (a property space, jail space, free parking, chance space, etc)
 * It will hold a list of which players (if any) are on itself
 */

public abstract class AbstractSpace implements Serializable {
    private int myLocation;
    private String myName;
    transient ObservableList<AbstractPlayer> myOccupants = FXCollections.observableArrayList();
    private SpaceGroup myGroup;
    private Property myProp;
    private String myPopString;

    public AbstractSpace(int locationIndex, String spaceName, String spaceGroup,
    String jumpToSpace, List<Double> taxNums, Property prop){
        myLocation = locationIndex;
        myName = spaceName;
        myGroup = SpaceGroup.valueOf(spaceGroup);
        myProp = prop;
    }

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
    public abstract void doAction(AbstractGame game, int userChoice);

    /***
     * Getter method that gets all the players on the given space
     * @return the list of Occupants
     */
    public ObservableList<AbstractPlayer> getOccupants(){
        return myOccupants;
    }

    public abstract List getInfo();

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
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        AbstractSpace that = (AbstractSpace) o;
        return myLocation == that.myLocation &&
                myName.equals(that.myName);
    }

    public SpaceGroup getMyGroup(){
        return myGroup;
    }

    public void setMyProp(Property prop){
        myProp = prop;
    }

    public void setMyGroup(SpaceGroup group){
        myGroup = group;
    }

    public Property getMyProp(){return myProp;}

    @Override
    public int hashCode() {
        return Objects.hash(myLocation, myName);
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

    public abstract  String getPopString(AbstractGame game);

    protected void setPopString(String popupString){
        myPopString = popupString;
    }

    //public abstract PopUps generatePopUp();
}
