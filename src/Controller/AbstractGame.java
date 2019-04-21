package Controller;

import Model.*;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.properties.BuildingType;
import Model.properties.Property;
import Model.spaces.AbstractSpace;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.*;

public abstract class AbstractGame implements Serializable {

    private String name;

    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);
    private int boardSize = 0;

    //RULES
    private double startFunds;
    private double jailBail;
    private double passGo;
    private AbstractActionCard currentActionCard;

    private List<AbstractPlayer> players;
    private Bank bank;
    private Board board;
    private List<AbstractSpace> spaces;
    private List<Property> properties;
    private AbstractPlayer currPlayer;
    private List<Die> dice;
    private List<ActionDeck> decks;
    private HashMap<Integer, ArrayList<Integer>> diceHistory = new HashMap<>();
    private List<String> possibleTokens;
    private int numRollsInJail = 0;

    private int rollsInJailRule;
    private boolean evenBuildingRule;
    private boolean freeParkingRule;
    
    public AbstractGame(String filename) {
        parseXMLFile(filename);
        for(int i = 0; i < dice.size(); i++) {
            diceHistory.put(i, new ArrayList<>());
        }
    }

    private void parseXMLFile(String filename) {
        ConfigReader configReader = new ConfigReader(filename);
        try {
            decks = configReader.parseActionDecks();
            List<AbstractActionCard> allCards = configReader.parseActionCards();
            for(ActionDeck d : decks) {
                d.fillLiveDeck(allCards);
            }
            possibleTokens = configReader.parseTokens();
            dice = configReader.parseDice();
            List<Double> funds = configReader.parseBank();

            boardSize = configReader.parseBoard();
            List<List> spaceProps= configReader.parseSpaces();
            spaces = spaceProps.get(0);
            properties = spaceProps.get(1);
            List<Map<BuildingType, Integer>> buildingInfo = configReader.getBuildingProperties();
            //Map<BuildingType, Integer> buildingTotalAmount = buildingInfo.get(0);
            //Map<BuildingType, Integer> buildingMaxAmount = buildingInfo.get(1);
            bank = new Bank(funds, properties, buildingInfo);
            board = new Board(boardSize, spaceProps.get(0));

            startFunds = configReader.getRuleDouble("StartFunds");
            jailBail = configReader.getRuleDouble("JailBail");
            passGo = configReader.getRuleDouble("PassGo");
            evenBuildingRule = configReader.getRuleBool("EvenBuilding");
            freeParkingRule = configReader.getRuleBool("FreeParking");
            rollsInJailRule = (int) configReader.getRuleDouble("RollsInJail");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPlayers(List<AbstractPlayer> p){
        if (p.size() <=0 ) {
            //TODO: throw some "can't initialize players w empty list" exception
        }
        players = p;
        setCurrPlayer(0);
        for (AbstractPlayer pl : players)
            this.addPlayer(pl);
    }

    public abstract boolean checkGameOver();

    public abstract AbstractPlayer getWinner();

    public AbstractPlayer getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(int index){
        myPCS.firePropertyChange("currPlayer",currPlayer,players.get(index));
        currPlayer = players.get(index);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        myPCS.addPropertyChangeListener(propertyName,listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        myPCS.removePropertyChangeListener(listener);
    }

    public AbstractPlayer getLeftPlayer() {
        int playerIndex = players.indexOf(currPlayer);
        int leftIndex = playerIndex + 1;
        if(leftIndex > players.size() - 1) {
            leftIndex = 0;
        }
        return players.get(leftIndex);
    }

    public AbstractPlayer getRightPlayer() {
        int playerIndex = players.indexOf(currPlayer);
        int rightIndex = playerIndex - 1;
        if(rightIndex < 0) {
            rightIndex = players.size() - 1;
        }
        return players.get(rightIndex);
    }

    public List<AbstractPlayer> getPlayers() {
        return players;
    }

    public int rollDice() {
        int value = 0;
        for(int i = 0; i < dice.size(); i++) {
            int roll = dice.get(i).rollDie();
            value += roll;
            diceHistory.get(i).add(roll);
        }
        return value;
    }

    public Bank getBank() {
        return bank;
    }

    public Board getBoard() {
        return board;
    }

    public void startNextTurn() {
        int index = players.indexOf(this.getLeftPlayer());
        setCurrPlayer(index);
    }

    //checks 3 matching all dice in a row
    public boolean checkThreeDoublesInRow() {
        ArrayList<Integer> firstDie = diceHistory.get(0);
        List<Integer> check = firstDie.subList(firstDie.size() - 3, firstDie.size());
        for(Integer key : diceHistory.keySet()) {
            ArrayList<Integer> otherDie = diceHistory.get(key);
            List<Integer> other = otherDie.subList(otherDie.size() - 3, otherDie.size());
            if(!check.equals(other)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDoubles() {
        ArrayList<Integer> firstDie = diceHistory.get(0);
        int check = firstDie.get(firstDie.size() - 1);
        for(Integer key : diceHistory.keySet()) {
            ArrayList<Integer> otherDie = diceHistory.get(key);
            int other = otherDie.get(firstDie.size() - 1);
            if(!(check == other)) {
                return false;
            }
        }
        return true;
    }

    public List<ActionDeck> getMyActionDecks(){return decks;}

    //instantiate a player and add it to the list
    public void addPlayer(AbstractPlayer player) {
        player.setFunds(startFunds);
        player.moveTo(0);
    }

    public List<String> getPossibleTokens() {
        return possibleTokens;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public AbstractActionCard getCurrentActionCard() {
        return currentActionCard;
    }

    public void setCurrentActionCard(AbstractActionCard c) {
        this.currentActionCard = c;
    }

    public int getNewIndex(int oldIndex, int roll) {
        int boardSize = board.getSize();
        int newIndex = oldIndex + roll;
        if(newIndex > board.getSize() - 1) {
            newIndex = newIndex - boardSize;
        }
        return newIndex;
    }

    public void movePlayer(int oldIndex, int newIndex) {
        if(newIndex > board.getSize() - 1) {
            throw new IllegalArgumentException("The new index is outside of the board size");
        }
        if(oldIndex != currPlayer.getCurrentLocation()) {
            throw new IllegalArgumentException("The old index provided is not correct");
        }
        currPlayer.moveTo(newIndex);
        AbstractSpace oldSpace = getBoard().getSpaceAt(oldIndex);
        oldSpace.removeOccupant(getCurrPlayer());
        AbstractSpace newSpace = getBoard().getSpaceAt(newIndex);
        newSpace.addOccupant(getCurrPlayer());
    }

    @Deprecated
    public void callAction(int userChoice) {
        int currentLocation = currPlayer.getCurrentLocation();
        AbstractSpace currSpace = getBoard().getSpaceAt(currentLocation);
        currSpace.doAction(this, userChoice);
    }

    public HashMap<Integer, ArrayList<Integer>> getDiceHistory() {
        return diceHistory;
    }

    public void startAuction() {

    }

    public int getLastDiceRoll() {
        int value = 0;
        for(int i = 0; i < dice.size(); i++) {
            ArrayList<Integer> rollList = diceHistory.get(i);
            if(rollList.size() == 0) {
                throw new IllegalArgumentException("The dice has not been rolled");
            }
            else {
                value += rollList.get(rollList.size() - 1);
            }
        }
        return value;
    }

    //Getters and setters for rules to be changed by user
    public boolean getEvenBuildingRule(){
        return evenBuildingRule;
    }
    public void setEvenBuildingRule(boolean bool){
        this.evenBuildingRule = bool;
    }
    public boolean getFreeParkingRule(){
        return freeParkingRule;
    }
    public void setFreeParkingRule(boolean bool){
        this.freeParkingRule = bool;
    }
    public int getRollsInJailRule() {
        return rollsInJailRule;
    }
    public void setRollsInJailRule(int rollsInJailRule) {
        this.rollsInJailRule = rollsInJailRule;
    }
    public double getStartFunds() {
        return startFunds;
    }
    public void setStartFunds(double startFunds) {
        this.startFunds = startFunds;
    }

    public double getJailBail() {
        return jailBail;
    }

    public void setJailBail(double jailBail) {
        this.jailBail = jailBail;
    }

    public double getPassGo() {
        return passGo;
    }

    public void setPassGo(double passGo) {
        this.passGo = passGo;
    }

    public void setName(String name) {
        this.name = name;
    }


}
