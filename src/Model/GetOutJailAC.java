package Model;

import Controller.AbstractGame;

public class GetOutJailAC extends AbstractActionCard {
    public GetOutJailAC(String deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Override
    public void doCardAction(AbstractGame game) {

    }
}
