package Controller;

import Model.Bank;
import Model.Player;

import java.util.List;

public interface Game {
    public Player getCurrentPlayer();
    public void startTurn(Player currentPlayer);
    public Bank getBank();
    public int getDiceRoll();
    public List<Player> getPlayersOrder();
    public Board getBoard();
}
