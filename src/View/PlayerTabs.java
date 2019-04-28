package View;

import Controller.Controller;
import Controller.ClassicGame;
import Controller.SaveGame;
import Model.AbstractPlayer;
import Model.XmlReaderException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
    private Tab rulesTab;
    private Tab saveTab;
    private AbstractPlayer currPlayer;
    private ResourceBundle messages;
    private ResourceBundle display;

    public PlayerTabs(Controller controller){
        messages = ResourceBundle.getBundle("Messages");
        myController = controller;
        display = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(1).toString());

        myController.getGame().addPropertyChangeListener("currPlayer",this);

        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH * .6);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        currPlayer = controller.getGame().getCurrPlayer();

        // create Play Tab
        playTab = new Tab("Play");
        setPlayTab();

        rulesTab = new Tab("Rules");
        setRulesTab();

        saveTab = new Tab("Save");
        setSaveTab();

        tabPane.getTabs().addAll(playTab,rulesTab,saveTab);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        currPlayer = (AbstractPlayer) evt.getNewValue();
        setPlayTab();
    }

    private void setPlayTab(){
        if (display.getString("myPlayerTab").equals("JUNIOR")){
            playTab.setContent(new JuniorPlayerControl(currPlayer,myController).getPlayerControlView());
        }
        else{
            playTab.setContent(new ClassicPlayerControl(currPlayer,myController).getPlayerControlView());
        }
    }
    private void setRulesTab(){
        rulesTab.setContent(new RulesTab(myController.getGame()).getView());
    }
    private void setSaveTab(){
        Button saveGameButton = new Button("Save game for later");
        saveGameButton.setOnAction(new saveButtonHandler());
        saveTab.setContent(saveGameButton);
    }

    private class saveButtonHandler implements EventHandler<ActionEvent> {
        @Override
//        public void handle(ActionEvent event) {
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle(messages.getString("save-game"));
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("Serializable", "*.ser"));
//            fileChooser.setInitialFileName(".ser");
//            //TODO make generic
//            GameSaver<ClassicGame> mySaver = new GameSaver<ClassicGame>();
//            File openedFile = fileChooser.showSaveDialog(myController.getStage());
//            //TODO don't cast
//            mySaver.saveGame((ClassicGame)myController.getGame(),openedFile);
//        }

        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(messages.getString("save-game"));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Serializable","*.txt"));
            fileChooser.setInitialFileName(".txt");
            File openedFile = fileChooser.showSaveDialog(myController.getStage());
            SaveGame sg = new SaveGame();
            try {
                sg.save(myController.getGame(), openedFile.getAbsolutePath());
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
    }

    public TabPane getTabPane() {
        return tabPane;
    }
}

