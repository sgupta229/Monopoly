package useCases;

import monopoly_team04.Model.Controller.Board;
import monopoly_team04.Model.Controller.Dice;
import monopoly_team04.Model.Controller.Game;
import monopoly_team04.Model.*;
import Controller.Board;
import Controller.Dice;
import Model.*;
import org.mockito.Mockito;

/***
 9. A player collects $200, when they pass the Go space.
 */
public class apiUseCase6 {

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
    //within the move function, we will check if the index of the new current location is less than
    //the index of the old current location. If this is the case, the player passed go, and the call below is made
    // (this also still lets someone go straight to jail, as
    //it is in move and not moveTo().

    player.receivePayment(200);
    //which augments the players funds by 200


}