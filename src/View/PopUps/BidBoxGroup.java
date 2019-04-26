package View.PopUps;

import javafx.scene.control.ToggleButton;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class BidBoxGroup extends ToggleButton {
    private PropertyChangeSupport myPCS = new PropertyChangeSupport(this);

    private List<BidBox> myBids;
    private BidBox currentBid;
    private List<BidBox> outOfAuction;

    private int highestBid;
    private BidBox highestBidder;

    public BidBoxGroup(List<BidBox> list) {
        myBids = list;
        outOfAuction = new ArrayList<>();
    }
    public BidBoxGroup() {
        this(new ArrayList<>());
    }

    public void addBox(BidBox b){
        myBids.add(b);
    }

    public void startAuction(){
        highestBid = 0;
        disableAll();
        if(myBids.size() > 0){
            myBids.get(0).disable(false);
            currentBid = myBids.get(0);
        }
    }

    public void moveCurrentBidToNext(){
        currentBid.disable(true);
        int curr = myBids.indexOf(currentBid);
        int nextCurr;
        if (curr >= myBids.size()-1){
            nextCurr = 0;
        }
        else nextCurr = curr + 1;
        currentBid = myBids.get(nextCurr);
        if (outOfAuction.contains(currentBid)){
            if (outOfAuction.size() == myBids.size()) endAuction();
            else moveCurrentBidToNext();
        }
        currentBid.disable(false);
        currentBid.setInitalBidValue(getHighestBid() + Popup.BIDDING_STEP);
        if (myBids.size()-outOfAuction.size()<= 1 && highestBidder != null) {
            endAuction();
        }
    }

    public void respondToBid(int bid, BidBox bidder){
        if (bid > highestBid){
            highestBid = bid;
            highestBidder = bidder;
        }
        else {
            outOfAuction.add(bidder);
            bidder.disable(true);
        }
        for (BidBox b:myBids){
            b.setLowerBound(highestBid + Popup.BIDDING_STEP);
        }
        moveCurrentBidToNext();
    }

    public void respondToFold(BidBox b){
        b.disable(true);
        b.remove();
        outOfAuction.add(b);
        moveCurrentBidToNext();
    }

    public int getHighestBid(){
        return highestBid;
    }

    public BidBox getHighestBidder(){
        return highestBidder;
    }

    private void disableAll(){
        for (BidBox b: myBids){
            b.disable(true);
        }
    }

    private void endAuction(){
            myPCS.firePropertyChange("endAuction",false,true);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        myPCS.addPropertyChangeListener(propertyName,listener);
    }

}
