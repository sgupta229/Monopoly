package UseCases;

import Controller.*;
import Model.*;
import org.mockito.Mockito;

/** USE CASE:
 * A player lands on an owned property space, if the player does not own that space the player must pay the rent for that space to the player who owns it.
 */

public class UseCase3 {
    private Game gameMock = Mockito.mock(Game.class);
    private Property spaceProp = Mockito.mock(Property.class);

    //Game holds instances of all of these classes and therefore retreive them from getters in game
    Player currPlayer = gameMock.getCurrentPlayer();
    Token token = currPlayer.getMyToken();
    Board board = gameMock.getBoard();
    Bank bank = gameMock.getBank();

    //Start the turn here
    gameMock.startTurn(currPlayer);
    int roll = gameMock.getDiceRoll();
    int newLocation = token.move(roll);
    Space sp = board.getSpaceAt(newLocation);
    sp.doAction(gameMock);

    //Property space's doAction method will ->
    //bank.propertyOwned returns owner or null if unowned
    Player playerToPay = bank.propertyOwned(spaceProp);
    //property calculates own rent
    float rent = spaceProp.calculateRent();
    if(playerToPay != null){
        //Player will implement transfer interface and therefore will have method makePayment(amount, payTo)
        currPlayer.makePayment(rent, playerToPay);
    }
}