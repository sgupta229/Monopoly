package Model.properties;

import Model.AbstractPlayer;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class ColorProperty extends Property {

    //private int numHouse;
    private int numHotel;
    private List<Double> rentNumbers;
    private static final int INFO_NUM = 7;
    private static final int SIX = 6;
    private static final int HOTEL_RENT_INDEX = 5;


    public ColorProperty(double price, String propName, String color, List<Double> paymentInfo, int groupSize, Map<BuildingType, Double> buildingPriceMap){
        super(price, propName, color, paymentInfo, groupSize, buildingPriceMap);
        setGroup(color);
    }

    protected void initializePaymentInfo(List<Double> paymentInformation) throws IndexOutOfBoundsException{
        List<Double> paymentInformationCopy = paymentInformation;
        if(paymentInformationCopy.size()>=INFO_NUM)   {
            //pricePerHouse = paymentInformation.get(6);
            //pricePerHotel = paymentInformation.get(7);
            setMortgageAmount(paymentInformationCopy.get(SIX));
            rentNumbers = paymentInformationCopy.subList(0, SIX);
        }
        else{
            throw new IndexOutOfBoundsException("Bad data") ;
        }
    }

    public List getInfo(){
        ArrayList ret = new ArrayList();
        ret.addAll(Arrays.asList(myColor, this.getPrice()));
        for(double num:rentNumbers){
            ret.add(num);
        }
        ret.add(getBuildingPrices().get(BuildingType.valueOf("HOUSE")));
        ret.add(this.getMortgageAmount());
        ret.add(this.getName());
        return ret;
    }


    /***
     * Allows for users to see the color of a property which is crucial for monopolies
     * @return the color of this property
     */
    public String getPropertyColor(){
        return myColor;
    }

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
            rentTotal+= numHotel*rentNumbers.get(HOTEL_RENT_INDEX);
        }
        else{
            rentTotal+= rentNumbers.get(getNumBuilding(BuildingType.valueOf("HOUSE")));
        }
        return rentTotal;
    }

    public void addBuilding(BuildingType building){
        if(!getBuildingMap().containsKey(building)){
            getBuildingMap().put(building, 0);
        }
        getBuildingMap().put(building, getBuildingMap().get(building)+1);
        System.out.println(building );
        System.out.println(getBuildingMap().get(building));
    }

    public void removeBuilding(BuildingType building){
        if(getBuildingMap().get(building)>0){
            getBuildingMap().put(building, getBuildingMap().get(building)-1);
        }
    }

    public int getNumBuilding(BuildingType building){
        if(!getBuildingMap().containsKey(building)){
            getBuildingMap().put(building, 0);
        }
        return getBuildingMap().get(building);
    }
}
