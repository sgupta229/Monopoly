package Model;

public interface Deck {
    public ActionCard drawCard();
    public void discardCard(ActionCard card);
    public void shuffleDeck();
}
