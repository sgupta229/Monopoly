package Controller;

import Model.AbstractSpace;

import java.security.InvalidParameterException;
import java.util.List;

/**
 * This class holds the game board, which is made up of spaces
 */
public class Board {
    private List<AbstractSpace> mySpaces;
    private int myBoardSize;

    public Board(List<AbstractSpace> boardSpaces, int numSpaces){
        mySpaces = boardSpaces;
        myBoardSize = numSpaces;
    }

    /**
     * Returns the specific space at the location specified
     * Used when a player rolls the dice and determins his/her new location on the board
     * @param location
     * @return AbstractSpace
     * @throws IndexOutOfBoundsException
     */
    public AbstractSpace getSpaceAt(int location) throws IndexOutOfBoundsException{
        try {
            for(AbstractSpace sp : mySpaces){
                if(sp.getMyLocation() == location){
                    return sp;
                }
            }
        }
        finally {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Given a AbstractSpace (such as Boarwalk), this returns the location of that space on the board
     * Used when a user draws a card that says "Move to this space"
     * @param spaceName
     * @return integer (or index) of location of the AbstractSpace parameter
     * @throws InvalidParameterException
     */
    public int getLocationOfSpace(String spaceName) throws InvalidParameterException{
        try{
            for(AbstractSpace sp : mySpaces){
                if (sp.getMyName().equalsIgnoreCase(spaceName)){
                    return sp.getMyLocation();
                }
            }
        }
        finally{
            throw new InvalidParameterException();
        }
    }
}