package Model.spaces;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.Bank;
import Model.properties.Property;

import java.util.List;

public class JuniorPropSpace extends AbstractPropSpace {


    private Property myProperty;
    private static final String myUnOwnedPopString = "JuniorBuyProperty";
    private static final String myOwnedPopString = "JuniorPayRent";
    private static final String myPopString = "ClassicProp";

    public JuniorPropSpace(int locationIndex, String spaceName, String spaceGroup,
                           String jumpToSpace, List<Double> taxNums, Property myProp){
        super(locationIndex, spaceName, spaceGroup, jumpToSpace, taxNums, myProp);
        setPopString(myPopString);
        myProperty = myProp;

    }


    //public List getInfo(){
        //return myProperty.getInfo();
    //}

    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game, int userChoice) {
        AbstractPlayer currPlayer = game.getCurrPlayer();
        Bank bank = game.getBank();
        AbstractPlayer propOwner = game.getBank().propertyOwnedBy(myProperty);
        double propertyPrice = myProperty.getPrice();
        int lastDiceRoll = game.getLastDiceRoll();
        if (propOwner == null) {
            if(currPlayer.getFunds()>myProperty.getPrice()){
                currPlayer.addProperty(myProperty); //have to update the players assets
                currPlayer.makePayment(game.getBank(), propertyPrice, bank);
                bank.setPropertyOwner(myProperty, currPlayer);
                myProperty.setIsOwned(true);
                //front end updates this somewhere?
            }
            else{
                //we want this player to be removed and then the game would end
                //so im going to call make payment of that amount
                //they wont be able to make the payment, and
                //some popup will show up saying, you dont have enough,
                //you go bankrupt, game over, player with most money wins
                currPlayer.makePayment(game.getBank(), propertyPrice, bank);
            }
        } else { //property is owned by someone, what are the choices?
            currPlayer.makePayment(game.getBank(), myProperty.calculateRent(propOwner, lastDiceRoll), propOwner);
        }
        //else()
    }

    public String getPopString(AbstractGame game){
        //need to slightly change this class
        if(myProperty.getIsOwned() && game.getBank().propertyOwnedBy(myProperty)!=game.getCurrPlayer()){
            return myOwnedPopString;
        }
        else if(game.getBank().propertyOwnedBy(myProperty)==game.getCurrPlayer()){
            return null;
        }
        return myUnOwnedPopString;
    }


    //public Property getMyProperty(){
        //return myProperty;
    //}





}
