package Model.properties;

import Model.AbstractPlayer;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/***
 * Author: Dylan Karul
 * Purpose: This class is used for all color properties in the classic game
 * Assumptions: This is under the assumption that it gets its data in the right form
 * but we make this assumption because we check for this in config reader when reading in
 * the data, so it always should be getting proper data.
 * Dependencies: It is dependent on AbstractPlayer,
 * the BuildingType enum class, ColorProperty, and Property.
 * How to use it: You would use this class by calling a new ClassicColorProperty to represent a
 * color property in the normal classic rules game. Then, to get the rent info and other metadata,
 * call getInfo. Call calculateRent to get the proper amount of rent based off the buildings on it. To build, call
 * the various build methods
 */

public class ClassicColorProperty extends ColorProperty {

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

    /***
     * returns an array with specific crucial info for this property, such as
     * price, color, the different rent numbers
     * @return
     */
    public List getInfo(){
        ArrayList ret = new ArrayList();
        ret.addAll(Arrays.asList(getColor(), this.getPrice()));
        for(double num:getRentNumbers()){
            ret.add(num);
        }
        List<BuildingType> bTypes = new ArrayList<>();
        bTypes.addAll(getBuildingPrices().keySet());
        ret.add(getBuildingPrices().get(bTypes.get(0)));
        ret.add(this.getMortgageAmount());
        ret.add(this.getName());
        return ret;
    }



    /***
     * A method that utilizes the member variables to calculate how
     * much it costs when someone lands on this property
     * @param propOwner the person that owns the property
     * @param lastDiceRoll
     * @return the total rent value to be paid
     */
    @Override
    public double calculateRent(AbstractPlayer propOwner, int lastDiceRoll){
        if(this.getIsMortgaged()){
            return 0.0;
        }
        double rentTotal = 0.0;
        int numHotel;
        List<BuildingType> buildingTypes = new ArrayList<>();
        buildingTypes.addAll(getBuildingPrices().keySet());
        for(int buildings=1; buildings<buildingTypes.size(); buildings++){
            numHotel = getNumBuilding(buildingTypes.get(buildings));
            if(numHotel>0){
                rentTotal+= numHotel*getRentNumbers().get(HOTEL_RENT_INDEX+(buildings-1)+(numHotel-1));
            }
        }
        if(rentTotal==0.0){
            rentTotal+= getRentNumbers().get(getNumBuilding(buildingTypes.get(0)));
        }



        return rentTotal;
    }

    /***
     * adds a specific building to the property, increases the building's count by 1
     * @param building the type of building we are erecting
     */
    public void addBuilding(BuildingType building){
        if(!getBuildingMap().containsKey(building)){
            getBuildingMap().put(building, 0);
        }
        getBuildingMap().put(building, getBuildingMap().get(building)+1);
        System.out.println(building);
        System.out.println(getBuildingMap().get(building));
    }

    /***
     * decrements the properties building count of this building by the specified amount
     * @param building the building we are gettting rid of
     * @param amount how many we are getting rid of
     */
    public void removeBuilding(BuildingType building, int amount){
        if(getBuildingMap().get(building)>0){
            getBuildingMap().put(building, getBuildingMap().get(building)-amount);
        }
    }

    /***
     * returns the number of the specified building a property currently has on it
     * @param building the building we want to know about
     */
    public int getNumBuilding(BuildingType building){
        if(!getBuildingMap().containsKey(building)){
            getBuildingMap().put(building, 0);
        }
        return getBuildingMap().get(building);
    }
}
