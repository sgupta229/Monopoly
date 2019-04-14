package Model.actioncards;

import Controller.AbstractGame;
import Model.DeckType;

import java.util.List;

public class LoseMoneyAC extends AbstractActionCard {
    //List<Transfer> loseMoneyTo;
    String loseMoneyTo;
    List<Double> amountLose;


    //Deprecate -- was Double amount, now List<Double> amount
    @Deprecated
    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String loseTo, Double amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo;
        //amountLose = amount;
    }

    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String loseTo, List<Double> amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo;
        amountLose = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
        System.out.println("lose money");
    }
}
