package Model;

public class FreeParkingFunds implements Transfer {

    double myParkingMoney=0;

    @Override
    public void makePayment(double amount, Transfer receiver) {
        myParkingMoney -= myParkingMoney;
        receiver.receivePayment(myParkingMoney);
    }

    @Override
    public void receivePayment(double amount) {
        myParkingMoney+=amount;
    }
}
