package UseCases;

import Controller.*;
import Model.*;
import org.mockito.Mockito;

public class UseCase1 {
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
    game.bank.makePayment(50, player);
}
