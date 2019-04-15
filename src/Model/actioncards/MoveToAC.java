package Model.actioncards;


import Controller.AbstractGame;
import Model.AbstractPlayer;

public class MoveToAC extends AbstractActionCard {
    private String myTargetSpace;

    public MoveToAC(DeckType deckType, String message, Boolean holdable, String targetSpace) {
        super(deckType, message, holdable);
        myTargetSpace = targetSpace;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int newLocation = game.getBoard().getLocationOfSpace(myTargetSpace);
        game.movePlayer(prevLocation, newLocation);
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
