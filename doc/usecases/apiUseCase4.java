package usecases

import monopoly_team04.Model.*;

/***
 * 2. A player lands on Chance, draws "Advance to Go, collect $200", your token is moved and your funds are updated appropriately,
 * and the card is returned to the bottom of the deck.
 */
import monopoly_team04.Model.Controller.Board;
import monopoly_team04.Model.Controller.Dice;
import monopoly_team04.Model.Controller.Game;
import monopoly_team04.Model.*;
import org.mockito.Mockito;

import monopoly_team04.Model.*;

/***
 * 2. A player lands on Chance, draws "Advance to Go, collect $200", your token is moved and your funds are updated appropriately,
 * and the card is returned to the bottom of the deck.
 */
public class apiUseCase4{
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

    //doAction method for chance space will ->
    ActionCard cd = game.chanceDeck.draw();
    cd.doAction(game);

    //cd.doAction method will ->
    game.bank.makePayment(200, player);
    //the Go space, which is a concrete Space class, will have a method that gets its index on the board
    int Goindex = 29;
    player.getMyToken.moveTo(Goindex);
}