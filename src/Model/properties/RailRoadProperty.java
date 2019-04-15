package Model.properties;

import java.util.List;

public class RailRoadProperty extends Property {

    private String myGroup;
    private double rent;
    private double rent2;
    private double rent3;
    private double rent4;
    private double mortgage;
    private final double INFO_NUM = 5;


    public RailRoadProperty(double price, String propName, List<Double> paymentInfo){
        super(price, propName, paymentInfo);
        myGroup = propName;
    }

    protected void initializePaymentInfo(List<Double> paymentInformation){
        if(paymentInformation.size()>=INFO_NUM){
            rent = paymentInformation.get(0);
            rent2 = paymentInformation.get(1);
            rent3 = paymentInformation.get(2);
            rent4 = paymentInformation.get(3);
            mortgage = paymentInformation.get(4);
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
