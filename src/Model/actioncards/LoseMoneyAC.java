package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.Transfer;

import java.util.List;

public class LoseMoneyAC extends AbstractActionCard {
    //List<Transfer> loseMoneyTo;
    String loseMoneyTo;
    List<Double> amountLose;


    //Deprecate -- was Double amount, now List<Double> amount
    @Deprecated
    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String loseTo, Double amount) {
        super(deckType, message, holdable);
        //loseMoneyTo = loseTo;
        //amountLose = amount;
    }

    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String loseTo, List<Double> amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo;
        amountLose = amount;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        if(amountLose.size() == 1){
            singlePay(game);
        }
        else{
            multiPay(game);
        }
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }

    public void singlePay(AbstractGame game){
        double amnt = amountLose.get(0);
        AbstractPlayer currP = game.getCurrPlayer();
        if(loseMoneyTo.equalsIgnoreCase("bank")){
            currP.makePayment(amnt,game.getBank());
        }
        else{
            for(AbstractPlayer p: game.getPlayers()){
                if(!p.equals(currP)){
                    currP.makePayment(amnt, p);
                }
            }
        }
    }

    //If paying per house/hotel -- always to bank
    public void multiPay(AbstractGame game){
        AbstractPlayer currP = game.getCurrPlayer();
        int payment = 0;
        //var buildingsMap = currP.getNumBuildings();
        //for(int num : buildingsMap.getValues()){
        //}

        //int numHouses = currP.getNumHouses();
        //int numHotels = currP.getNumHotels();

        //double payment = (5 * amountLose.get(0)) + (2 * amountLose.get(1));
        currP.makePayment(payment, game.getBank());
    }
}
