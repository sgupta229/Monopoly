package Controller;

import Model.*;
import View.AddPlayersScreen;
import View.ChooseGameScreen;
import View.EndGameScreen;
import View.Layout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static final String TITLE = "Monopoly";
    public static final double HEIGHT = 700;
    public static final double WIDTH = 1200;

    private AbstractGame myGame;
    private String myGameConfigFile;
    private String gameStyle;
    transient private ObservableList<AbstractPlayer> newPlayers = FXCollections.observableArrayList();

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

    public void setGame(String gameConfigFile){
        myGameConfigFile = gameConfigFile;

        try{
            myGame = (AbstractGame) Class.forName("Controller.ClassicGame").getConstructor(String.class).newInstance(myGameConfigFile);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
//        } catch(XmlReaderException e){
//            String msg = e.getMessage();
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("XML Config");
//            alert.setHeaderText("XML Config File Error");
//            alert.setContentText(msg);
//            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //TODO: make gameFactory or use reflection to create concrete Game class based on gameType
        //gameType = ClassicGame, MegaGame
        // AbstractGame myGame = (AbstractGame) Class
        System.out.println(myGameConfigFile);
        //TODO
        gameStyle = fileToStylesheetString(new File("data/GUI.css"));
    }

    public void setGame(AbstractGame game){
        myGame = game;
    }

    public void goToAddPlayersScreen(){
        window.setScene(new AddPlayersScreen(WIDTH,HEIGHT,gameStyle,this,newPlayers).getScene());
    }

    public void startGame(){
        window.setScene(new Layout(WIDTH,HEIGHT,gameStyle,this,myGame).getScene());
    }

    public void endGame(AbstractPlayer winner) { window.setScene(new EndGameScreen(WIDTH,HEIGHT,gameStyle,winner,window).getScene());}

    public void initializePlayers(){
        myGame.setPlayers(newPlayers);
    }

    public Stage getStage(){
        return window;
    }

    public void addPlayer(String name, String image){
        //create player depending on game type
        AbstractPlayer newP;
        if (myGameConfigFile.equalsIgnoreCase("Classic")){
            newP = new ClassicPlayer(name,image);
        }
        else{
            newP = new ClassicPlayer(name,image);   //TODO
        }
        //add player to arraylist
        newPlayers.add(newP);
        // on startgame, initialize players in game with setPlayers
    }

    @Deprecated
    public ImageView getPlayerImageView(AbstractPlayer p){
        return null; //new ImageView(playersToImages.get(p));
    }

    //maybe should pass game directly in constructors? to make dependency clearer?
    public AbstractGame getGame(){return myGame;}
    public ObservableList<AbstractPlayer> getPlayers(){ return newPlayers;}

    private String fileToStylesheetString ( File stylesheetFile ) {
        try {
            return stylesheetFile.toURI().toURL().toString();
        } catch ( MalformedURLException e ) {
            return null;
        }
    }

    private ObservableList<Node> stringListToObservableList(List<String> strings){
        List<Node> nodes = new ArrayList<>();
        for (String s : strings) {
            nodes.add(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(s),
                    40,40,false,false)));
        }
        return FXCollections.observableList(nodes);
    }


}
