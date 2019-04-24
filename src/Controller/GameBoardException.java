package Controller;

public class GameBoardException extends RuntimeException{
    public GameBoardException (String message){
        super (message);
    }
    public GameBoardException(String message, Object values){
        super(String.format(message, values));
    }

}
