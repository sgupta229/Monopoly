package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;

public class GoToJailAC extends AbstractActionCard {
    public GoToJailAC(DeckType deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLoc = curr.getCurrentLocation();
        int newLoc = game.getBoard().getLocationOfSpace("JAIL");
        curr.setJail(true);

        game.movePlayer(prevLoc, newLoc);
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
