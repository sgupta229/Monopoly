package Controller;

import java.io.Serializable;

public class Die implements Serializable {
    private int numSides;
    private int[] values;

    public Die(int numSides, int[] values) {
        this.numSides = numSides;
        this.values = values;
    }
    public Die(int numSides){
        this.numSides = numSides;
        int[] vals = new int[numSides];
        for (int i=0; i<numSides; i++){
            vals[i] = i+1;
        }
        this.values = vals;
    }

    public int rollDie() {
        int max = values.length - 1;
        int range = max + 1;
        int index = (int)(Math.random() * range);
        return values[index];
    }
}
