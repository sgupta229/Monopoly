package Model;

public class UtilityProperty extends Property{


    public UtilityProperty(double price){
        super(price);
    }


    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public double calculateRent(){
        return 0.0;
    }
}
