package Model.actioncards;

import Controller.AbstractGame;
import Model.DeckType;

public class GetOutJailAC extends AbstractActionCard {
    public GetOutJailAC(DeckType deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Override
    public void doCardAction(AbstractGame game) {
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
