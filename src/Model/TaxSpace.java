package Model;

import Controller.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class TaxSpace extends AbstractSpace {

    double flatRate;
    double percentageTaken;
    Transfer taxReceiver;

    public TaxSpace(int locationIndex, String spaceName, double myRate, double myPercentage){
        super(locationIndex, spaceName);
        flatRate = myRate;
        percentageTaken = myPercentage;
        //taxReceiver = myTaxReceiver;
        //assuming that tax goes to the bank
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(AbstractGame game){
        double amountTaxed;
        taxReceiver = game.getBank();
        AbstractPlayer currentPlayer = game.getCurrPlayer();
        double playersWealth = currentPlayer.getFunds();
        double tempFlat = playersWealth-flatRate;
        double tempPercent =  playersWealth - (playersWealth*percentageTaken);
        if(percentageTaken>0){
            if(tempFlat>tempPercent){
                amountTaxed = flatRate;
            }
            else{
                amountTaxed = playersWealth*percentageTaken;
            }
        }
        else{
            amountTaxed = flatRate;
        }
        currentPlayer.makePayment(amountTaxed, taxReceiver);
        game.endTurn();
    }


}
