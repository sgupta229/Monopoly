package Model.spaces;

import Controller.AbstractGame;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import Model.AbstractPlayer;
import Model.Bank;

public class PropSpace extends AbstractSpace {


    private Property myProperty;


    public PropSpace(int locationIndex, String spaceName, Property prop){
        super(locationIndex, spaceName);

        myProperty = prop;

    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game, int userChoice) {
        AbstractPlayer propOwner = game.getBank().propertyOwnedBy(myProperty);
        Bank bank = game.getBank();
        AbstractPlayer currPlayer = game.getCurrPlayer();
        System.out.println("this is my property " + myProperty.getName());
        double propertyPrice = myProperty.getPrice();
//        int lastDiceRoll = game.getLastDiceRoll();
        if (propOwner == null) {
            if (userChoice == 0) { //aka buy property
                bank.setPropertyOwner(myProperty, currPlayer);
                currPlayer.makePayment(propertyPrice, bank);
                currPlayer.addProperty(myProperty); //have to update the players assets
                //front end updates this somewhere?
            } else {
                //start auction process.
                //game.startAuction();
            }
        } else { //property is owned by someone, what are the choices?
            //pay rent or...
            currPlayer.makePayment(myProperty.calculateRent(propOwner, 2), propOwner);
        }
        //else()
    }



}
