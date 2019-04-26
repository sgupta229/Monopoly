package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.properties.BuildingType;

import java.util.List;
import java.util.Map;

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
    @Deprecated
    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String loseTo, List<Double> amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo;
        amountLose = amount;
    }

    public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, List<String> loseTo, List<Double> amount) {
        super(deckType, message, holdable);
        loseMoneyTo = loseTo.get(0);
        amountLose = amount;
    }

    //public LoseMoneyAC(DeckType deckType, String message, Boolean holdable, String extraString, List<Double> extraDoubles){}

    /**
     * Checks if multi pay or double pay (by house/hotel) then calls helper method
     * @param game takes in game to find players and bank
     */
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

    //Helper method for payments of single cost (not per house/per hotel)
    private void singlePay(AbstractGame game){
        double amnt = amountLose.get(0);
        AbstractPlayer currP = game.getCurrPlayer();
        if(loseMoneyTo.equalsIgnoreCase("bank")){
            currP.makePayment(game.getBank(), amnt,game.getBank());
        }
        else{
            for(AbstractPlayer p: game.getPlayers()){
                if(!p.equals(currP)){
                    currP.makePayment(game.getBank(), amnt, p);
                }
            }
        }
    }

    //If paying per house/hotel -- always to bank
    private void multiPay(AbstractGame game){
        AbstractPlayer currP = game.getCurrPlayer();
        double payment = 0;
        Map<BuildingType, Integer> buildingsMap = currP.getNumBuildings();

        //Must be changed for diff game types
        for(BuildingType bt : buildingsMap.keySet()){
            int numB = buildingsMap.get(bt);
            //Building level = 0 for house; 1 for hotel
            //Price per house at index 0; pp hotel at index 1
            payment += numB * amountLose.get(bt.getBuildingLevel());
        }
        currP.makePayment(game.getBank(), payment, game.getBank());
    }
}
