package Model.properties;

import Model.AbstractPlayer;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ClassicColorProperty extends ColorProperty {

    private int numHotel;
    private static final int INFO_NUM = 7;
    private static final int SIX = 6;
    private static final int HOTEL_RENT_INDEX = 5;


    public ClassicColorProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
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
        for(double num:getRentNumbers()){
            ret.add(num);
        }
        ret.add(getBuildingPrices().get(BuildingType.valueOf("HOUSE")));
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
        if(this.getIsMortgaged()){
            return 0.0;
        }
        double rentTotal = 0.0;
        numHotel = getNumBuilding(BuildingType.valueOf("HOTEL"));
        if(numHotel>0){
            rentTotal+= numHotel*getRentNumbers().get(HOTEL_RENT_INDEX);
        }
        else{
            rentTotal+= getRentNumbers().get(getNumBuilding(BuildingType.valueOf("HOUSE")));
        }

        return rentTotal;
    }

    public void addBuilding(BuildingType building){
        if(!getBuildingMap().containsKey(building)){
            getBuildingMap().put(building, 0);
        }
        getBuildingMap().put(building, getBuildingMap().get(building)+1);
        System.out.println(building);
        System.out.println(getBuildingMap().get(building));
    }

    public void removeBuilding(BuildingType building, int amount){
        if(getBuildingMap().get(building)>0){
            getBuildingMap().put(building, getBuildingMap().get(building)-amount);
        }
    }

    public int getNumBuilding(BuildingType building){
        if(!getBuildingMap().containsKey(building)){
            getBuildingMap().put(building, 0);
        }
        return getBuildingMap().get(building);
    }
}
