package Model;

import Controller.AbstractGame;

public class GoToJailAC extends AbstractActionCard {
    public GoToJailAC(String deckType, String message, Boolean holdable) {
        super(deckType, message, holdable);
    }

    @Override
    public void doCardAction(AbstractGame game) {

    }
}