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
    private double snakeEyes;

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
    private double bankFunds;
    private int rollsInJailRule;
    private boolean evenBuildingRule;
    private boolean freeParkingRule;
    private Transfer freeParking = new FreeParkingFunds();
    private int lastDiceRoll = 0;
    
    public AbstractGame(String filename) throws XmlReaderException {
        parseXMLFile(filename);
        for(int i = 0; i < dice.size(); i++) {
            diceHistory.put(i, new ArrayList<>());
        }
    }

    private void parseXMLFile(String filename) throws XmlReaderException {
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
            bankFunds = funds.get(0);
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
            snakeEyes = configReader.getRuleDouble("SnakeEyes");
        }
        catch (XmlReaderException e) {
            throw new XmlReaderException(e.getMessage() + ": Check data file " + filename);
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

    public List<Integer> rollDice() {
        int val = 0;
        List<Integer> rolls = new ArrayList<>();
        for(int i = 0; i < dice.size(); i++) {
            int roll = dice.get(i).rollDie();
            rolls.add(roll);
            val += roll;
            diceHistory.get(i).add(roll);
        }
        lastDiceRoll = val;

        return rolls;
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
        //clearDiceHistory();
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

    public boolean checkNeedToPayBail() {
        if(!(checkDoubles())) {
            if(currPlayer.getNumRollsInJail() == getRollsInJailRule()) {
                return true;
            }
        }
        return false;
    }

    public abstract boolean checkDoublesForJail();

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
        checkPassGo(oldIndex, newIndex);
        currPlayer.moveTo(newIndex);
        AbstractSpace oldSpace = getBoard().getSpaceAt(oldIndex);
        oldSpace.removeOccupant(getCurrPlayer());
        AbstractSpace newSpace = getBoard().getSpaceAt(newIndex);
        newSpace.addOccupant(getCurrPlayer());
    }

    public void checkPassGo(int oldIndex, int newIndex) {
        if(newIndex < oldIndex) {
            bank.makePayment(passGo, currPlayer);
        }
    }

    public void checkSnakeEyes(List<Integer> diceRoll) {
        for(Integer i : diceRoll) {
            if(i != 1) {
                return;
            }
        }
        bank.makePayment(snakeEyes, currPlayer);
    }

    public void handleMoveInJail(int oldIndex, int newIndex) {
        if(!currPlayer.isInJail()) {
            this.movePlayer(oldIndex, newIndex);
        }

        else {
            currPlayer.incrementNumRollsinJail();
            if(checkDoubles()) {
                this.movePlayer(oldIndex, newIndex);
            }
            else if(currPlayer.getNumRollsInJail() == getRollsInJailRule()){
                this.movePlayer(oldIndex, newIndex);
                currPlayer.resetNumRollsInJail();
                currPlayer.setJail(false);
            }
        }
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

    public void handleAuction(AbstractPlayer p, int bid, int propLocation) {
        Property prop = board.getSpaceAt(propLocation).getMyProp();
        bank.setPropertyOwner(prop, p);
        p.makePayment(bid, bank);
        p.addProperty(prop);
        System.out.println(bank.propertyOwnedBy(prop).getName());

/*        AbstractPlayer maxPlayer = null;
        double maxBid = 0;
        for(AbstractPlayer p : bidMap.keySet()){
            if(bidMap.get(p) > maxBid){
                maxBid = bidMap.get(p);
                maxPlayer = p;
            }
        }
        if(maxBid > 0 && maxPlayer!=null){
            bank.setPropertyOwner(prop, maxPlayer);
            maxPlayer.makePayment(maxBid, bank);
        }*/
    }

    public void clearDiceHistory() {
        diceHistory = new HashMap<>();
        for(int i = 0; i < dice.size(); i++) {
            diceHistory.put(i, new ArrayList<>());
        }
    }

    public int getLastDiceRoll() {
        return lastDiceRoll;
    }

    //Getters and setters for rules to be changed by user

    public boolean getEvenBuildingRule(){
        return evenBuildingRule;
    }

    public void setEvenBuildingRule(boolean bool){
        bank.setEvenBuildingRule(bool);
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

    public Transfer getFreeParking(){
        return freeParking;
    }

    public double getBankFunds(){
        return bankFunds;
    }

    public void setBankFunds(double amount){
        bank.setFunds(amount);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<AbstractSpace> getSpaces() {
        return spaces;
    }

    public void forfeitHandler(AbstractPlayer playerOut){
        this.players.remove(playerOut);
        List<Property> propSet = playerOut.getProperties();
        Set<BuildingType> bTypes = bank.getTotalBuildingMap().keySet();
        for(Property p : propSet){
            for(BuildingType bt : bTypes){
                bank.setTotalBuildingMap(bt, p.getNumBuilding(bt));
                //p.removeBuilding(bt, p.getNumBuilding(bt));
            }
            bank.sellBackProperty(p, this);
        }
        playerOut.makePayment(playerOut.getFunds(), bank);
    }

    public void setSnakeEyes(double d) {
        snakeEyes = d;
    }

    public double getSnakeEyes() {
        return snakeEyes;
    }
}
