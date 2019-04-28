package Model.spaces;

import Controller.AbstractGame;
import Model.properties.Property;

import java.util.List;
/***
 * Author: Dylan Karul
 * Purpose: This abstract class is used for all different types of Properties Spaces on the board (such as
 * Junior Property Spaces, Classic Game Property Spaces, etc)
 * Dependencies: It is dependent on AbstractGame, Property, and AbstractSpace.
 * How to use it: You would extend this class for any new type of monopoly game that you wanted
 * to create, and use it to represent your main properties types of spaces on the board. Calling the doAction method
 * allows you to invoke the action necesarry if someone lands on this space.
 */
public abstract class AbstractPropSpace extends AbstractSpace {


    private Property myProperty;

    public AbstractPropSpace(int locationIndex, String spaceName, String spaceGroup,
                             String jumpToSpace, List<Double> taxNums, Property myProp){
        super(locationIndex, spaceName, spaceGroup, jumpToSpace, taxNums, myProp);

        myProperty = myProp;

    }

    public AbstractPropSpace(int locationIndex, String spaceName, Property prop){
        super(locationIndex, spaceName);

        myProperty = prop;

    }

    /***
     * returns the metadata concerning the literal property that this space is linked to,
     * such as its name, rent, and more
     * @return
     */
    public List getInfo(){
        return myProperty.getInfo();
    }

    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public abstract void doAction(AbstractGame game, int userChoice);

    public Property getMyProperty(){
        return myProperty;
    }



}
