package monopoly_team04.Model.Controller;

/***
 * This class handles all movements for a player. Holds the players current location on the board
 */
public interface Token {

    /***
     * moves the player the specifc amount ahead on the board
     * @param amount
     * @return new location int
     */
    public int move(int amount);

    /***
     * gets the players current location in terms of its index on the board
     * @return
     */
    public int getCurrentLocation();

    /***
     * moves the player directly to a specific index on the board
     * @param index the location being moved to
     */
    public void moveTo(int index);
}
