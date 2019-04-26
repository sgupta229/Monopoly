package Controller;

import Model.AbstractPlayer;
import Model.XmlReaderException;

public class ClassicGame extends AbstractGame {

    public ClassicGame(String filename) throws XmlReaderException {
        super(filename);
    }

    //checks 3 matching all dice in a row

    public boolean checkGameOver() {
        int numPlayers = getPlayers().size();
        int numPlayersBankrupt = 0;
        for(AbstractPlayer p : getPlayers()) {
            if(p.getCantPayBool()) {
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
            if(!p.getCantPayBool()) {
                return p;
            }
        }
        throw new IllegalArgumentException("There is no winner");
    }
}
