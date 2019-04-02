/**
 * A player lands on Chance, draws "Bank pays you a dividend of $50", your funds are updated appropriately, and the card is returned to the bottom of the deck
 */
import Controller.*;
import Model.*;

public class useCase1 {
    //Player holds instance of token
    Player player = new Player();
    //Board holds instances of spaces
    Board board = new Board();
    //Game holds instances of players, bank, dice, board, deck
    Game game = new Game();

    Bank bank = new Bank();
    Dice die = new Dice();
    Deck chanceDeck = new Deck();

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