package Controller;

import Model.AbstractPlayer;
import Model.XmlReaderException;

import java.util.ArrayList;
import java.util.List;
import Model.properties.Property;

public class JuniorGame extends AbstractGame {

    public JuniorGame(String filename) throws XmlReaderException {
        super(filename);
    }

    @Override
    public List<Integer> rollDice() {
        int oldIndex = getCurrPlayer().getCurrentLocation();
        List<Integer> rolls = super.rollDice();
        int rollVal = getLastDiceRoll();
        int newIndex = getNewIndex(oldIndex, rollVal);
        handleMoveInJail(oldIndex, newIndex);
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
        for(AbstractPlayer p : getPlayers()) {
            if(p.getCantPayBool()) {
                return true;
            }
        }
        return false;
    }

    public AbstractPlayer getWinner() {
        double maxFunds = 0;
        int maxFundsPlayerNum = 0;
        AbstractPlayer winner = getPlayers().get(0);
        int winnerPropNum = 0;
        for(AbstractPlayer p : getPlayers()) {
            if(p.getFunds() > maxFunds) {
                winner = p;
                maxFundsPlayerNum = getPlayers().indexOf(p);
                winnerPropNum = p.getProperties().size();
            }
        }
        for(int x=0; x<getPlayers().size(); x++) {
            if(x!=maxFundsPlayerNum && getPlayers().get(x).getFunds()==maxFunds) {
                int playersPropNum = getPlayers().get(x).getProperties().size();
                if(playersPropNum>winnerPropNum){
                    winner = getPlayers().get(x);
                }
            }
        }
        return winner;
    }
}
