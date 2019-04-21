package Model.spaces;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.Transfer;
import Model.properties.Property;
import Model.spaces.AbstractSpace;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class TaxSpace extends AbstractSpace {

    private double flatRate;
    private double percentageTaken;
    private Transfer taxReceiver;
    private final int TAKE_FIXED_AMOUNT=0;
    private final int TAKE_PERCENTAGE=1;

    public TaxSpace(int locationIndex, String spaceName, String spaceGroup,
                    String jumpToSpace, List<Double> taxNums, Property myProp){
        super(locationIndex, spaceName, spaceGroup, jumpToSpace, taxNums, myProp);
        flatRate = taxNums.get(0);
        percentageTaken = taxNums.get(1)/100;
        //taxReceiver = myTaxReceiver;
        //assuming that tax goes to the bank
    }

    public TaxSpace(int locationIndex, String spaceName, double myRate, double myPercentage){
        super(locationIndex, spaceName);
        flatRate = myRate;
        percentageTaken = myPercentage;
        //taxReceiver = myTaxReceiver;
        //assuming that tax goes to the bank
    }

    public List getInfo(){
        ArrayList ret = new ArrayList();
        ret.addAll(Arrays.asList(flatRate, percentageTaken*100, percentageTaken));
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
        double amountTaxed;
        if(game.getFreeParkingRule()){
            taxReceiver = game.getFreeParking();
        }
        else{
            taxReceiver = game.getBank();
        }

        AbstractPlayer currentPlayer = game.getCurrPlayer();
        double playersWealth = currentPlayer.getFunds();
        if(userChoice==TAKE_FIXED_AMOUNT){
            amountTaxed = flatRate;
        }
        else{
            amountTaxed = playersWealth * percentageTaken;
        }
        currentPlayer.makePayment(amountTaxed, taxReceiver);
    }


}
