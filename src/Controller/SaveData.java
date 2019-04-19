package Controller;

import Model.AbstractPlayer;
import Model.Bank;
import Model.actioncards.ActionDeck;
import Model.properties.Property;
import Model.spaces.AbstractSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Deprecated
public class SaveData implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private List<AbstractPlayer> players;
    private Bank bank;
    private Board board;
    private List<AbstractSpace> spaces;
    private List<Property> properties;
    private AbstractPlayer currPlayer;
    private List<Die> dice;
    private List<ActionDeck> decks;
    private HashMap<Integer, ArrayList<Integer>> diceHistory;
    private List<String> possibleTokens;
    private int numRollsInJail;

    public SaveData(List<AbstractPlayer> players, Bank bank, Board board, List<AbstractSpace> spaces,
                    List<Property> properties, AbstractPlayer currPlayer, List<Die> dice, List<ActionDeck> decks, HashMap<Integer,ArrayList<Integer>> diceHistory, List<String> possibleTokens, int numRollsInJail) {
        super();
        this.players = players;
        this.bank = bank;
        this.board = board;
        this.spaces = spaces;
        this.properties = properties;
        this.currPlayer = currPlayer;
        this.dice = dice;
        this.decks = decks;
        this.diceHistory = diceHistory;
        this.possibleTokens = possibleTokens;
        this.numRollsInJail = numRollsInJail;
    }

//    public SaveData(List<AbstractPlayer> players, Bank bank, Board board, List<AbstractSpace> spaces, List<Property> properties,
//                AbstractPlayer currPlayer, List<Die> dice, List<ActionDeck> decks, HashMap<Integer, ArrayList<Integer>> diceHistory, List<String> possibleTokens, int numRollsInJail) {
//    new SaveData(players,bank,board,spaces,properties,currPlayer,dice,decks,diceHistory,possibleTokens,numRollsInJail);
//    }


    public List<AbstractPlayer> getPlayers() {return players;}
    public Bank getBank() {return bank;}
    public Board getBoard() {return board;}
    public List<AbstractSpace> getSpaces() {return spaces;}
    public List<Property> getProperties() {return properties;}
    public AbstractPlayer getCurrPlayer() {return currPlayer;}
    public List<Die> getDice(){return dice;}
    public List<ActionDeck> getDecks() {return decks;}
    public HashMap<Integer, ArrayList<Integer>> getDiceHistory() {return diceHistory;}
    public List<String> getPossibleTokens() {return possibleTokens;}
    public int getNumRollsInJail() {return numRollsInJail;}
}