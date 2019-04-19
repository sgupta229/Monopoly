package Model;

import Controller.Token;
import Model.properties.Property;
import Model.actioncards.AbstractActionCard;
import Model.properties.BuildingType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPlayer implements Transfer, Serializable {
    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    private String name;
    private int numRollsInJail = 0;
    private boolean inJail;
    private double funds;
    private Token token;
    private String tokenImage;
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
        System.out.println(this.getName() + "'s funds updated. new funds: " + funds);
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

    public void startAuction() {

    }

    public int getPropertiesOfType(String type) {
        int count = 0;
        String checkType = type.toLowerCase();
        for(Property p : properties) {
            System.out.println(p.getGroup());
            if(p.getGroup().toLowerCase().equals(checkType)) {
                count++;
            }
        }
        return count;
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



    @Deprecated
    public void setToken(Token token) {
        this.token = token;
    }
    @Deprecated
    public void setCurrentLocation(int newLocation) {
        currentLocation = newLocation;
    }
    @Deprecated
    public Token getToken() {
        return token;
    }
}
