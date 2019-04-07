package Controller;

import Model.AbstractPlayer;
import Model.Bank;
import Model.Deck;
import Model.Die;

import java.util.ArrayList;

public class ClassicGame extends AbstractGame {

    public ClassicGame(ArrayList<AbstractPlayer> players, Bank bank, Board board, Die[] dice, ArrayList<Deck> decks) {
        super(players, bank, board, dice, decks);
    }

}
