package Controller;

import Model.AbstractPlayer;
import Model.Bank;
import Model.actioncards.AbstractActionCard;
import Model.actioncards.ActionDeck;
import Model.properties.Property;
import Model.spaces.AbstractSpace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<Integer, ArrayList<Integer>> diceHistory = new HashMap<Integer, ArrayList<Integer>>();
    private List<String> possibleTokens;
    private int numRollsInJail = 0;
}
