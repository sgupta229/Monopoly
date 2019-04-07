package Controller;

import Model.AbstractPlayer;
import Model.Bank;
import Model.Deck;
import Model.Die;

import java.util.ArrayList;

public abstract class AbstractGame {
    private ArrayList<AbstractPlayer> players;
    private Bank bank;
    private Board board;
    private AbstractPlayer currPlayer;
    private Die[] dice;
    private ArrayList<Deck> decks;

    public AbstractGame(ArrayList<AbstractPlayer> players, Bank bank, Board board, Die[] dice, ArrayList<Deck> decks) {
        this.players = players;
        this.bank = bank;
        this.board = board;
        this.currPlayer = players.get(0);
        this.dice = dice;
        this.decks = decks;
    }

    public AbstractPlayer getCurrPlayer() {
        return currPlayer;
    }

    public int rollDice() {
        int value = 0;
        for(int i = 0; i < dice.length; i++) {
            value += dice[i].rollDie();
        }
        return value;
    }

    public Bank getBank() {
        return bank;
    }

    public Board getBoard() {
        return board;
    }

    public void startNextTurn() {
        int index = players.indexOf(currPlayer) + 1;
        if(index > (players.size() - 1)) {
            index = 0;
        }
        currPlayer = players.get(index);
    }
}
