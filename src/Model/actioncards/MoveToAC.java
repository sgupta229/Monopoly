package Model.actioncards;


import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.spaces.SpaceGroup;

public class MoveToAC extends AbstractActionCard {
    private String myTargetSpace;

    public MoveToAC(DeckType deckType, String message, Boolean holdable, String targetSpace) {
        super(deckType, message, holdable);
        myTargetSpace = targetSpace;
    }

    @Override
    public void doCardAction(AbstractGame game) {
        try{
            int spaces = Integer.parseInt(myTargetSpace);
            numberMove(game, spaces);
            ActionDeck d = this.getMyDeck();
            d.discardCard(this);
        }
        catch (NumberFormatException e){
            int counter = 0;
            for(SpaceGroup sg : SpaceGroup.values()){
                counter ++;
                if (sg.name().equalsIgnoreCase(myTargetSpace)){
                    generalMove(game, sg);
                }
                else if(counter >= SpaceGroup.values().length){
                    specificMove(game);
                }

            }
            ActionDeck d = this.getMyDeck();
            d.discardCard(this);
        }
    }

    public void generalMove(AbstractGame game, SpaceGroup group){
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int boardSize = game.getBoardSize();
        for(int i=prevLocation+1; i<boardSize+prevLocation; i++){
            if(i >= boardSize){
                i = i-boardSize;
            }
            if(game.getBoard().getSpaceAt(i).getMyGroup() == group){
                game.movePlayer(prevLocation, i);
            }
        }
    }

    public void numberMove(AbstractGame game, int spaces){
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int newLocation = getIndexOnBoard(prevLocation, spaces, game.getBoardSize());
        game.movePlayer(prevLocation, newLocation);
    }

    public void specificMove(AbstractGame game){
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        int newLocation = game.getBoard().getLocationOfSpace(myTargetSpace);
        game.movePlayer(prevLocation, newLocation);
    }

    private int getIndexOnBoard(int start, int change, int boardSize){
        int newDex = start + change;
        if(newDex >= boardSize){
            return newDex - boardSize;
        }
        else if(newDex < 0){
            return boardSize + newDex;
        }
        return newDex;
    }
}
