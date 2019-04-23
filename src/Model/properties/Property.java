package Model.properties;

import Model.AbstractPlayer;

import java.io.Serializable;
import java.util.*;

/***
 * This class holds all the information regarding a property such as its color, price, rent amount,
 * rent amount with monopoly, cost of placing a house, cost of placing a hotel, cost of rent
 * with every specific  number of houses, cost of rent with a hotel, and the mortgage cost
 */
public abstract class Property implements Serializable {
    protected ResourceBundle general;

    private double myPrice;
    private double mortgageAmount;
    private String myGroup;
    private String myName;
    protected String myColor;
    private Boolean isMortgaged;
    private Boolean isOwned;
    private List allPaymentInfo;
    private int myGroupSize;
    private Map<BuildingType, Double> buildingPrices;
    private Map<BuildingType, Integer> buildingMap;

    /////need property to take in building map with buildingtypes as keys already
    ///// and also buildingprices that is already populated, and just set these

    public Property(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
//        isMortgaged = false;
        isOwned = false;
        myPrice=price;
        myName = propName;
        myColor = color;
        allPaymentInfo = paymentInfo;
        myGroupSize = groupSize;
        initializePaymentInfo(allPaymentInfo);
        buildingPrices = buildingPriceMap;
        this.buildingMap = new HashMap<>();
        general = ResourceBundle.getBundle("GeneralInfo");

        for(BuildingType buildingType : buildingPrices.keySet()){
            buildingMap.put(buildingType, 0);
        }

    }

    public Property(double price, String propName,  List<Double> paymentInfo, int groupSize){
//        isMortgaged = false;
        myPrice=price;
        myName = propName;
        allPaymentInfo = paymentInfo;
        myGroupSize = groupSize;
        initializePaymentInfo(allPaymentInfo);
        general = ResourceBundle.getBundle("GeneralInfo");
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

    public void addBuilding(BuildingType building) {

    }

    public void removeBuilding(BuildingType building, int amount) {

    }

    public int getNumBuilding(BuildingType building) {
        return 0;
    }

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

    public abstract List getInfo();

    public String getMyName(){
        return myName;
    }

    public String getColor(){
        return myColor;
    }

    public String getGroup(){
        return myGroup;
    }

    public void setGroup(String group){
        myGroup = group;
    }

    public int getMyGroupSize(){
        return myGroupSize;
    }

    public void setMyColor(String color){
        myColor=color;
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

    public void setIsOwned(boolean ownedOrNot){
        isOwned = ownedOrNot;
    }

    public boolean getIsOwned(){
        return isOwned;
    }

    public boolean getIsMortgaged(){
        return isMortgaged;
    }

    public double getMortgageAmount(){
        return mortgageAmount;
    }


    public void setMortgageAmount(Double amount){
        mortgageAmount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Double.compare(property.myPrice, myPrice) == 0 &&
                myGroup.equals(property.myGroup) &&
                myName.equals(property.myName); //&&
        //myColor.equals(property.myColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myGroup, myName, myColor);
    }

    public double getBuildingPrice(BuildingType building){
        return buildingPrices.get(building);
    }

    public Map<BuildingType, Integer> getBuildingMap() {
        return buildingMap;
    }

    public Map<BuildingType, Double> getBuildingPrices() {
        return buildingPrices;
    }
}
