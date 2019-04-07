package Model;

import Controller.AbstractGame;

/**
 * Concrete subclasses will extend this eventual abstract class
 * Subclasses based on their doAction methods
 */
public interface ActionCard {

    /**
     * doAction method will vary for different cards
     * Some actions will include: go to jail, get out of jail, win money, lose money, move to space
     * Must take Game as parameter to handle all possible actions
     * Need game to access board if action is moveTo/goToJail
     * Need game to access bank if action is win money from bank and players if player to your right pays you...
     * @param game
     */
    public void doAction(AbstractGame game);
}