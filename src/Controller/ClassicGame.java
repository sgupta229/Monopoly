package Controller;

import Model.AbstractPlayer;

public class ClassicGame extends AbstractGame {

    public ClassicGame(String filename) {
        super(filename);
        ConfigReader configReader = new ConfigReader(filename);
    }

    @Override
    public int rollDice() {
        int oldIndex = getCurrPlayer().getCurrentLocation();
        int roll = super.rollDice();
        int newIndex = getNewIndex(oldIndex, roll);
        if(!getCurrPlayer().isInJail()) {
            this.movePlayer(oldIndex, newIndex);
        }
        else {
            getCurrPlayer().incrementNumRollsinJail();
            if(getCurrPlayer().getNumRollsInJail() == getRollsInJailRule()) {
                this.movePlayer(oldIndex, newIndex);
                getCurrPlayer().resetNumRollsInJail();
                getCurrPlayer().setJail(false);
            }
        }
        checkPassGo(oldIndex, newIndex);
        return roll;
    }

    public void checkPassGo(int oldIndex, int newIndex) {
        if(newIndex < oldIndex) {
//        if(0 <= newIndex && 0>= oldIndex) {
            getCurrPlayer().addFunds(getPassGo());
        }
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
