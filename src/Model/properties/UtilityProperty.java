package Model.properties;

import Model.AbstractPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UtilityProperty extends Property {

    private String myGroup = "UTILITY";
    private double rentMult;
    private double rentMult2;
    //private double mortgage;
    private static final double INFO_NUM = 3;

    public UtilityProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
        super(price, propName, color, paymentInfo, groupSize, buildingPriceMap);
        setGroup(myGroup);
    }

    public UtilityProperty(double price, String propName, List<Double> paymentInfo, int groupSize){
        super(price, propName, paymentInfo, groupSize);
//        myColor = general.getString("utilitiesColor");
        setGroup(myGroup);
    }

    @Deprecated
    public UtilityProperty(double price, String propName, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPricesMap){
        super(price, propName, paymentInfo, groupSize);
        setGroup(myGroup);

    }

    protected void initializePaymentInfo(List<Double> paymentInformation){
        if(paymentInformation.size()>=INFO_NUM){
            rentMult = paymentInformation.get(0);
            rentMult2 = paymentInformation.get(1);
            setMortgageAmount(paymentInformation.get(2));
        }
        else{
            throw new IndexOutOfBoundsException("Bad data");
        }
    }

    public List getInfo(){
        ArrayList ret = new ArrayList();
        ret.addAll(Arrays.asList(this.getPrice(), rentMult, rentMult2, this.getMortgageAmount(), this.getName()));
        return ret;
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
