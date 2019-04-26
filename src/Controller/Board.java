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
     * @param location location on board
     * @return AbstractSpace
     * @throws GameBoardException if location is not on board
     */
    public AbstractSpace getSpaceAt(int location) throws GameBoardException{
        if(location > mySpaces.size()){
            throw new GameBoardException("Location not found on board: ", location);
        }
        else{
            for(AbstractSpace sp : mySpaces) {
                if (sp.getMyLocation() == location) {
                    return sp;
                }
            }
            throw new GameBoardException("Space not found on board at location: ", location);
        }
    }

    /**
     * Given a AbstractSpace (such as Boarwalk), this returns the location of that space on the board
     * Used when a user draws a card that says "Move to this space"
     * @param spaceName name of space to find on board
     * @return integer (or index) of location of the AbstractSpace parameter
     * @throws GameBoardException if name is not on board
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