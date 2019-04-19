package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.spaces.SpaceGroup;

import java.util.List;

public class MoveToNearestAC extends AbstractActionCard {
    private String myTargetGroup;
    private double passGo;

    public MoveToNearestAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
        myTargetGroup = extraString;
        passGo = extraDoubles.get(0);
    };

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        SpaceGroup sg = SpaceGroup.valueOf(myTargetGroup);
        int prevLocation = curr.getCurrentLocation();
        int boardSize = game.getBoardSize();
        for(int i=prevLocation; i<boardSize+prevLocation; i++){
            if(i>=boardSize){
                i=i-boardSize;
            }
            if(game.getBoard().getSpaceAt(i).getMyGroup() == sg){
                game.movePlayer(prevLocation, i);
                if(prevLocation > i){
                    game.getBank().makePayment(passGo, curr);
                }
            }
            break;
        }
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
