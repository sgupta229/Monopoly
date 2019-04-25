package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;

import java.util.List;

public class GoToJailAC extends AbstractActionCard {
    private String targetSpace;

    public GoToJailAC(DeckType deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Deprecated
    public GoToJailAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){
        super(deckType, message, holdable);
    }

    public GoToJailAC(DeckType deckType, String message, Boolean holdable, List<String> extraStrings, List<Double> extraDoubles){
        super(deckType, message, holdable);
        targetSpace = extraStrings.get(0);
    }

    /**
     * Move player to jail space and set player's "InJail" variable to true
     * @param game takes in game to find board and player
     */
    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLoc = curr.getCurrentLocation();
        int newLoc = game.getBoard().getLocationOfSpace(targetSpace);
        curr.setJail(true);

        game.movePlayer(prevLoc, newLoc);
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
