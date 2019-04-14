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
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {
    public static final String TITLE = "Monopoly";
    public static final double HEIGHT = 700;
    public static final double WIDTH = 1200;

    private AbstractGame myGame;
    private String myGameType;
    private String gameStyle;
    private ObservableList<AbstractPlayer> newPlayers = FXCollections.observableArrayList();
    private ObservableList<String> availableTokens;
    private Map<AbstractPlayer, Image> playersToImages = new HashMap<>();

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
            availableTokens = FXCollections.observableList(myGame.getPossibleTokens());
            nextScene = new AddPlayersScreen(WIDTH,HEIGHT,gameStyle,this,newPlayers,availableTokens).getScene();
        }
        else{
            nextScene = new Scene(new Group(),WIDTH,HEIGHT);
        }

        window.setScene(nextScene);
    }

    public void startGame(){
        myGame.setPlayers(newPlayers);
        window.setScene(new Layout(WIDTH,HEIGHT,gameStyle,this,myGame).getScene());
        System.out.println("current player:" + myGame.getCurrPlayer().getName());
    }

    public void addPlayer(String name, Image icon){
        //create player depending on game type
        AbstractPlayer newP;
        if (myGameType.equalsIgnoreCase("classic")){
            newP = new ClassicPlayer(name);
        }
        else{
            newP = new ClassicPlayer(name);
        }
        //add image to map
        playersToImages.put(newP,icon);
        //add player to arraylist
        newPlayers.add(newP);
        // on startgame, initialize players in game with setPlayers
    }

    public ImageView getPlayerImageView(AbstractPlayer p){
        return new ImageView(playersToImages.get(p));
    }

    //temporary for testing
    public AbstractGame getGame(){return myGame;}
    public ObservableList<AbstractPlayer> getPlayers(){ return newPlayers;}

    private String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }


}
