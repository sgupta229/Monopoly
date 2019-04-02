/**
 * A player lands on Chance, draws "Bank pays you a dividend of $50", your funds are updated appropriately, and the card is returned to the bottom of the deck
 */
import doc.api*;

public class useCase1 {
    Player player = new Player();
    Bank bank = new Bank();
    Dice die = new Dice();
    Board board = new Board();
    Game game = new Game();
    Token token = new Token();
    Deck chanceDeck = new Deck();

    int roll = game.dice.roll(6);
    int newLocation = player.token.move(roll);
    Space sp = board.getSpaceAt(newLocation);
    sp.doAction(game);

    //doAction method will ->
    ActionCard cd = game.chanceDeck.draw();
    cd.doAction(game);

    //cd.doAction metho will ->
    bank.withdraw(50);
    player.receive(50);
}