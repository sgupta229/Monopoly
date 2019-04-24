package Model;

import java.io.Serializable;

public class FreeParkingFunds implements Transfer, Serializable {

    double myParkingMoney=0;

    @Override
    public void makePayment(Bank b, double amount, Transfer receiver) {
        myParkingMoney -= amount;
        System.out.println(myParkingMoney);
        receiver.receivePayment(myParkingMoney);
    }

    @Override
    public void receivePayment(double amount) {
        myParkingMoney+=amount;
    }
}
