package Model;

import Controller.AbstractGame;

public class GoSpace extends AbstractSpace{

    public GoSpace(int locationIndex, String spaceName){
        super(locationIndex, spaceName);
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game){
    }
}
