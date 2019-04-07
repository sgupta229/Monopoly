package Model;

import Controller.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxSpace extends Space{

    List<AbstractPlayer> myOccupants = new ArrayList<>();
    double flatRate;
    double percentageTaken;
    Transfer taxReceiver;

    public IncomeTaxSpace(int locationIndex, String spaceName,double myRate, double myPercentage, Transfer myTaxReceiver){
        super(locationIndex, spaceName);
        flatRate = myRate;
        percentageTaken = myPercentage;
        taxReceiver = myTaxReceiver;
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(AbstractGame game){
        AbstractPlayer currentPlayer = game.getCurrPlayer();
        double playersWealth = currentPlayer.getFunds();
        double amountTaxed = playersWealth-flatRate-(playersWealth*percentageTaken);
        currentPlayer.makePayment(amountTaxed, taxReceiver);
        taxReceiver.receivePayment(amountTaxed);
    }


}
