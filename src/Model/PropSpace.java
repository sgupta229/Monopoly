package Model;

public class PropSpace extends AbstractSpace {


    Property myProperty;

    public PropSpace(int locationIndex, String spaceName, Property thisProperty){
        super(locationIndex, spaceName);
        myProperty = thisProperty;

    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(Game game){
        if(game.getBank().propertyOwnedBy(myProperty)==null){
            //prompt front end button to purchase property, handler does the rest
        }
    }


}
