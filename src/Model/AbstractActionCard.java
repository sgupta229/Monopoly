package Model;

import Controller.AbstractGame;


public abstract class AbstractActionCard {
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

    public void setDeck(ActionDeck deck){
        myDeck = deck;
    }

    public abstract void doCardAction(AbstractGame game);

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
