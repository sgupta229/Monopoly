package Model;

import Controller.ConfigReader;
import Model.properties.ColorProperty;
import Model.properties.Property;

import java.util.*;

public class Bank implements Transfer{

    Map<Property, AbstractPlayer> ownedPropsMap = new HashMap<>();
    Set<Property> unOwnedProps;
    private double myBalance;
    private double numHouses;
    private double numHotels;
    private double maxHousesPerProp;


    @Deprecated
    public Bank(double startingBalance, List<Property> properties){
        myBalance=startingBalance;
        unOwnedProps = new HashSet<Property>(properties);
    }

    public Bank(List<Double> allInfo, List<Property> properties){
        myBalance=allInfo.get(0);
        numHouses = allInfo.get(1);
        numHotels = allInfo.get(2);
        maxHousesPerProp = allInfo.get(3);
        unOwnedProps = new HashSet<Property>(properties);
    }


    /***
     * Returns the player that owns a specific property, or null if no one does
     * @param property
     * @return
     */
    public AbstractPlayer propertyOwnedBy(Property property){
        if(ownedPropsMap.containsKey(property)){
            return ownedPropsMap.get(property);
        }
        else if (unOwnedProps.contains(property)){
            return null;
        }
        //need to turn this into a try catch
        return null;
    }

    public void setPropertyOwner(Property property, AbstractPlayer newOwner){
        if(ownedPropsMap.containsKey(property)){
            ownedPropsMap.put(property, newOwner);
        }
        if(unOwnedProps.contains(property)){
            System.out.println("here");
            ownedPropsMap.put(property, newOwner);
            unOwnedProps.remove(property);
        }
        ownedPropsMap.put(property, newOwner);
        for (Map.Entry<Property,AbstractPlayer> k : ownedPropsMap.entrySet()){
            System.out.println("Just Purchased by "+ k.getKey().getName() + " " + k.getValue().getName());
        }
        for (Property k : unOwnedProps){
            System.out.println("Unowned "+ k.getName());
        }
    }

    /***
     * Allows a player/bank (or anyone that implements Transfer) to pay others
     * @param amount
     * @param receiver
     */
    public void makePayment(double amount, Transfer receiver){
        if(myBalance-amount>0){
            myBalance -= amount;
            receiver.receivePayment(amount);
        }
        else{
            receiver.receivePayment(myBalance);
            myBalance = 0;
        }
    }

    /***
     * Allows anyone that implements Transfer to receive a payment from another
     * @param amount
     */
    public void receivePayment(double amount){
        myBalance+=amount;
    }

    /***
     * Sells a specific property to a player
     * @param property
     * @param purchaser
     */
    public void sellProperty(Property property, AbstractPlayer purchaser){}

    public void mortgageProperty(Property property){
        AbstractPlayer propOwner = ownedPropsMap.get(property);
        propOwner.makePayment(property.getMortgageAmount(), this);
        property.setIsMortgaged(true);
    }

    public void build(Property property){

    }

    /***
     * Auctions off a property to the list of players at the auction
     * @param property
     * @param auctionGoers
     */
    public void auctionProperty(Property property, List<AbstractPlayer> auctionGoers){}

    public double getBankBalance(){
        return myBalance;
    }

    public void giveHouse(){
        numHouses--;
    }
    public void returnHouse(){
        numHouses++;
    }

    public void giveHotel(){
        numHotels++;
    }
    public void returnHotel(){
        numHotels++;
    }

    public double getNumHouses(){
        return numHouses;
    }
    public double getNumHotels(){
        return numHotels;
    }
}
