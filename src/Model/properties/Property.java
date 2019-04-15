package Model.properties;

import Model.AbstractPlayer;

import java.util.List;

/***
 * This class holds all the information regarding a property such as its color, price, rent amount,
 * rent amount with monopoly, cost of placing a house, cost of placing a hotel, cost of rent
 * with every specific  number of houses, cost of rent with a hotel, and the mortgage cost
 */
public abstract class Property {

    private double myPrice;
    private double mortgageAmount;
    private String myGroup;
    private String myName;
    private String myColor;
    private Boolean isMortgaged;
    private List allPaymentInfo;
    private int myGroupSize;


    public Property(double price, String propName,  List<Double> paymentInfo, int groupSize){
        myPrice=price;
        myName = propName;
        allPaymentInfo = paymentInfo;
        myGroupSize = groupSize;
        initializePaymentInfo(allPaymentInfo);
    }

    @Deprecated
    public Property(double price, String propName, String color, List<Double> paymentInfo){
        myPrice=price;
        myName = propName;
        myColor=color;
        allPaymentInfo = paymentInfo;
        initializePaymentInfo(allPaymentInfo);

    }

    @Deprecated
    public Property(double price, String propName){
        myPrice=price;

        myName = propName;

    }

    protected abstract void initializePaymentInfo(List<Double> paymentInformation) throws IndexOutOfBoundsException;

    /***
     * A getter method that returns the name of this property
     * @return the name of Property
     */
    public String getName(){
        return myName;
    }

    /***
     * A getter method that returns the price of this property
     * @return the cost to purchase this property
     */
    public double getPrice(){
        return myPrice;
    }


    public String getColor(){
        return myColor;
    }

    public String getGroup(){
        return myGroup;
    }

    public int getMyGroupSize(){
        return myGroupSize;
    }


    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public abstract double calculateRent(AbstractPlayer propOwner, int lastDiceRoll);

    public void setIsMortgaged(boolean mortgageVal){
        isMortgaged = mortgageVal;
    }

    public boolean getIsMortgaged(){
        return isMortgaged;
    }

    public double getMortgageAmount(){
        return mortgageAmount;
    }


}
