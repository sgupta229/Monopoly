package Model.spaces;


import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.properties.Property;

import java.util.ArrayList;
import java.util.List;


public class GoToSpace extends AbstractSpace {

    private String spaceToMoveTo;
    private int spacesLocation;
    private static final String myPopString = "Corner";
    private List<String> jailTypes = List.of("JAIL", "SHARK_DIVING");

    public GoToSpace(int locationIndex, String spaceName, String spaceGroup,
                     String jumpToSpace, List<Double> taxNums, Property myProp){
        super(locationIndex, spaceName, spaceGroup, jumpToSpace, taxNums, myProp);
        spaceToMoveTo = jumpToSpace;
        setPopString(myPopString);
    }

    public GoToSpace(int locationIndex, String spaceName, String jumpToSpace){
        super(locationIndex, spaceName);
        spaceToMoveTo = jumpToSpace;
        //spacesLocation = spaceToMoveTo.getMyLocation();
    }

    public List getInfo(){
        return new ArrayList();
    }


    /***
     * This method performs the specific action that a type of space requires.
     * It takes game in as a parameter so that it can do things such as
     * figure out the current player, get the bank and perform bank actions,
     * get a specific deck and draw a card, and more.
     * @param game the active Game driver class for this game
     */

    public void doAction(AbstractGame game, int userChoice){
        AbstractPlayer currPlayer = game.getCurrPlayer();
        spacesLocation = game.getBoard().getLocationOfSpace(spaceToMoveTo);
        int oldLocation = currPlayer.getCurrentLocation();
        //this.removeOccupant(currPlayer);
        //currPlayer.moveTo(spacesLocation, game.getBoardSize());
        //game.getBoard().getSpaceAt(spacesLocation).addOccupant(currPlayer);
        game.movePlayer(oldLocation, spacesLocation);
        System.out.println("moved to jail");
        for(String jailName:jailTypes){
            if(spaceToMoveTo.equalsIgnoreCase(jailName)){
                game.getCurrPlayer().setJail(true);
            }
        }

    }

    public String getPopString(AbstractGame game){
        return myPopString;
    }
}
