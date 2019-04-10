package View;

import Controller.Controller;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class PlayerTabs {
    private Controller myController;
    private TabPane tabPane;

    public PlayerTabs(Controller controller){
        myController = controller;

        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH * .4);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // create Play Tab
        Tab playTab = new Tab("Play"); //label "P1"
        playTab.setContent(new ClassicPlayerControl(myController).getPlayerControlView());

        Tab statsTab = new Tab("Stats");
        statsTab.setContent(createStats());

        Tab rulesTab = new Tab("Rules");
        rulesTab.setContent(createRules());

        // add tab
        tabPane.getTabs().addAll(playTab,statsTab,rulesTab);

//            tabPane.getSelectionModel().select(2); example of how to select the third tab
    }

    private Label createStats(){
        return new Label("stats");
    }
    private Label createRules(){
        return new Label("rules");
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
