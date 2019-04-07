package Model;

import Controller.Game;

import java.util.ArrayList;
import java.util.List;

public class IncomeTaxSpace extends Space{

    List<Player> myOccupants = new ArrayList<>();
    float flatRate;
    float percentageTaken;
    Transfer taxReceiver;

    public IncomeTaxSpace(float myRate, float myPercentage, Transfer myTaxReceiver){
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
    public void doAction(Game game){
        Player currentPlayer = game.getCurrentPlayer();
        float playersWealth = currentPlayer.getFunds();
        float amountTaxed = playersWealth-flatRate-(playersWealth*percentageTaken);
        currentPlayer.makePayment(amountTaxed, taxReceiver);
        taxReceiver.receivePayment(amountTaxed);
    }


}
