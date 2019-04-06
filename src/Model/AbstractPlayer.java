package Model;

public class AbstractPlayer implements Transfer {
    private double money;

    @Override
    public void makePayment(float amount, Transfer receiver) {
        this.money = this.money - amount;
        receiver.receivePayment(amount);
    }

    @Override
    public void receivePayment(float amount) {
        this.money = this.money + amount;
    }


}
