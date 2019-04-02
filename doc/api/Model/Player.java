package Model;

/***
 * Represents each participant in the game. Will hold a total amount of funds, an array of assets held,
 * an array of actioncards held, and the players Token. This will implement Transfer, and thus it will
 * have make and recieve payment methods
 */
public interface Player {

    /***
     * Allows a player to propose a trade to another player
     * @param otherParty the person being offered to
     */
    public void proposeTrade(Player otherParty);

    /***
     * Changes the member variable that states whether a player is in jail or not
     * @param inJail the boolean that it will be changed to
     */
    public void setJail(boolean inJail);

    /***
     * returns the token associated with this player
     * @return
     */
    public Token getMyToken();

    /***
     * gets an immutable list of this players assets, useful for making trades
     * @return
     */
    public List<Property> getAssets();

    /***
     * checks if the player has a monopoly of the property given
     * @param property
     * @return whether there is a monopoly or not
     */
    public boolean checkMonopoly(Property property);
}
