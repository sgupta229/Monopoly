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
        if(myTargetSpace.equalsIgnoreCase("RAILROAD") || myTargetSpace.equalsIgnoreCase("UTILITY")){
            generalMove(game);
        }
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int newLocation = game.getBoard().getLocationOfSpace(myTargetSpace);
        game.movePlayer(prevLocation, newLocation);
        ActionDeck d = this.getMyDeck();
        d.discardCard(this);
    }

    public void generalMove(AbstractGame game){
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int boardSize = game.getBoardSize();
        for(int i=prevLocation+1; i<boardSize+prevLocation; i++){
            if(i >= boardSize){
                i = i-boardSize;
            }
            //if(game.getBoard().getSpaceAt(i).getMyGroup().equalsIgnoreCase(myTargetSpace)){
            //}
        }
    }
}
