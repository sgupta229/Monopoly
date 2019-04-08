package Model;


import Controller.AbstractGame;


public class GoToSpace extends AbstractSpace {

    AbstractSpace spaceToMoveTo;
    int spacesLocation;

    public GoToSpace(int locationIndex, String spaceName, AbstractSpace jumpToSpace){
        super(locationIndex, spaceName);
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

    public void doAction(AbstractGame game){
        game.getCurrPlayer().getToken().moveTo(spacesLocation);
        if(spaceToMoveTo.getMyName().equals("Jail")){
            game.getCurrPlayer().setJail(true);
        }
    }
}
