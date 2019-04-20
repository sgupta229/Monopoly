package View;

import Controller.Controller;
import Controller.GameSaver;
import Controller.ClassicGame;
import Model.AbstractPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ResourceBundle;

public class PlayerTabs implements PropertyChangeListener {
    private Controller myController;
    private TabPane tabPane;
    private Tab playTab;
    private Tab statsTab;
    private Tab rulesTab;
    private Tab saveTab;
    private StatsTab stats;
    private AbstractPlayer currPlayer;

    private ResourceBundle messages;

    public PlayerTabs(Controller controller){
        messages = ResourceBundle.getBundle("Messages");
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

        saveTab = new Tab("Save");
        setSaveTab();

        tabPane.getTabs().addAll(playTab,statsTab,rulesTab,saveTab);

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
    private void setSaveTab(){
        Button saveGameButton = new Button("Save game for later");
        saveGameButton.setOnAction(new saveButtonHandler());
        saveTab.setContent(saveGameButton);
    }

    private class saveButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(messages.getString("save-game"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Serializable", "*.ser"));
            fileChooser.setInitialFileName(".ser");
            //TODO make generic
            GameSaver<ClassicGame> mySaver = new GameSaver<ClassicGame>();
            File openedFile = fileChooser.showSaveDialog(myController.getStage());
            //TODO don't cast
            mySaver.saveGame((ClassicGame)myController.getGame(),openedFile);
        }
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}
