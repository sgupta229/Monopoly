package Controller;

public class Die {
    private int numSides;
    private int[] values;

    public Die(int numSides, int[] values) {
        this.numSides = numSides;
        this.values = values;
    }

    public int rollDie() {
        int max = values.length - 1;
        int range = max + 1;
        int index = (int)(Math.random() * range);
        return values[index];
    }
}
