package Model.properties;

import Model.AbstractPlayer;
import Model.properties.Property;

import java.util.List;

public class UtilityProperty extends Property {

    private String myGroup = "UTILITY";
    private double rentMult;
    private double rentMult2;
    private double mortgage;
    private final double INFO_NUM = 3;


    public UtilityProperty(double price, String propName, List<Double> paymentInfo, int groupSize){
        super(price, propName, paymentInfo, groupSize);
        setGroup(myGroup);

    }

    protected void initializePaymentInfo(List<Double> paymentInformation){
        if(paymentInformation.size()>=INFO_NUM){
            rentMult = paymentInformation.get(0);
            rentMult2 = paymentInformation.get(1);
            mortgage = paymentInformation.get(2);
        }
        else{
            throw new IndexOutOfBoundsException("Bad data");
        }


    }


    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    public double calculateRent(AbstractPlayer propOwner, int lastDiceRoll){
        double rentTotal = 0.0;
        if(this.getIsMortgaged()){
            return 0.0;
        }
        else{
            int numberOfUtilities = propOwner.getPropertiesOfType("Utility");
            if(numberOfUtilities==1){
                rentTotal+=lastDiceRoll*rentMult;
            }
            else{
                rentTotal+=lastDiceRoll*rentMult2;
            }
        }
        return rentTotal;
    }


}
