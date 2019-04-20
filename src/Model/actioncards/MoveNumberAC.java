package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;

import java.util.List;

public class MoveNumberAC extends AbstractActionCard {
    private int myNumberSpaces;
    private Double passGo;

    public MoveNumberAC (DeckType deckType, String message, Boolean holdable, List<String> extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
        myNumberSpaces = Integer.parseInt(extraString.get(0));
        passGo = extraDoubles.get(0);
    };

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        ActionDeck d = this.getMyDeck();
        int prevLocation = curr.getCurrentLocation();
        int newLocation = getIndexOnBoard(prevLocation, myNumberSpaces, game.getBoardSize());
        game.movePlayer(prevLocation, newLocation);
        d.discardCard(this);
    }

    private int getIndexOnBoard(int start, int change, int boardSize){
        int newDex = start + change;
        if(newDex >= boardSize){
            return newDex - boardSize;
        }
        else if(newDex < 0){
            return boardSize + newDex;
        }
        return newDex;
    }
}
