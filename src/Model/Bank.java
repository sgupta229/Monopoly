package Model;
import java.util.List;

/***
 * Keeps track of its own funds and also the ownership of properties. Will eventually implement Transfer,
 * meaning it will have make and receive Payment methods. It will hold a map of all owned properties and who owns
 * them and a list of all unowned properties
 */
public interface Bank {

    /***
     * Returns the player that owns a specific property, or null if no one does
     * @param property
     * @return
     */
    public Player propertyOwnedBy(Property property);

    /***
     * Sells a specific property to a player
     * @param property
     * @param purchaser
     */
    public void sellProperty(Property property, Player purchaser);

    /***
     * Auctions off a property to the list of players at the auction
     * @param property
     * @param auctionGoers
     */
    public void auctionProperty(Property property, List<Player> auctionGoers);
}
