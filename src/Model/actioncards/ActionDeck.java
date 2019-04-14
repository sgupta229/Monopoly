package Model.actioncards;

import java.util.*;

public class ActionDeck {
    private DeckType myDeckType;
    private Stack<AbstractActionCard> myLiveCards;
    private List<AbstractActionCard> myDeadCards;

    //Live will be empty until call deck.fillLiveDeck(all action cards list);
    public ActionDeck(DeckType deckType){
        myDeckType = deckType;
        myLiveCards = new Stack<>();
        myDeadCards = new ArrayList<>();
    }

    //Uses a stack for logical drawing of top card sense
    //First initialize empty decks
    //Pass in list of all action cards read from data --> fill all empty initialized decks with this master list
    //Will fill cards into correct decks based on myDeckType
    public void fillLiveDeck(List<AbstractActionCard> cards){
        for(AbstractActionCard cd : cards){
            if(cd.getMyDeckType() == this.myDeckType) {
                myLiveCards.push(cd);
                cd.setDeck(this);
            }
        }
        Collections.shuffle(myLiveCards);
    }

    public AbstractActionCard drawCard(){
        AbstractActionCard topCard = myLiveCards.pop();
        if(myLiveCards.isEmpty()){
            this.shuffleDeck();
        }
        return topCard;
    }

    //ASSUMPTION/SIMPLIFICATION -- discard to discard pile -- not to bottom of deck; then shuffle when live deck is empty
    //Call this in the AbstractActionCard doCardAction() method == myDeck.discardCard(this);
    public void discardCard(AbstractActionCard card){
        myDeadCards.add(card);
    }

    //https://www.geeksforgeeks.org/collections-shuffle-java-examples/
    public void shuffleDeck(){
        //Collections.shuffle(myDeadCards);
        //myLiveCards = new Stack<>();
        this.fillLiveDeck(myDeadCards);
        myDeadCards = new ArrayList<>();
    }

    public DeckType getMyDeckType(){
        return myDeckType;
    }
    public Stack<AbstractActionCard> getMyLiveCards(){return myLiveCards;}
    public List<AbstractActionCard> getMyDeadCards(){ return myDeadCards;}
}
