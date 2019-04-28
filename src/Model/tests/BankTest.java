package Model.Tests;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.*;
import Model.properties.BuildingType;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class BankTest {
    ConfigReader conf;

    {
        try {
            conf = new ConfigReader("Normal_Config_Rework.xml");
        } catch (XmlReaderException e) {
            e.printStackTrace();
        }
    }

    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    List<AbstractSpace> spaceList;
    List<Property> propsList;
    Bank gameBank;
    Property prop1;
    Property prop2;
    List<Map<BuildingType, Integer>> buildingInfo;
    List<Double> allInfo;

    double startBalance;


    @BeforeEach
    void setUp() throws XmlReaderException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        gameClass.parseXMLFile("Normal_Config_Rework.xml");
        spaceList = gameClass.getSpaces();
        //spaceList = conf.parseSpaces().get(0);
        propsList = gameClass.getProperties();
        //propsList = conf.parseSpaces().get(1);
        //allInfo = conf.parseBank();
        //buildingInfo = conf.getBuildingProperties();
        player1 = new ClassicPlayer();
        player2 = new ClassicPlayer();
        player1.setFunds(1000);
        player2.setFunds(1000);
        gameBank = gameClass.getBank();
        //gameBank = new Bank(allInfo, propsList, buildingInfo);
        prop1 = propsList.get(0);
        prop2 = propsList.get(1);
        startBalance = gameBank.getBankBalance();

    }


    @Test
    public void propertyOwnedByPlayerOne() {
        gameBank.setPropertyOwner(prop1, player1);
        AbstractPlayer prop1Owner = gameBank.propertyOwnedBy(prop1);
        assertEquals(player1, prop1Owner);
    }

    @Test
    public void propertyOwnedByNoOne() {
        AbstractPlayer prop1Owner = gameBank.propertyOwnedBy(prop1);
        assertEquals(null, prop1Owner);
    }

    @Test
    public void setPropertyOwnerChangeOwner() {
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.setPropertyOwner(prop1, player2);
        AbstractPlayer prop1Owner = gameBank.propertyOwnedBy(prop1);
        assertEquals(player2, prop1Owner);
    }

    @Test
    public void setPropertyOwnerFirstTime() {
        gameBank.setPropertyOwner(prop1, player1);
        AbstractPlayer prop1Owner = gameBank.propertyOwnedBy(prop1);
        assertEquals(player1, prop1Owner);
    }

    @Test
    public void makePaymentNormal() {
        double payAmount = 3010;
        double newBal = startBalance-payAmount;
        gameBank.makePayment(gameBank, payAmount, player1);
        assertEquals(gameBank.getBankBalance(), newBal);
        assertEquals(payAmount+1000, player1.getFunds());
    }

    @Test
    public void makePaymentMoreThanBankHas() {
        double payAmount = startBalance*2;
        double newBal = 0;
        gameBank.makePayment(gameBank, payAmount, player1);
        assertEquals(gameBank.getBankBalance(), newBal);
        assertEquals(startBalance+1000, player1.getFunds());
    }

    @Test
    public void receivePayment() {
        double payAmount = 10;
        double newBankBal = startBalance+payAmount;
        gameBank.receivePayment(payAmount);
        assertEquals(newBankBal, gameBank.getBankBalance());
    }

    @Test
    void mortgagePropFixesMortgagedBoolean(){
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.mortgageProperty(prop1);
        assertEquals(true, prop1.getIsMortgaged());
    }

    @Test
    void mortgagePropPaysPlayer(){
        gameBank.setPropertyOwner(prop1, player1);
        double mortAmount = 30;
        double playerFunds = player1.getFunds()+mortAmount;
        gameBank.mortgageProperty(prop1);
        double bankNewFunds = startBalance-mortAmount;
        assertEquals(bankNewFunds, gameBank.getBankBalance());
        assertEquals(playerFunds, player1.getFunds());
    }

    @Test
    void mortgagePropNoRent(){
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.mortgageProperty(prop1);
        var rent = prop1.calculateRent(player2, 3);
        var expectedRent = 0.0;
        assertEquals(expectedRent, rent);
    }

    @Test
    void cantMortgageBecauseBuildingOnProp(){
        gameBank.setPropertyOwner(prop1, player1);
        buildHouse(gameBank.getBuildingTypes().get(0));
        var canMort = gameBank.checkIfCanMortgage(prop1);
        var expectedCanMort = false;
        assertEquals(expectedCanMort, canMort);
    }

    @Test
    void cantMortgageBecauseAlreadyMortgaged(){
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.mortgageProperty(prop1);
        var canMort = gameBank.checkIfCanMortgage(prop1);
        var expectedCanMort = false;
        assertEquals(expectedCanMort, canMort);
    }

    @Test
    void tryToUnMortgageNotMortgaged(){
        var playerOriginalFunds = player1.getFunds();
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.unMortgageProperty(prop1);
        var playerNewFunds = player1.getFunds();
        assertEquals(playerOriginalFunds, playerNewFunds);
    }

    @Test
    void cantBuildHotelBecauseNotEnoughHousesYet(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        var canBuild = gameBank.checkIfCanBuild(prop1, gameBank.getBuildingTypes().get(1));
        var expectedCanBuild = false;
        assertEquals(expectedCanBuild, canBuild);
    }

    @Test
    void cantBuildHouseBecauseAlreadyBuiltHotel(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        gameBank.build(prop2, gameBank.getBuildingTypes().get(0));
        for(int i=0; i<3; i++){
            gameBank.build(prop2, gameBank.getBuildingTypes().get(0));
            gameBank.build(prop1, gameBank.getBuildingTypes().get(0));
        }
        gameBank.build(prop1, gameBank.getBuildingTypes().get(1));
        var canBuild = gameBank.checkIfCanBuild(prop1, gameBank.getBuildingTypes().get(0));
        var expectedCanBuild = false;
        assertEquals(expectedCanBuild, canBuild);
    }

    @Test
    void cantBuildHotelBecauseAlreadyBuiltMaxHotel(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        gameBank.build(prop2, gameBank.getBuildingTypes().get(0));
        for(int i=0; i<3; i++){
            gameBank.build(prop2, gameBank.getBuildingTypes().get(0));
            gameBank.build(prop1, gameBank.getBuildingTypes().get(0));
        }
        gameBank.build(prop1, gameBank.getBuildingTypes().get(1));
        var canBuild = gameBank.checkIfCanBuild(prop1, gameBank.getBuildingTypes().get(1));
        var expectedCanBuild = false;
        assertEquals(expectedCanBuild, canBuild);
    }

    @Test
    void canBuildIsAllowed(){
        player1.addProperty(prop1);
        player1.addProperty(prop2);
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.setPropertyOwner(prop2, player1);

        var canBuild = gameBank.checkIfCanBuild(prop1, gameBank.getBuildingTypes().get(0));
        var expectedCanBuild = true;
        assertEquals(canBuild, expectedCanBuild);
    }

    @Test
    void unMortgagePropertyPlayerPays(){
        player1.setFunds(1000);
        double playerOriginalFunds = player1.getFunds();
        double mortAmount = prop1.getMortgageAmount();
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.mortgageProperty(prop1);
        gameBank.unMortgageProperty(prop1);
        var playerNewFunds = player1.getFunds();
        var expectedNewFunds = playerOriginalFunds-mortAmount*.1;
        assertEquals(expectedNewFunds, playerNewFunds);
    }

    void buildHouse(BuildingType btype){
        player1.receivePayment(1000);
        player1.addProperty(prop1);
        player1.addProperty(prop2);
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.setPropertyOwner(prop2, player1);
        gameBank.build(prop1, btype);
    }

    @Test
    void buildHouseIncreasesPropsHouseNum(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        int numHouses = prop1.getNumBuilding(gameBank.getBuildingTypes().get(0));
        var expectedhouses = 1;
        assertEquals(expectedhouses, numHouses);
    }

    @Test
    void buildHousePlayerPays(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        double expectedNewBal = 1950;
        double actualNewBal = player1.getFunds();
        assertEquals(expectedNewBal, actualNewBal);
    }

    @Test
    void sellBackBuildingsDecreasesPropsHouseNum(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        gameBank.sellBackBuildings(prop1, gameBank.getBuildingTypes().get(0));
        int numHouses = prop1.getNumBuilding(gameBank.getBuildingTypes().get(0));
        var expectedhouses = 0;
        assertEquals(expectedhouses, numHouses);
    }

    @Test
    void sellBackBuildingGetMoneyBack(){
        buildHouse(gameBank.getBuildingTypes().get(0));
        double playerOldFunds = player1.getFunds();
        gameBank.sellBackBuildings(prop1, gameBank.getBuildingTypes().get(0));
        double playerNewFunds = player1.getFunds();
        assertEquals(playerOldFunds, playerNewFunds-25);
    }
}
