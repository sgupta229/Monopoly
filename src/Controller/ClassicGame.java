package Controller;

import Model.AbstractPlayer;
import Model.XmlReaderException;
import java.util.ArrayList;
import java.util.List;

public class ClassicGame extends AbstractGame {

    public ClassicGame(String filename) throws XmlReaderException {
        super(filename);
    }

    @Override
    public List<Integer> rollDice() {
        int oldIndex = getCurrPlayer().getCurrentLocation();
        List<Integer> rolls = super.rollDice();
        int rollVal = getLastDiceRoll();
        int newIndex = getNewIndex(oldIndex, rollVal);
        checkJail(oldIndex, newIndex);
        checkPassGo(oldIndex, newIndex);
        checkSnakeEyes(rolls);
        checkDoublesForJail();
        return rolls;
    }

    //checks 3 matching all dice in a row
    public boolean checkDoublesForJail() {
        if(getDiceHistory().get(0).size() < 3) {
            return false;
        }
        ArrayList<Integer> firstDie = getDiceHistory().get(0);
        List<Integer> check = new ArrayList<>(firstDie.subList(firstDie.size() - 3, firstDie.size()));
        for(Integer key : getDiceHistory().keySet()) {
            ArrayList<Integer> otherDie = getDiceHistory().get(key);
            List<Integer> other = new ArrayList<>(otherDie.subList(otherDie.size() - 3, otherDie.size()));
            if(!check.equals(other)) {
                return false;
            }
        }
        movePlayer(getCurrPlayer().getCurrentLocation(), 10);
        getCurrPlayer().setJail(true);
        return true;
    }

    public boolean checkGameOver() {
        int numPlayers = getPlayers().size();
        int numPlayersBankrupt = 0;
        for(AbstractPlayer p : getPlayers()) {
            if(p.getFunds() <= 0) {
                numPlayersBankrupt++;
            }
        }
        if(numPlayersBankrupt == numPlayers - 1) {
            return true;
        }
        return false;
    }

    public AbstractPlayer getWinner() {
        for(AbstractPlayer p : getPlayers()) {
            if(p.getFunds() > 0) {
                return p;
            }
        }
        throw new IllegalArgumentException("There is no winner");
    }
}
