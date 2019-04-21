package Model;



import Controller.AbstractGame;

import Model.properties.BuildingType;
import Model.properties.Property;

import javax.swing.tree.TreeCellEditor;
import java.io.Serializable;
import java.util.*;

public class Bank implements Transfer, Serializable {

    Map<Property, AbstractPlayer> ownedPropsMap = new HashMap<>();
    Set<Property> unOwnedProps;
    private double myBalance;
    private Map<BuildingType, Integer> totalBuildingMap;
    private Map<BuildingType, Integer> maxBuildingsPerProp;
    boolean evenBuildingRule;

    public Bank(List<Double> allInfo, List<Property> properties, List<Map<BuildingType, Integer>> buildingInfo){
        myBalance=allInfo.get(0);
        unOwnedProps = new HashSet<>(properties);
        totalBuildingMap = buildingInfo.get(0);
        maxBuildingsPerProp = buildingInfo.get(1);
        //////totalBuildingMap.put(something);
        //////maxBuildingsPerProp.put(something);
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
            System.out.println("PROPERTY IN OWNED MAP");
            System.out.println("MY OWNER IS: " + ownedPropsMap.get(property));
            return ownedPropsMap.get(property);
        }
        else if (unOwnedProps.contains(property)){
            return null;
        }
        System.out.println("not in either");
        //TODO: need to turn this into a try catch
        return null;
    }

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
     * A player sells a property back
     * @param property
     */
    public void sellBackProperty(Property property, AbstractGame game){
        AbstractPlayer propOwner = ownedPropsMap.get(property);
        this.makePayment(property.getMortgageAmount(), propOwner);
        ownedPropsMap.remove(property);
        unOwnedProps.add(property);
        propOwner.removeProperty(property);
        game.startAuction();

    }

    public void mortgageProperty(Property property){
        AbstractPlayer propOwner = ownedPropsMap.get(property);
        this.makePayment(property.getMortgageAmount(), propOwner);
        property.setIsMortgaged(true);
    }


    public void unMortgageProperty(Property property){
        AbstractPlayer propOwner = ownedPropsMap.get(property);
        propOwner.makePayment(property.getMortgageAmount()*1.1, this);
        property.setIsMortgaged(false);
    }

/*    public void build(Buildable property, BuildingType building){
        if(!(property instanceof Property)) {
            throw new IllegalArgumentException("the Buildable is not a property");
        }*/
    public boolean checkIfCanBuild(Property property, BuildingType building){
        ArrayList<BuildingType> typesOfBuildings = new ArrayList<>();
        typesOfBuildings.addAll(maxBuildingsPerProp.keySet());

        if(typesOfBuildings.indexOf(building)>0 && property.getNumBuilding(building)==0){
            BuildingType bBefore = typesOfBuildings.get(typesOfBuildings.indexOf(building)-1);
            if(property.getNumBuilding(bBefore)!=maxBuildingsPerProp.get(bBefore)){
                return false;
            }
        }
        if(maxBuildingsPerProp.get(building)==property.getNumBuilding(building)){
            return false;
        }
        if(totalBuildingMap.get(building)==0){
            return false;
        }
        if(ownedPropsMap.get(property).getFunds() < property.getBuildingPrice(building)){
            return false;
        }
        if(evenBuildingRule){
            List<Property> otherProps = propertyOwnedBy(property).getPropertiesOfType(property.getColor());
            for(Property p: otherProps){
                if(p.getNumBuilding(building)==property.getNumBuilding(building)-1){
                    return false;
                }
            }
        }
        if(!propertyOwnedBy(property).checkMonopoly(property)){
            return false;
        }
        return true;

    }


    public void build(Property property, BuildingType building){

        if(checkIfCanBuild(property, building)){
            if (maxBuildingsPerProp.get(building) > property.getNumBuilding(building)) {
                AbstractPlayer propOwner = propertyOwnedBy(property);
                totalBuildingMap.put(building, totalBuildingMap.get(building) - 1);
                property.addBuilding(building);
                propOwner.makePayment(property.getBuildingPrice(building), this);
            }
        }
    }

    public void sellBackBuildings(Property property, BuildingType building){
        AbstractPlayer propOwner = propertyOwnedBy(property);
        totalBuildingMap.put(building, totalBuildingMap.get(building)+1);
        property.removeBuilding(building);
        this.makePayment(property.getBuildingPrice(building)/2, propOwner);
    }

    public void unbuildForUpgrade(Property property, BuildingType building){
        AbstractPlayer propOwner = propertyOwnedBy(property);
        totalBuildingMap.put(building, totalBuildingMap.get(building)+1);
        property.removeBuilding(building);
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

    public Set<Property> getUnOwnedProps(){
        return unOwnedProps;
    }

    public void setEvenBuildingRule(boolean bool){
        evenBuildingRule = bool;
    }

}
