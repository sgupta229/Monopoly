package Model.properties;


import Model.AbstractPlayer;

import java.util.List;

public class RailRoadProperty extends Property {

    private String myGroup= "RAILROAD";
    private double rent;
    private double rent2;
    private double rent3;
    private double rent4;
    private double mortgage;
    private List<Double> rentNumbers;
    private final double INFO_NUM = 5;


    public RailRoadProperty(double price, String propName, List<Double> paymentInfo, int groupSize){
        super(price, propName, paymentInfo, groupSize);
    }

    protected void initializePaymentInfo(List<Double> paymentInformation){
        if(paymentInformation.size()>=INFO_NUM){
            rent = paymentInformation.get(0);
            rent2 = paymentInformation.get(1);
            rent3 = paymentInformation.get(2);
            rent4 = paymentInformation.get(3);
            mortgage = paymentInformation.get(4);
            rentNumbers = paymentInformation;
        }
        else{
            throw new IndexOutOfBoundsException("Bad data");
        }

    }

    @Deprecated
    public void build(){}

    public void build(BuildingType type, Property property){

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
            int numberOfRailRoads = propOwner.getPropertiesOfType("RailRoad");
            rentTotal+=rentNumbers.get(numberOfRailRoads-1);

        }
        return rentTotal;
    }


}
