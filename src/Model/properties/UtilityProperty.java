package Model.properties;

import Model.properties.Property;

import java.util.List;

public class UtilityProperty extends Property {

    private String myGroup;
    private double rentMult;
    private double rentMult2;
    private double mortgage;
    private final double INFO_NUM = 3;


    public UtilityProperty(double price, String propName, List<Double> paymentInfo){
        super(price, propName, paymentInfo);
        myGroup = propName;

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

    public void build(){

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
