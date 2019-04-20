package View;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerTabs implements PropertyChangeListener {
    private Controller myController;
    private TabPane tabPane;
    private Tab playTab;
    private Tab statsTab;
    private Tab rulesTab;
    private StatsTab stats;
    private AbstractPlayer currPlayer;

    public PlayerTabs(Controller controller){
        myController = controller;
        myController.getGame().addPropertyChangeListener("currPlayer",this);

        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH * .6);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        currPlayer = controller.getGame().getCurrPlayer();

        // create Play Tab
        playTab = new Tab("Play");
        setPlayTab();

        statsTab = new Tab("Stats");
        setStatsTab();

        rulesTab = new Tab("Rules");
        setRulesTab();

        tabPane.getTabs().addAll(playTab,statsTab,rulesTab);

//            tabPane.getSelectionModel().select(2); example of how to select the third tab
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        currPlayer = (AbstractPlayer) evt.getNewValue();
        setPlayTab();
    }

    private void setPlayTab(){
        playTab.setContent(new ClassicPlayerControl(currPlayer,myController).getPlayerControlView());
    }
    private void setStatsTab(){
        statsTab.setContent(new StatsTab(myController.getGame().getBank()).getView());
    }
    private void setRulesTab(){
        rulesTab.setContent(new Label("rules"));
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
