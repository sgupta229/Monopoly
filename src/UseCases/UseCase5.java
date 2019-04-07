package UseCases;

import Controller.Board;
import Controller.Dice;
import Controller.Game;
import Model.*;
import org.mockito.Mockito;

/***
 * 18. A player lands on a mortgaged property, no rent has to be paid.
 */
public class UseCase5 {
    Player player = Mockito.mock(Player.class);
    Board board = Mockito.mock(Board.class);
    Game game = Mockito.mock(Game.class);
    Bank bank = Mockito.mock(Bank.class);
    Dice dice = Mockito.mock(Dice.class);
    Deck chanceDeck = Mockito.mock(Deck.class);

    game.startTurn(player);
    //In start turn method of game:
    //player is given the option to roll the dice. When they click the button, this is called
    int roll = game.dice.roll(6);
    //the dice.roll returns an integer, which determines how many spaces ahead the player moves
    //say the integer returned was 5, we then call
    int newLocation = player.token.move(roll);
    //which moves the player to a new space. Then, in order to see what type of space it is
    //and invoke the specific action of that space, this is caled
    Space sp = board.getSpaceAt(newLocation);
    //it returns that the type of space we landed on was a PropSpace, and that spaces doAction is called
    sp.doAction(game);

    //doAction method for this property space will get the specific property (such as Main St) and then call the Property
    //method, calculcate rent.
    Property landedOn = Mockito.mock(Property.class);
    //
    landedOn.calculateRent();
    //calculate rent checks all specs of the property... including if
    //it is mortgagaed or not. Thus, the calculated rent would return 0.
    float calculatedRent = (float) 0.0;

    //the player would then call the Transfer function that player implemented,  makePayment, which would specify that it
    //is making a payment of 0 to the owner of that property. Within this method, it find the owner of the property by:
    Player owner  = bank.propertyOwned(landedOn);

    //within the makePayment, this is called:
    owner.receivePayment(calculatedRent);
    //which pays the owner 0 dollars

    //the game would then go to the next turn
}
