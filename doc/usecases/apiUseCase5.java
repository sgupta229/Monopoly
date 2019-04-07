package useCases;

import Controller.Board;
import Controller.Dice;
import Model.*;
import org.mockito.Mockito;

/***
 * 18. A player lands on a mortgaged property, no rent has to be paid.
 */
public class apiUseCase5 {
    Player player = Mockito.mock(Player.class);
    Board board = Mockito.mock(Board.class);
    Game game = Mockito.mock(Game.class);
    Bank bank = Mockito.mock(Bank.class);
    Dice dice = Mockito.mock(Dice.class);
    Deck chanceDeck = Mockito.mock(Deck.class);

    game.startTurn(player);
    //In start turn method of game:
    int roll = game.dice.roll(6);
    int newLocation = player.token.move(roll);
    Space sp = board.getSpaceAt(newLocation);
    sp.doAction(game);

    //doAction method for this property space will get the property ->
    Property landedOn = Mockito.mock(Property.class);
    landedOn.calculateRent();
    //calculate rent checks all specs of the property... including if
    //it is mortgagaed or not. Thus, the calculated rent would return 0.
    float calculatedRent = (float) 0.0;

    //the player would then call the Transfer function it implemented makePayment, which would specify that it
    //is making a payment of 0 to the owner of that property. Within this method, it find the owner of the property by:
    Player owner  = bank.propertyOwned(landedOn);

    //within the makePayment, this is called:
    owner.receivePayment(calculatedRent);
    //which pays the owner 0 dollars

    //the game would then go to the next turn
}