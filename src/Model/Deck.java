package Model;

/**
 * Will hold action cards and handles their drawing and returning to piles
 * Will have two list of action cards - liveCards and discardCards
 */
public interface Deck {
    /**
     * Game will initialize decks (such as Deck communityChestDeck and Deck chanceDeck)
     * communityCheckDeck.drawCard() will be called when player lands on community chest space
     * Returns top card from community chest deck which will then perform that card's action
     * @return
     */
    public ActionCard drawCard();

    /**
     * Every time an action card is used, it will end its use by calling actionCard.myDeck.discardCard(this)
     * Thereby returning said action card to the correct Deck's discard pile
     * @param card
     */
    public void discardCard(ActionCard card);

    /**
     * Called by drawCard() whenever the liveDeck becomes empty
     * Re-orders discardDeck cards and places them in liveDeck, re-initializing discardDeck to empty
     */
    public void shuffleDeck();
}