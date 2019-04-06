package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassicBank implements Bank, Transfer{

    Map<Property, Player> ownedPropsMap = new HashMap<>();
    List<Property> unOwnedProps = new ArrayList<>();
    float myBalance;

    public ClassicBank(float startingBalance){
        myBalance=startingBalance;
    }

    /***
     * Returns the player that owns a specific property, or null if no one does
     * @param property
     * @return
     */
    public Player propertyOwnedBy(Property property){
        if(ownedPropsMap.containsKey(property)){
            return ownedPropsMap.get(property);
        }
        else if (unOwnedProps.contains(property)){
            return null;
        }
        //need to turn this into a try catch
        return null;
    }

    /***
     * Allows a player/bank (or anyone that implements Transfer) to pay others
     * @param amount
     * @param receiver
     */
    public void makePayment(float amount, Transfer receiver){
        myBalance -= amount;
        receiver.receivePayment(amount);
        //what happens if bank doesn't have enough
    }

    /***
     * Allows anyone that implements Transfer to receive a payment from another
     * @param amount
     */
    public void receivePayment(float amount){
        myBalance+=amount;
    }

    /***
     * Sells a specific property to a player
     * @param property
     * @param purchaser
     */
    public void sellProperty(Property property, Player purchaser){}

    /***
     * Auctions off a property to the list of players at the auction
     * @param property
     * @param auctionGoers
     */
    public void auctionProperty(Property property, List<Player> auctionGoers){}
}
