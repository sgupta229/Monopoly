package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.DeckType;

public class GoToJailAC extends AbstractActionCard {
    public GoToJailAC(DeckType deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        int loc = game.getBoard().getLocationOfSpace("JAIL");
        curr.moveTo(loc, game.getBoardSize());
        curr.setJail(true);
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
