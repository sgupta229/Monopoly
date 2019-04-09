package Model;


import Controller.AbstractGame;

public class MoveToAC extends AbstractActionCard {
    private String myTargetSpace;

    public MoveToAC(DeckType deckType, String message, Boolean holdable, String targetSpace) {
        super(deckType, message, holdable);
        myTargetSpace = targetSpace;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }
}
