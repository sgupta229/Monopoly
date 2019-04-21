package Controller;

import Model.spaces.AbstractSpace;

import java.io.Serializable;
import java.util.List;

/**
 * This class holds the game board, which is made up of spaces
 */
public class Board implements Serializable {
    private List<AbstractSpace> mySpaces;
    private int myBoardSize;

    public Board(int numSpaces, List<AbstractSpace> boardSpaces){
        mySpaces = boardSpaces;
        myBoardSize = numSpaces;
    }

    /**
     * Returns the specific space at the location specified
     * Used when a player rolls the dice and determins his/her new location on the board
     * @param location
     * @return AbstractSpace
     * @throws GameBoardException
     */
    public AbstractSpace getSpaceAt(int location) throws GameBoardException{
        if(location > mySpaces.size()){
            throw new GameBoardException(location);
        }
        else{
            for(AbstractSpace sp : mySpaces) {
                if (sp.getMyLocation() == location) {
                    return sp;
                }
            }
            throw new GameBoardException(location);
        }
    }

    /**
     * Given a AbstractSpace (such as Boarwalk), this returns the location of that space on the board
     * Used when a user draws a card that says "Move to this space"
     * @param spaceName
     * @return integer (or index) of location of the AbstractSpace parameter
     * @throws GameBoardException
     */
    public int getLocationOfSpace(String spaceName) throws GameBoardException{
        for(AbstractSpace sp : mySpaces){
            if (sp.getMyName().equalsIgnoreCase(spaceName)){
                return sp.getMyLocation();
            }
        }
        throw new GameBoardException(spaceName);
    }

    public int getSize() {
        return this.myBoardSize;
    }
}