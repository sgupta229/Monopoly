package monopoly_team04.Model.Controller;

<<<<<<< HEAD
<<<<<<< HEAD
import monopoly_team04.Model.Bank;
import monopoly_team04.Model.Deck;
import monopoly_team04.Model.DeckType;
import monopoly_team04.Model.Player;
=======
import Model.Bank;
import Model.DeckType;
import Model.Player;
>>>>>>> 82633877f6cc8985eab3c12b8c74116b0fd35b65
=======
import Model.Bank;
import Model.DeckType;
import Model.Player;
>>>>>>> 01cbc3aa8adc2d94a0e2c353efe82add9cc6e4ee

import java.util.List;

/**
 * Overruling class that holds all the information necessary for gameplay
 * Helps in communication between various other classes by passing information back and forth as needed
 */
public interface Game {
    /**
     * Gets the play whos turn is up
     * Useful when a card is drawn and the card must be applied to a certain player
     * @return
     */
    public Player getCurrentPlayer();

    /**
     * Starts the turn of the current player
     * Will prompt front end to prompt player to either roll die or perform other action
     * @param currentPlayer
     */
    public void startTurn(Player currentPlayer);

    /**
     * Returns the bank and therefore all information held by bank
     * Info includes: PropertiesUnowned, PropertiesOwned, Funds, methods
     * @return
     */
    public Bank getBank();

    /**
     * Returns integer rolled by dice (sent to player.token to move token on board)
     * @return
     */
    public int getDiceRoll();

    /**
     * Returns a list of playrs in their order
     * Useful for cases in which player must pay other player "to his/her right" (next in order)
     * @return
     */
    public List<Player> getPlayersOrder();

    /**
     * Returns game board and can get spaces via methods in Board class
     * @return
     */
    public Board getBoard();

    /**
     * Returns the deck of action cards specified by space
     * @param type
     * @return
     */
    public Deck getActionDeck(DeckType type);
}