package Model;

public class JuniorPlayer extends AbstractPlayer {

    public JuniorPlayer(String name, String image) {
        super(name,image);
    }

    public JuniorPlayer() {
        super();
    }

    @Override
    public void makePayment(double amount, Transfer receiver, Bank bank){
        if(amount>this.getFunds()){
            this.setCantPayBool(true);
            receiver.receivePayment(this.getFunds());
            this.setFunds(0);
        }
        else{
            receiver.receivePayment(amount);
            this.setFunds(this.getFunds()-amount);
        }
    }

}
