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

    public boolean checkGameOver() {
        System.out.println("junior checking game over");
        for(AbstractPlayer p : getPlayers()) {
            if(p.getCantPayBool()) {
                System.out.println("in the loop");
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
                maxFunds = p.getFunds();
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
