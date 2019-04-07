package Controller;

import Model.AbstractPlayer;
import Model.Bank;
import Controller.Die;
import Model.ActionDeck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractGame {
    private ArrayList<AbstractPlayer> players;
    private Bank bank;
    private Board board;
    private AbstractPlayer currPlayer;
    private Die[] dice;
    private ArrayList<ActionDeck> decks;
    private HashMap<Integer, ArrayList<Integer>> diceHistory = new HashMap<Integer, ArrayList<Integer>>();

    public AbstractGame(ArrayList<AbstractPlayer> players, Bank bank, Board board, Die[] dice, ArrayList<ActionDeck> decks) {
        this.players = players;
        this.bank = bank;
        this.board = board;
        this.currPlayer = players.get(0);
        this.dice = dice;
        this.decks = decks;
        for(int i = 0; i < dice.length; i++) {
            diceHistory.put(i, new ArrayList<Integer>());
        }
    }

    public AbstractPlayer getCurrPlayer() {
        return currPlayer;
    }

    public int rollDice() {
        int value = 0;
        for(int i = 0; i < dice.length; i++) {
            int roll = dice[i].rollDie();
            value += roll;
            diceHistory.get(i).add(roll);
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

    //checks 3 matching all dice in a row
    public boolean checkDoubles() {
        ArrayList<Integer> firstDie = diceHistory.get(0);
        List<Integer> check = firstDie.subList(firstDie.size() - 3, firstDie.size());
        for(Integer key : diceHistory.keySet()) {
            ArrayList<Integer> otherDie = diceHistory.get(key);
            List<Integer> other = otherDie.subList(otherDie.size() - 3, otherDie.size());
            if(!check.equals(other)) {
                return false;
            }
        }
        return true;
    }

}
