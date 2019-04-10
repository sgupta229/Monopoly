package Controller;

import Model.AbstractPlayer;
import Model.Bank;
import Controller.Die;
import Model.ActionDeck;

import java.util.ArrayList;
import java.util.List;

public class ClassicGame extends AbstractGame {

    public ClassicGame(Bank bank, Board board, Die[] dice, ArrayList<ActionDeck> decks) {
        super(bank, board, dice, decks);
    }

}
