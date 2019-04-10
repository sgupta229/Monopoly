package Controller;

import Model.*;
import View.AddPlayersScreen;
import View.ChooseGameScreen;
import View.Layout;
import View.SceneSetUp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static final String TITLE = "Monopoly";
    public static final double HEIGHT = 700;
    public static final double WIDTH = 1200;

    private AbstractGame myGame;
    private String myGameType;
    private String gameStyle;
    private ObservableList<AbstractPlayer> newPlayers = FXCollections.observableArrayList();
    private ObservableList<Image> availableTokens;

    private Stage window;

    public Controller(Stage s){
        window = s;
    }
    public void goToChooseGameScreen() {
        window.setTitle(TITLE);
        String style = fileToStylesheetString(new File("data/GUI.css"));
        window.setScene(new ChooseGameScreen(WIDTH,HEIGHT,style,this).getScene());
        window.show();
    }

    //TODO possibly should refactor into two methods
    public void setGameAndGoToAddPlayers(String gameType){
        Scene nextScene;
        myGameType = gameType;

        //TODO: make gameFactory or use reflection to create concrete Game class based on gameType

        if(myGameType.equalsIgnoreCase("classic")){
            myGame = new ClassicGame("Normal_Config.xml");
            gameStyle = fileToStylesheetString(new File("data/GUI.css"));
            System.out.println(myGame.getPossibleTokens());
            System.out.println(myGame.getBank());
            availableTokens = makeImagesFromStrings(myGame.getPossibleTokens());
            nextScene = new AddPlayersScreen(WIDTH,HEIGHT,gameStyle,this,newPlayers,availableTokens).getScene();
        }
        else{
            nextScene = new Scene(new Group(),WIDTH,HEIGHT);
        }

        window.setScene(nextScene);
    }
    public void startGame(){
        myGame.setPlayers(newPlayers);
        window.setScene(new Layout(WIDTH,HEIGHT,gameStyle,this).getScene());
    }

    public void addPlayer(){
        //add icon and name to map
        //create player depending on game type
        AbstractPlayer newP;
        if (myGameType.equalsIgnoreCase("classic")){
            newP = new ClassicPlayer();
        }
        else{
            newP = new ClassicPlayer();
        }
        //add player to arraylist
        newPlayers.add(newP);
        // on startgame, initialize players in game
    }

    private ObservableList<Image> makeImagesFromStrings(List<String> strings){
        List<Image> images = new ArrayList<>();
        for(String s:strings){
            images.add(new Image(this.getClass().getClassLoader().getResourceAsStream(s)));
        }
        return FXCollections.observableList(images);
    }

    private String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }
}
