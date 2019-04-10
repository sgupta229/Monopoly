package Model;


import Controller.AbstractGame;
import Controller.Board;


public class GoToSpace extends AbstractSpace {

    String spaceToMoveTo;
    int spacesLocation;

    public GoToSpace(int locationIndex, String spaceName, String jumpToSpace){
        super(locationIndex, spaceName);
        spaceToMoveTo = jumpToSpace;
        //spacesLocation = spaceToMoveTo.getMyLocation();
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game){
        AbstractPlayer currPlayer = game.getCurrPlayer();
        spacesLocation = game.getBoard().getLocationOfSpace(spaceToMoveTo);
        this.removeOccupant(currPlayer);
        currPlayer.moveTo(spacesLocation);
        game.getBoard().getSpaceAt(spacesLocation).addOccupant(currPlayer);
        if(spaceToMoveTo.equalsIgnoreCase("JAIL")){
            game.getCurrPlayer().setJail(true);
        }
        game.endTurn();
    }
}
