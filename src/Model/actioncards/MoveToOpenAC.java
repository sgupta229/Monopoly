package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.spaces.AbstractSpace;

import java.util.List;

public class MoveToOpenAC extends AbstractActionCard {
    private List<String> myTargetSpaces;
    private Double passGo;

    public MoveToOpenAC(DeckType deckType, String message, Boolean holdable, List<String> extraStrings, List<Double> extraDoubles){
        super(deckType, message, holdable);
        myTargetSpaces = extraStrings;
        passGo = extraDoubles.get(0);
    };

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        for(int i=0; i<game.getBoardSize(); i++){
            AbstractSpace tempSpace = game.getBoard().getSpaceAt(i);
            if(tempSpace.g)
        }
    }
}
