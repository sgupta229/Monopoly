/**
 * A player lands on an owned property space, if the player does not own that space the player must pay the rent for that space to the player who owns it.
 */

import Controller.*;
import Model.*;

public class useCase2 {
    //Player holds instances of tokens
    Player currPlayer = new Player();
    //Board holds instances of spaces
    Board board = new Board();
    //Game holds instances of players, bank, dice, board, deck
    Game game = new Game();

    Dice die = new Dice();
    Bank bank = new Bank();
    Deck communityChestDeck = new Deck();
    Deck chanceDeck = new Deck();

    //Start the turn here
    game.startTurn(currPlayer);
    int roll = die.roll(6);
    int newLocation = currPlayer.token.move(roll);
    Space sp = board.getSpaceAt(newLocation);
    sp.doAction(game);

    // doAction method for this space is a property space's do action:
    // sp.getMyProperty() will be a specific helper method for property space concrete classes
    // returns player that owns that property
    Property prop = sp.getMyProperty();
    if(game.bank.propertyOwned(prop) != null && game.bank.propertyOwned(prop) != game.currPlayer){
        Player p = game.bank.propertyOwned(prop);
        int rent = prop.getRent();
        game.currPlayer.pay(p, rent);
    }


}