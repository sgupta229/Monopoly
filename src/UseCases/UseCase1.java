//package UseCases;
//
//import monopoly_team04.Model.Controller.*;
//import monopoly_team04.Model.*;
//import org.mockito.Mockito;
//
///** USE CASE:
// * A player lands on Chance, draws "Bank pays you a dividend of $50", your funds are updated appropriately, and the card is returned to the bottom of the deck
// */
//
//public class UseCase1 {
//    private Game gameMock = Mockito.mock(Game.class);
//    private DeckType deckType = DeckType.CHANCE;
//
//    Player currPlayer = gameMock.getCurrentPlayer();
//    Token token = currPlayer.getMyToken();
//    Board board = gameMock.getBoard();
//    Bank bank = gameMock.getBank();
//    Deck chanceDeck = gameMock.getActionDeck(deckType);
//
//    //Starts turn for player
//    gameMock.startTurn(currPlayer);
//
//    //In start turn method of game:
//    int roll = gameMock.getDiceRoll();
//    int newLocation = token.move(roll);
//    Space sp = board.getSpaceAt(newLocation);
//    sp.doAction(gameMock);
//
//    //doAction method for chance space will ->
//    ActionCard cd = chanceDeck.drawCard();
//    cd.doAction(gameMock);
//
//    //cd.doAction method will ->
//    //bank will implement transfer interface with makePayment method
//    bank.makePayment(50, currPlayer);
//}
