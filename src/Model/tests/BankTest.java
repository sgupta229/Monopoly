package Model.tests;

import Controller.ClassicGame;
import Controller.ConfigReader;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BankTest {
    ConfigReader conf = new ConfigReader("Normal_Config.xml");
    ClassicGame gameClass;
    ClassicPlayer player1;
    ClassicPlayer player2;
    List<AbstractSpace> spaceList;
    List<Property> propsList;
    Bank gameBank;
    Property prop1;

    double startBalance = 10000;




    @BeforeEach
    void setUp() throws XmlTagException {
        gameClass = new ClassicGame("Normal_Config.xml");
        spaceList = conf.parseSpaces().get(0);
        propsList = conf.parseSpaces().get(1);
        player1 = new ClassicPlayer();
        player2 = new ClassicPlayer();
        gameBank = new Bank(startBalance, propsList);
        prop1 = propsList.get(0);

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
        double payAmount = 10001;
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
}
