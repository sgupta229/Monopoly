package Model;

/***
 * This is an interface that will be implemented by Player, Bank, and any new classes we decide to add
 * that will deal with the transfers of money.
 */
public interface Transfer {

    /***
     * Allows a player/bank (or anyone that implements Transfer) to pay others
     * @param amount
     * @param receiver
     */
    public void makePayment(Bank bank, double amount, Transfer receiver);

    /***
     * Allows anyone that implements Transfer to receive a payment from another
     * @param amount
     */
    public void receivePayment(double amount);
}
