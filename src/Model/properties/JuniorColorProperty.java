package Model.properties;

import Model.AbstractPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JuniorColorProperty extends ColorProperty {

    //private int numHouse;
    //private List<Double> rentNumbers;


    public JuniorColorProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
        super(price, propName, color, paymentInfo, groupSize, buildingPriceMap);
        setGroup(color);
    }

    /*protected void initializePaymentInfo(List<Double> paymentInformation) throws IndexOutOfBoundsException{
        List<Double> paymentInformationCopy = paymentInformation;

            //pricePerHouse = paymentInformation.get(6);
            //pricePerHotel = paymentInformation.get(7);
            setMortgageAmount(paymentInformationCopy.get(paymentInformationCopy.size()-1));
            rentNumbers = paymentInformationCopy.subList(0, paymentInformationCopy.size()-1);

    }*/

    public List getInfo(){
        ArrayList ret = new ArrayList();
        ret.addAll(Arrays.asList(getColor(), this.getPrice()));
//        ret.add(getRentNumbers().get(0));
        ret.add(this.getMortgageAmount());
        ret.add(this.getName());
        return ret;
    }


/*    *//***
     * Allows for users to see the color of a property which is crucial for monopolies
     * @return the color of this property
     *//*
    public String getPropertyColor(){
        return myColor;
    }*/

    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @return the total rent value to be paid
     */
    @Override
    public double calculateRent(AbstractPlayer propOwner, int lastDiceRoll){
        double rentNums;
        if(this.getIsMortgaged()){
            return  0.0;
        }
        else{
            rentNums = getRentNumbers().get(0);
            if(propOwner.checkMonopoly(this)){
                rentNums = rentNums*2;
            }
            return rentNums;
        }
    }

    public void addBuilding(BuildingType building) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Junior Doesn't Build");
    }

    public void removeBuilding(BuildingType building, int amount) throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Junior Doesn't Build");

    }

    public int getNumBuilding(BuildingType building)throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Junior Doesn't Build");
        //return 0;
    }
}
