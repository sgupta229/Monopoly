package Model;

import Controller.Game;


public class GoToSpace extends Space{

    Space spaceToMoveTo;
    int spacesLocation;

    public GoToSpace(Space jumpToSpace){
        spaceToMoveTo = jumpToSpace;
        spacesLocation = spaceToMoveTo.getMyLocation();
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */
    public void doAction(Game game){
        game.getCurrentPlayer().getMyToken().moveTo(spacesLocation);
    }
}
