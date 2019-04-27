package Model;



import Controller.AbstractGame;

import Model.properties.BuildingType;
import Model.properties.Property;
import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;

/***
 * Author: Dylan Karul
 * Purpose: This class is used for all financing aspects of Monopoly. It gives players
 * properties, takes and gives out money when necessary, allows players to build on properties,
 * and keeps track of who owns which properties
 * Assumptions: This is under the assumption that it gets its data in the right form
 * but we make this assumption because we check for this in config reader when reading in
 * the data, so it always should be getting proper data.
 * Dependencies: It is dependent on AbstractGame, AbstractPlayer, the Transfer interface,
 * the BuildingType enum class, and Property.
 * How to use it: You would use this class by calling a new Bank and giving it the necessary info
 * that the bank needs to know about the game (how much money it has, the list of properties in the game,
 * the types of buildings and how many buildings it has). Then, you can call its method such as: mortgage,
 * which mortgages a property and pays its owner the mortgage value.
 */

public class Bank implements Transfer, Serializable {

    Map<Property, AbstractPlayer> ownedPropsMap = new HashMap<>();
    Set<Property> unOwnedProps;
    private double myBalance;
    private Map<BuildingType, Integer> totalBuildingMap;
    private Map<BuildingType, Integer> maxBuildingsPerProp;
    private boolean evenBuildingRule;
    List<BuildingType> typesOfBuildings = new ArrayList<>();
    private static final double ONE_POINT_ONE = 1.1;

    public Bank(List<Double> allInfo, List<Property> properties, List<Map<BuildingType, Integer>> buildingInfo){
        myBalance=allInfo.get(0);
        unOwnedProps = new HashSet<>(properties);
        totalBuildingMap = buildingInfo.get(0);
        maxBuildingsPerProp = buildingInfo.get(1);
        typesOfBuildings.addAll(maxBuildingsPerProp.keySet());
        //////totalBuildingMap.put(something);
        //////maxBuildingsPerProp.put(something);
    }

    public Bank() {

    }

    @Deprecated
    public Bank(double startingBalance, List<Property> properties){
        myBalance=startingBalance;
        unOwnedProps = new HashSet<>(properties);
    }

    ////need to take in atotalBuildingmap and maxbuilingPerPropmap
    /////and set them here
    @Deprecated
    public Bank(List<Double> allInfo, List<Property> properties){
        myBalance=allInfo.get(0);
        unOwnedProps = new HashSet<>(properties);
        //////totalBuildingMap.put(something);
        //////maxBuildingsPerProp.put(something);
    }


    /***
     * Returns the player that owns a specific property, or null if no one does
     * @param property
     * @return
     */
    public AbstractPlayer propertyOwnedBy(Property property){
        if(ownedPropsMap.containsKey(property)){
//            System.out.println("PROPERTY IN OWNED MAP");
//            System.out.println("MY OWNER IS: " + ownedPropsMap.get(property));
            return ownedPropsMap.get(property);
        }
        else if (unOwnedProps.contains(property)){
            return null;
        }
//        System.out.println("not in either");
        //TODO: need to turn this into a try catch
        return null;
    }

    /***
     * purpose: when someone buys a property, you call this to update the map
     * of properties to who owns them, so that if someone needs to check who owns a property,
     * they have the proper info
     * @param property the prop that is being bought
     * @param newOwner the person buying the prop
     */
    public void setPropertyOwner(Property property, AbstractPlayer newOwner){
        if(ownedPropsMap.containsKey(property)){
            ownedPropsMap.put(property, newOwner);
        }
        else if(unOwnedProps.contains(property)){
            ownedPropsMap.put(property, newOwner);
            unOwnedProps.remove(property);
        }
    }

    /***
     * Allows a player/bank (or anyone that implements Transfer) to pay others
     * @param amount
     * @param receiver
     */
    public void makePayment(Bank bank, double amount, Transfer receiver){
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
     * A player sells a property back
     * @param property
     */
    public void sellBackProperty(Property property, AbstractGame game){
        AbstractPlayer propOwner = ownedPropsMap.get(property);
        //this.makePayment(property.getMortgageAmount(), propOwner);
        ownedPropsMap.remove(property);
        unOwnedProps.add(property);
        propOwner.removeProperty(property);
        property.setIsOwned(false);
        //game.startAuction();
    }

    /***
     * a checker for if someone can mortgage or not, before the bank
     * mortgages their property
     * @param property
     * @return true if they can, false if not
     */
    public boolean checkIfCanMortgage(Property property){
        for(BuildingType bType:typesOfBuildings){
            if(property.getNumBuilding(bType)!=0){
                return false;
            }
        }

        return !property.getIsMortgaged();
    }

    /***
     * A method that mortgages a property for a player,
     * pays that player, and sets the props mortgage value to true
     * @param property the prop that is being mortgaged
     */
    public void mortgageProperty(Property property){
        if(checkIfCanMortgage(property)){
            AbstractPlayer propOwner = ownedPropsMap.get(property);
            this.makePayment(this, property.getMortgageAmount(), propOwner);
            property.setIsMortgaged(true);
        }
    }


    /***
     * Unmortgages a property for a player so they can collect rent on it again
     * @param property
     */
    public void unMortgageProperty(Property property){
        if(property.getIsMortgaged()){
            AbstractPlayer propOwner = ownedPropsMap.get(property);
            propOwner.makePayment(this, property.getMortgageAmount()*ONE_POINT_ONE, this);
            property.setIsMortgaged(false);
        }
    }


    /***
     * checks the various reasons why someone wouldn't be allowed to build a specific building,
     * such as them not having a monopoly on that color, etc
     * @param property the prop the player wants to build on
     * @param building the building they want to erect
     * @return true if they can build, false if not
     */
    public boolean checkIfCanBuild(Property property, BuildingType building){
        if(typesOfBuildings.indexOf(building)>0 && property.getNumBuilding(building)==0){
            BuildingType bBefore = typesOfBuildings.get(typesOfBuildings.indexOf(building)-1);
            if(property.getNumBuilding(bBefore)!=maxBuildingsPerProp.get(bBefore)){
                return false;
            }
        }
        for(int x= typesOfBuildings.indexOf(building)+1; x<typesOfBuildings.size(); x++){
            if(property.getNumBuilding(typesOfBuildings.get(x))!=0){
                return false;
            }
        }
        System.out.println("this is the building to build" + building);

        if(maxBuildingsPerProp.get(building)==property.getNumBuilding(building)){
            System.out.println("1");
            return false;
        }
        if(totalBuildingMap.get(building)==0){
            System.out.println("2");
            return false;
        }
        if(ownedPropsMap.get(property).getFunds() < property.getBuildingPrice(building)){
            System.out.println("3");
            return false;
        }
        if(evenBuildingRule){
            List<Property> otherProps = propertyOwnedBy(property).getPropertiesOfType(property.getColor());
            for(Property p: otherProps){
                if(p.getNumBuilding(building)==property.getNumBuilding(building)-1 || !(checkIfCanUpgrade(property, building))){
                    System.out.println("4");
                    return false;
                }
            }
        }
        if(property.getIsMortgaged()){
            System.out.println("5");
            return false;
        }
        System.out.println("6");
        return (propertyOwnedBy(property).checkMonopoly(property));
    }

    private boolean checkIfCanUpgrade(Property property, BuildingType building){
        if(typesOfBuildings.indexOf(building)>0){
            List<Property> otherProps = propertyOwnedBy(property).getPropertiesOfType(property.getColor());
            for(Property p: otherProps){
                BuildingType bBefore = typesOfBuildings.get(typesOfBuildings.indexOf(building)-1);
                if(p.getNumBuilding(bBefore)==property.getNumBuilding(bBefore)-1){
                    return false;
                }
            }
        }

        return true;
    }


    /***
     * erects a specific building structure on a property, if they player is allowed to do so
     * @param property the prop the player wants to build on
     * @param building the building they want to erect
     */
    public void build(Property property, BuildingType building){
        if(checkIfCanBuild(property, building)){
            System.out.println("can buildd");
            if(typesOfBuildings.indexOf(building)>0 && property.getNumBuilding(building)==0){
                BuildingType bBefore = typesOfBuildings.get(typesOfBuildings.indexOf(building)-1);
                int numOfPrevBuildings = property.getNumBuilding(bBefore);
                for(int x=0; x<numOfPrevBuildings; x++){
                    unbuildForUpgrade(property, bBefore);
                }
            }
            if (maxBuildingsPerProp.get(building) > property.getNumBuilding(building)) {
                AbstractPlayer propOwner = propertyOwnedBy(property);
                totalBuildingMap.put(building, totalBuildingMap.get(building) - 1);
                property.addBuilding(building);
                propOwner.makePayment(this, property.getBuildingPrice(building), this);
            }
        }
    }

    /***
     * this is called if a player wants to sell back a building for quick cash
     * @param property
     * @param building
     */
    public void sellBackBuildings(Property property, BuildingType building){
        AbstractPlayer propOwner = propertyOwnedBy(property);
        totalBuildingMap.put(building, totalBuildingMap.get(building)+1);
        property.removeBuilding(building, 1);
        this.makePayment(this, property.getBuildingPrice(building)/2, propOwner);
    }


    private void unbuildForUpgrade(Property property, BuildingType building){
        AbstractPlayer propOwner = propertyOwnedBy(property);
        totalBuildingMap.put(building, totalBuildingMap.get(building)+1);
        property.removeBuilding(building, 1);

    }


    /***
     * @return the banks balance
     */
    public double getBankBalance(){
        return myBalance;
    }

    /***
     * @return the props that are not owned
     */
    public Set<Property> getUnOwnedProps(){
        return unOwnedProps;
    }

    /***
     * sets if evenBuilding is on or off
     */
    public void setEvenBuildingRule(boolean bool){
        evenBuildingRule = bool;
    }

    /***
     * sets the starting funds for the bank
     */
    public void setFunds(double amount){
        myBalance = amount;
    }

    //public Map<BuildingType, Integer> getTotalBuildingMap() {
      //  return totalBuildingMap;
    //}

    /***
     * @return the different types of buildings a player can build
     */
    public List<BuildingType> getBuildingTypes(){
        return typesOfBuildings;
    }

    /***
     * sets the total amount of each building the bank has.
     */
    public void setTotalBuildingMap(BuildingType bt, Integer amnt) {
        this.totalBuildingMap.put(bt, totalBuildingMap.get(bt)+amnt);
    }

    public Map<Property, AbstractPlayer> getOwnedPropsMap() {
        return ownedPropsMap;
    }
}
