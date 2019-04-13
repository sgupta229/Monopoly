package Model;

import Model.properties.Property;

import Model.actioncards.AbstractActionCard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public abstract class AbstractPlayer implements Transfer {
    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    private String name;
    private String tokenImage;

    private double funds;
    private ArrayList<Property> properties;
    private ArrayList<AbstractActionCard> actionCards;
//    private Token token;
    private int currentLocation;
    private boolean inJail;

    public AbstractPlayer() {
        this.inJail = false;
        properties = new ArrayList<>();
        actionCards = new ArrayList<>();
    }

    public AbstractPlayer(String name) {
        this.name = name;
//        this.tokenImage = tokenImage;
        this.inJail = false;
        properties = new ArrayList<>();
        actionCards = new ArrayList<>();
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

//    public Token getToken() {
//        return token;
//    }

    public int getCurrentLocation(){
        return currentLocation;
    }
//    public void setCurrentLocation(int newLocation) {
//        currentLocation = newLocation;
//    }
    public int move(int moveSpaces, int boardSize) {
        int oldLocation = currentLocation;
        int newLocation = currentLocation + moveSpaces;
        if(newLocation > boardSize - 1) {
            moveTo(newLocation - boardSize,boardSize);
        }
        else {
            moveTo(newLocation, boardSize);
        }
        return currentLocation;
    }
    public int moveTo(int newLocation, int boardSize) {
        if(newLocation > boardSize - 1) {
            throw new IllegalArgumentException("This is an invalid location");
        }
        myPCS.firePropertyChange("currentLocation",currentLocation,newLocation);
        currentLocation = newLocation;
        return currentLocation;
    }

    public void proposeTrade(AbstractPlayer other) {

    }

    public void setJail(boolean set) {
        inJail = set;
    }

    public void setFunds(double newFunds) {
        myPCS.firePropertyChange("funds",this.funds,newFunds);
        this.funds = newFunds;
    }

//    public void setToken(Token token) {
//        this.token = token;
//    }

    public boolean isInJail() {
        return inJail;
    }

    public String getName() {
        return this.name;
    }

    public String getTokenImage() {
        return this.tokenImage;
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        myPCS.addPropertyChangeListener(propertyName,listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        myPCS.removePropertyChangeListener(listener);
    }
}
