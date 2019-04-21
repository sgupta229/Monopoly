package Model;

import Model.properties.Property;
import Model.actioncards.AbstractActionCard;
import Model.properties.BuildingType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.Serializable;
import java.util.*;

public abstract class AbstractPlayer implements Transfer, Serializable {
    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    private String name;
    private String tokenImage;
    private int numRollsInJail = 0;
    private boolean inJail;
    private double funds;
    private ObservableList<Property> properties;
    private List<AbstractActionCard> actionCards;
    private int currentLocation;


    public AbstractPlayer() {
        this.inJail = false;
        properties = FXCollections.observableArrayList();
        actionCards = new ArrayList<>();
    }

    public AbstractPlayer(String name, String image) {
        this();
        this.name = name;
        this.setImage(image);
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
    }


    @Override
    public void makePayment(double amount, Transfer receiver) {
        if(this.funds < amount) {
            throw new IllegalArgumentException("Not enough money to pay");
        }
        setFunds(this.funds - amount);
        receiver.receivePayment(amount);
    }

    @Override
    public void receivePayment(double amount) {
        setFunds(this.funds + amount);
    }

    public boolean checkMonopoly(Property property) {
        int count = 0;
        String group = property.getGroup().toLowerCase();
        int groupSize = property.getMyGroupSize();
        for(Property p : properties) {
            if(p.getGroup().toLowerCase().equals(group)) {
                count++;
            }
        }
        if(count == groupSize) {
            return true;
        }
        return false;
    }

    public double getFunds() {
        return funds;
    }

    public int getCurrentLocation(){
        return currentLocation;
    }

    public int moveTo(int newLocation) {
        int oldLocation = currentLocation;
        currentLocation = newLocation;
        myPCS.firePropertyChange("currentLocation",oldLocation,currentLocation);
        return currentLocation;
    }

    public void executeTrade(AbstractPlayer other, List<Property> currProp, List<Property> otherProp) {

    }

    public void payBail(Bank b) {
        this.makePayment(50, b);
        this.inJail = false;
    }

    public void setJail(boolean set) {
        inJail = set;
    }

    public void setFunds(double newFunds) {
        double oldFunds = this.funds;
        this.funds = newFunds;
        myPCS.firePropertyChange("funds",oldFunds,this.funds);
    }

    public void addFunds(double addAmount) {
        setFunds(this.funds + addAmount);
    }

    public boolean isInJail() {
        return inJail;
    }

    public String getName() {
        return this.name;
    }

    public void addActionCard(AbstractActionCard c) {
        actionCards.add(c);
    }

    public Map<BuildingType, Integer> getNumBuildings() {
        Map<BuildingType, Integer> buildingInventory = new HashMap<>();
        for(Property p : getProperties()) {
            Map<BuildingType, Integer> mapCount= p.getBuildingMap();
            for(BuildingType b : mapCount.keySet()) {
                if(!buildingInventory.containsKey(b)) {
                    buildingInventory.put(b, mapCount.get(b));
                }
                else {
                    buildingInventory.put(b, buildingInventory.get(b) + mapCount.get(b));
                }
            }
        }
        return buildingInventory;
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        myPCS.addPropertyChangeListener(propertyName,listener);
    }



    public List<Property> getPropertiesOfType(String type) {
        String checkType = type.toLowerCase();
        List<Property> propsList = new ArrayList<>();
        for(Property p : properties) {
            //System.out.println(p.getGroup());
            if(p.getGroup().toLowerCase().equals(checkType)) {
                propsList.add(p);
            }
        }
        return propsList;
    }





    public ObservableList<Property> getProperties() {
        return properties;
    }

    public void incrementNumRollsinJail() {
        numRollsInJail++;
    }

    public void resetNumRollsInJail() {
        numRollsInJail = 0;
    }

    public int getNumRollsInJail() {
        return numRollsInJail;
    }

    public List<AbstractActionCard> getActionsCards() {
        return actionCards;
    }

    public List<AbstractActionCard> getActionCards(){return actionCards;}

    public void setImage(String img) {
        tokenImage = img;
    }
    public String getImage() {
        return this.tokenImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    //TESTING ONLY
    public void setCurrentLocation(int newLocation) {
        currentLocation = newLocation;
    }
}
