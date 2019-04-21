package Model.spaces;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.Bank;
import Model.properties.Property;

import java.util.List;

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
