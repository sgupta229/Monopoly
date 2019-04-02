/**
 * A player lands on Go To Jail, token is moved directly to jail without passing go, player can either roll doubles or pay 50 to the bank to get out.
 */

import Controller.*;
import Model.*;

public class useCase2{
    //Player holds instances of tokens
    Player player = new Player();
    //Board holds instances of spaces
    Board board = new Board();
    //Game holds instances of players, bank, dice, board, deck
    Game game = new Game();

    Dice die = new Dice();
    Bank bank = new Bank();
    Deck communityChestDeck = new Deck();
    Deck chanceDeck = new Deck();

    //Start the turn here
    game.startTurn();
    int roll = die.roll(6);
    int newLocation = player.token.move(roll);
    Space sp = board.getSpaceAt(newLocation);
    sp.doAction(game);

    //Space's doAction will be the go to jail action since player landed on go to jail space
    //doAction method does:
    int jailLocation = game.board.getLocationOf(Space jailSpace);
    game.player.token.moveTo(jailLocation);
    game.player.setJail(true);
}