package Model.actioncards;

import Controller.AbstractGame;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class AbstractActionCard implements Serializable {
    private DeckType myDeckType;
    private ActionDeck myDeck;
    private String myMessage;
    private Boolean isHoldable;

    //Initialize empty decks
    //Parse action card data checking for deck type then using that deck in this constructor
    //enum.valueOf(string "CHANCE") will create the enum -- IllegalArgumentException
    //deckType must be all upper case and match the enums in DeckType.java exactly
    //https://www.baeldung.com/java-string-to-enum
    //Parsing data should create List<AbstractActionCard> allActionCards with all types of action cards from xml
    public AbstractActionCard(DeckType deckType, String message, Boolean holdable){
        myDeckType = deckType;
        myMessage = message;
        isHoldable = holdable;
    }

    //New constructor to work with refactoring
    public AbstractActionCard(DeckType deckType, String message, Boolean holdable, List<String> extraString, List<Double> extraDouble){
        myDeckType = deckType;
        myMessage = message;
        isHoldable = holdable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof AbstractActionCard)){
            return false;
        }
        AbstractActionCard that = (AbstractActionCard) o;
        return myDeckType == that.myDeckType &&
                Objects.equals(myDeck, that.myDeck) &&
                Objects.equals(myMessage, that.myMessage) &&
                Objects.equals(isHoldable, that.isHoldable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(myDeckType, myDeck, myMessage, isHoldable);
    }

    //sets the card's deck for returning
    public void setDeck(ActionDeck deck){
        myDeck = deck;
    }

    //abstract filled out in each subclass
    public abstract void doCardAction(AbstractGame game);

    /**
     * Getters for deckType; isHoldable; MyMessage; MyDeck
     * @return DeckType; boolean; string message; ActionDeck respectively
     */
    //Getters here
    public DeckType getMyDeckType(){
        return myDeckType;
    }
    public boolean getIsHoldable(){
        return isHoldable;
    }
    public String getMyMessage(){
        return myMessage;
    }
    public ActionDeck getMyDeck(){
        return myDeck;
    }
}
