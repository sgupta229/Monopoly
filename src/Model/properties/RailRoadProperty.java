package Model.properties;


import Model.AbstractPlayer;

import java.util.*;

public class RailRoadProperty extends Property {

    private String myGroup= "RAILROAD";
    //private double rent;
    //private double rent2;
    //private double rent3;
    //private double rent4;
    //private double mortgage;
    private List<Double> rentNumbers;
    private final double INFO_NUM = 5;
    private final int FOUR = 4;

    public RailRoadProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
        super(price, propName, color, paymentInfo, groupSize, buildingPriceMap);
        setGroup(myGroup);
    }


    public RailRoadProperty(double price, String propName, List<Double> paymentInfo, int groupSize){
        super(price, propName, paymentInfo, groupSize);
//        myColor = general.getString("railroadColor");
        setGroup(myGroup);
    }

    @Deprecated
    public RailRoadProperty(double price, String propName, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPricesMap){
        super(price, propName, paymentInfo, groupSize);
        setGroup(myGroup);
    }

    public List getInfo(){
        ArrayList ret = new ArrayList();
        ret.addAll(Arrays.asList(this.getPrice()));
        for(double num:rentNumbers){
            ret.add(num);
        }
        ret.add(this.getName());
        return ret;
    }

    protected void initializePaymentInfo(List<Double> paymentInformation){
        List<Double> paymentInformationCopy = paymentInformation;
        if(paymentInformation.size()>=INFO_NUM){
            //rent = paymentInformationCopy.get(0);
            //rent2 = paymentInformationCopy.get(1);
            //rent3 = paymentInformationCopy.get(2);
            //rent4 = paymentInformationCopy.get(3);
            setMortgageAmount(paymentInformationCopy.get(FOUR));
            rentNumbers = paymentInformationCopy;
        }
        else{
            throw new IndexOutOfBoundsException("Bad data");
        }

    }

    @Deprecated
    public void build(){}

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
            int numberOfRailRoads = propOwner.getPropertiesOfType(myGroup).size();
            rentTotal+=rentNumbers.get(numberOfRailRoads-1);

        }
        return rentTotal;
    }

}
