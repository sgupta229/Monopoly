package Controller;

public class GameBoardException extends IndexOutOfBoundsException{
    public GameBoardException (int index){
        super (index);
    }
    public GameBoardException(String spaceName){
        super(spaceName);
    }
}
