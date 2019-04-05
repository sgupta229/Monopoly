package Model;
import Controller.Game;

import java.util.*;

/***
 * This will be the interface that represents all the different kind of spaces
 * that can be on the board (a property space, jail space, free parking, chance space, etc)
 * It will hold a list of which players (if any) are on itself
 */
public interface Space {

    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(Game game);

    /***
     * Getter method that gets all the players on the given space
     * @return the list of Occupants
     */
    public List<Player> getOccupants();

    /***
     * adds a player to the list of players on the space
     * @param newOccupant the player that is now on the spot
     */
    public void addOccupant(Player newOccupant);
}
