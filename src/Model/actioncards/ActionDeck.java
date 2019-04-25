package Model.actioncards;

import java.io.Serializable;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ActionDeck implements Serializable {
    private DeckType myDeckType;
    private Stack<AbstractActionCard> myLiveCards;
    private List<AbstractActionCard> myDeadCards;

    //Live will be empty until call deck.fillLiveDeck(all action cards list);
    public ActionDeck(DeckType deckType){
        myDeckType = deckType;
        myLiveCards = new Stack<>();
        myDeadCards = new ArrayList<>();
    }

    /**
     * Fill empty live deck and shuffle (contains CHANCE and COMMUNITY_CHEST cards -- sorted here)
     * @param cards list of action cards
     */
    //Uses a stack for logical drawing of top card sense
    public void fillLiveDeck(List<AbstractActionCard> cards){
        for(AbstractActionCard cd : cards){
            if(cd.getMyDeckType() == this.myDeckType) {
                myLiveCards.push(cd);
                cd.setDeck(this);
            }
        }
        Collections.shuffle(myLiveCards);
    }

    /**
     * Used by action spaces to draw top card from deck
     * @return top abstract action card
     */
    public AbstractActionCard drawCard(){
        AbstractActionCard topCard = myLiveCards.pop();
        if(myLiveCards.isEmpty()){
            this.shuffleDeck();
        }
        return topCard;
    }

    /**
     * adds a card to its correct dead card pile
     * @param card action card to return
     */
    //ASSUMPTION/SIMPLIFICATION -- discard to discard pile -- not to bottom of deck; then shuffle when live deck is empty
    //Call this in the AbstractActionCard doCardAction() method == myDeck.discardCard(this);
    public void discardCard(AbstractActionCard card){
        myDeadCards.add(card);
    }

    //fillLiveDeck shuffles the cards, just gotta refill here
    //https://www.geeksforgeeks.org/collections-shuffle-java-examples/
    private void shuffleDeck(){
        this.fillLiveDeck(myDeadCards);
        myDeadCards = new ArrayList<>();
    }

    /**
     * Getters for deckType; list of live cards; list of dead cards
     * @return DeckType; List of abstract action card
     */
    public DeckType getMyDeckType(){
        return myDeckType;
    }
    public Stack<AbstractActionCard> getMyLiveCards(){return myLiveCards;}
    public List<AbstractActionCard> getMyDeadCards(){ return myDeadCards;}
}
