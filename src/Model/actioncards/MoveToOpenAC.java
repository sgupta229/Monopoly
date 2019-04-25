package Model.actioncards;

import Controller.AbstractGame;
import Model.AbstractPlayer;
import Model.properties.Property;
import Model.spaces.AbstractSpace;

import java.util.ArrayList;
import java.util.List;

public class MoveToOpenAC extends AbstractActionCard {
    //List of colors to chose from for junior monopoly
    private List<String> myTargetSpaces;
    private Double passGo;
    private List<AbstractSpace> myPossibleSpaces;

    public MoveToOpenAC(DeckType deckType, String message, Boolean holdable, List<String> extraStrings, List<Double> extraDoubles){
        super(deckType, message, holdable);
        //System.out.println(extraStrings.get(0));
        myTargetSpaces = extraStrings;
        passGo = extraDoubles.get(0);
        myPossibleSpaces = new ArrayList<>();
    }

    /**
     * Like move to nearest but only make move if property is unowned
     * This is in junior monopoly only and player gets this unowned property for free
     * @param game to get board and locations
     */
    @Override
    public void doCardAction(AbstractGame game) {
        AbstractPlayer curr = game.getCurrPlayer();
        int prevLocation = curr.getCurrentLocation();
        ActionDeck d = this.getMyDeck();
        for(int i=0; i<game.getBoardSize(); i++){
            AbstractSpace tempSpace = game.getBoard().getSpaceAt(i);
            Property p = tempSpace.getMyProp();

            if(p!=null && myTargetSpaces.contains(p.getColor())){
                //System.out.println("HERE");
                //property is unowned -- move here and get property for free (junior monopoly only)
                if(game.getBank().getUnOwnedProps().contains(p)){
                    game.movePlayer(prevLocation, i);
                    game.getBank().setPropertyOwner(p,curr);
                    p.setIsOwned(true);
                    d.discardCard(this);
                    return;
                }
                else{
                    myPossibleSpaces.add(tempSpace);
                }
            }
        }
        game.movePlayer(prevLocation, myPossibleSpaces.get(0).getMyLocation());
        d.discardCard(this);
    }
}
