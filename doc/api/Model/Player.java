package Model;

/***
 * Represents each participant in the game. Will hold a total amount of funds, an array of assets held,
 * an array of actioncards held, and the players Token
 */
public interface Player implements Transfer{

    /***
     * This method allows for players to transfer
     */
    public void makeTrade(Player bargainer);
}
