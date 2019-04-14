package Model;

import Model.actioncards.AbstractActionCard;
import Model.properties.BuildingType;
import Model.properties.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPlayer implements Transfer {
    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    private String name;

    private double funds;
    private Map<String, ObservableList<Property>> properties;
    private ArrayList<AbstractActionCard> actionCards;
//    private Token token;
    private int currentLocation;

    private boolean inJail;

    public AbstractPlayer() {
        this.inJail = false;
        properties = new HashMap<>();
        actionCards = new ArrayList<>();
    }

    public AbstractPlayer(String name) {
        this.name = name;
        this.inJail = false;
        properties = new HashMap<>();
        actionCards = new ArrayList<>();
    }

    public void addProperty(Property property) {
        String group = property.getGroup();
        if(!properties.containsKey(group)) {
            properties.get(group).add(property);
        }
        else {
            ObservableList<Property> list = FXCollections.observableArrayList();
            list.add(property);
            properties.put(group, list);
        }
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

    public boolean checkMonopoly() {
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

    public void proposeTrade(AbstractPlayer other) {

    }

    public void build(BuildingType type) {


    }

    public abstract void doSpecialMove();

    public void setJail(boolean set) {
        inJail = set;
    }

    public void setFunds(double newFunds) {
        myPCS.firePropertyChange("funds",this.funds,newFunds);
        this.funds = newFunds;
        System.out.println(this.getName() + "'s funds updated. new funds: " + funds);
    }

    public void addFunds(double funds) {
        this.funds += funds;
    }

    public boolean isInJail() {
        return inJail;
    }

    public String getName() {
        return this.name;
    }

    public void setCurrentLocation(int newLocation) {
        currentLocation = newLocation;
    }

    public void addActionCard(AbstractActionCard c) {
        actionCards.add(c);
    }

    public abstract Map<BuildingType, Integer> getNumBuildings();

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        myPCS.addPropertyChangeListener(propertyName,listener);
    }

}
