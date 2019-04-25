package Model.actioncards;


import Controller.AbstractGame;
import Model.AbstractPlayer;

import java.util.List;

public class MoveToAC extends AbstractActionCard {
    private String myTargetSpace;
    private Double passGo;

    public MoveToAC(DeckType deckType, String message, Boolean holdable, String targetSpace) {
        super(deckType, message, holdable);
        myTargetSpace = targetSpace;
    }

    @Deprecated
    public MoveToAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
        myTargetSpace = extraString;
        passGo = extraDoubles.get(0);
    }

    public MoveToAC(DeckType deckType, String message, Boolean holdable, List<String> extraStrings, List<Double> extraDoubles){
        super(deckType, message, holdable);
        myTargetSpace = extraStrings.get(0);
        passGo = extraDoubles.get(0);
    }

    /**
     * make payment if pass go, move player to specified space
     * @param game
     */
    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int newLocation = game.getBoard().getLocationOfSpace(myTargetSpace);
        //This is handled in game.movePlayer();
        if (myTargetSpace.equalsIgnoreCase("GO") || (prevLocation > newLocation)) {
            game.getBank().makePayment(game.getBank(), passGo, curr);
        }

        game.movePlayer(prevLocation, newLocation);
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
