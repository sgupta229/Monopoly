package Model.spaces;

import Controller.AbstractGame;
import Model.FreeParkingFunds;
import Model.Transfer;
import Model.properties.Property;
import Model.AbstractPlayer;

import java.util.ArrayList;
import java.util.List;

public class FreeParkingSpace extends AbstractSpace {

    private static final String myPopString = "Corner";

    public FreeParkingSpace(int locationIndex, String spaceName, String spaceGroup,
                            String jumpToSpace, List<Double> taxNums, Property myProp){

        super(locationIndex, spaceName, spaceGroup, jumpToSpace, taxNums, myProp);
        setPopString(myPopString);
    }


    public FreeParkingSpace(int locationIndex, String spaceName){
        super(locationIndex, spaceName);
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
        if(game.getFreeParkingRule()){
            System.out.println("i am in the free parking space");
            AbstractPlayer curr = game.getCurrPlayer();
            Transfer fpFunds = game.getFreeParking();
            fpFunds.makePayment(0, curr);

        }
    }

    public String getPopString(AbstractGame game){
        return myPopString;
    }




}
