package Model.Tests;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.*;
import Model.properties.Buildable;
import Model.properties.BuildingType;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class BankTest {
    ConfigReader conf = new ConfigReader("Normal_Config_Rework.xml");
    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    List<AbstractSpace> spaceList;
    List<Property> propsList;
    Bank gameBank;
    Property prop1;
    List<Map<BuildingType, Integer>> buildingInfo;
    List<Double> allInfo;

    double startBalance;




    @BeforeEach
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config_Rework.xml");
        spaceList = conf.parseSpaces().get(0);
        propsList = conf.parseSpaces().get(1);
        allInfo = conf.parseBank();
        buildingInfo = conf.getBuildingProperties();
        player1 = new ClassicPlayer();
        player2 = new ClassicPlayer();
        gameBank = new Bank(allInfo, propsList, buildingInfo);
        prop1 = propsList.get(0);
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
        double payAmount = 10;
        double newBal = startBalance-payAmount;
        gameBank.makePayment(payAmount, player1);
        assertEquals(gameBank.getBankBalance(), newBal);
        assertEquals(payAmount, player1.getFunds());
    }

    @Test
    public void makePaymentMoreThanBankHas() {
        double payAmount = startBalance*2;
        double newBal = 0;
        gameBank.makePayment(payAmount, player1);
        assertEquals(gameBank.getBankBalance(), newBal);
        assertEquals(startBalance, player1.getFunds());
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

    void buildHouse(){
        player1.receivePayment(1000);
        gameBank.setPropertyOwner(prop1, player1);
        gameBank.build((Buildable) prop1, BuildingType.valueOf("HOUSE"));
    }

    @Test
    void buildHouseIncreasesPropsHouseNum(){
        buildHouse();
        int numHouses = ((Buildable) prop1).getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedhouses = 1;
        assertEquals(expectedhouses, numHouses);
    }

    @Test
    void buildHousePlayerPays(){
        buildHouse();
        double expectedNewBal = 950;
        double actualNewBal = player1.getFunds();
        assertEquals(expectedNewBal, actualNewBal);
    }

    @Test
    void sellBackBuildingsDecreasesPropsHouseNum(){
        buildHouse();
        gameBank.sellBackBuildings((Buildable) prop1, BuildingType.valueOf("HOUSE"));
        int numHouses = ((Buildable) prop1).getNumBuilding(BuildingType.valueOf("HOUSE"));
        var expectedhouses = 0;
        assertEquals(expectedhouses, numHouses);
    }

    @Test
    void sellBackBuildingGetMoneyBack(){
        buildHouse();
        double playerOldFunds = player1.getFunds();
        gameBank.sellBackBuildings((Buildable) prop1, BuildingType.valueOf("HOUSE"));
        double playerNewFunds = player1.getFunds();
        assertEquals(playerOldFunds, playerNewFunds-25);
    }
}
