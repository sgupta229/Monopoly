package Model.actioncards;


import Controller.AbstractGame;
import Model.DeckType;

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
        System.out.println("hello?");
    }
}
