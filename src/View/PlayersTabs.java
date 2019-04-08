package View;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PlayersTabs {
    private TabPane tabPane;

    public PlayersTabs(){
        tabPane = new TabPane();
        tabPane.setPrefWidth(Main.WIDTH * .4);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // create Play Tab
        Tab playTab = new Tab("Play"); //label "P1"
//        playTab.setContent(new ClassicPlayerControl().getPlayerControlView());

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
