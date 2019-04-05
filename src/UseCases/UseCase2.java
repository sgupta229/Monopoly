//package UseCases;
//
//import monopoly_team04.Model.Controller.*;
//import monopoly_team04.Model.*;
//import org.mockito.Mockito;
//
///** USE CASE:
// * A player lands on Go To Jail, token is moved directly to jail without passing go, player can either roll doubles or pay 50 to the bank to get out.
// */
//
//public class UseCase2 {
//    private Game gameMock = Mockito.mock(Game.class);
//    private DeckType deckType = DeckType.CHANCE;
//    private Space jailSpace = Mockito.mock(Space.class);
//
//    Player currPlayer = gameMock.getCurrentPlayer();
//    Token token = currPlayer.getMyToken();
//    Board board = gameMock.getBoard();
//    Bank bank = gameMock.getBank();
//    Deck chanceDeck = gameMock.getActionDeck(deckType);
//
//    //Start the turn here
//    gameMock.startTurn(currPlayer);
//    int roll = gameMock.getDiceRoll();
//    int newLocation = token.move(roll);
//    Space sp = board.getSpaceAt(newLocation);
//    sp.doAction(gameMock);
//
//    //Space's doAction will be the go to jail action since player landed on go to jail space
//    //doAction method does:
//    int jailLocation = board.getLocationOfSpace(jailSpace);
//    token.moveTo(jailLocation);
//    currPlayer.setJail(true);
//}
