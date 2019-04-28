package Controller;

import Model.*;
import View.AddPlayersScreen;
import View.ChooseGameScreen;
import View.EndGameScreen;
import View.Layout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private String className;
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

    public void setGame(String gameConfigFile) throws XmlReaderException{
        myGameConfigFile = gameConfigFile;
        try {
            ConfigReader cfr = new ConfigReader(myGameConfigFile);
            className = cfr.parseGameType();
        }
        catch (XmlReaderException e){
            throw e;
        }
        //BUG FIX MOVE THIS IN A TRY CATCH
            //ConfigReader cfr = new ConfigReader(myGameConfigFile);
            //className = cfr.parseGameType();

        try{
            //myGame = new ClassicGame(myGameConfigFile);
            myGame = (AbstractGame) Class.forName("Controller."+className+"Game").getConstructor(String.class).newInstance(myGameConfigFile);
            //BUG FIX, when added reflection, had to call parseXMLFile here instead of in game itself because wasn't seeing the thrown exception
            myGame.parseXMLFile(myGameConfigFile);
        } catch(XmlReaderException e){
            throw e;
        }
         catch (InstantiationException e) {
            throw new XmlReaderException("Instantiation error");
        } catch (IllegalAccessException e) {
            throw new XmlReaderException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new XmlReaderException("Error");
        } catch (NoSuchMethodException e) {
            throw new XmlReaderException("Method reflection not found");
        } catch (ClassNotFoundException e) {
            throw new XmlReaderException(className + " was not a valid class name... please check the data file's ActionCard 'type' attributes to ensure they match the class names");
            //e.printStackTrace();
        }

        //TODO: make gameFactory or use reflection to create concrete Game class based on gameType
        //gameType = ClassicGame, MegaGame
        // AbstractGame myGame = (AbstractGame) Class
        //System.out.println(myGameConfigFile);
        //TODO
        gameStyle = fileToStylesheetString(new File("data/GUI.css"));
    }

    public void setGame(AbstractGame game){
        myGame = game;
        gameStyle = fileToStylesheetString(new File("data/GUI.css"));
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

    public void addPlayer(String name, String image) throws XmlReaderException{
        //create player depending on game type
        AbstractPlayer newP;
        try{

            //myGame = new ClassicGame(myGameConfigFile);
            newP = (AbstractPlayer) Class.forName("Model."+className+"Player").getConstructor(String.class, String.class).newInstance(name, image);
            newPlayers.add(newP);
        }
        catch (InstantiationException e) {
            //throw new XmlReaderException("Instantiation error");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            //throw new XmlReaderException("Method reflection not found");
        } catch (ClassNotFoundException e) {
            throw new XmlReaderException(className + " was not a valid class name... please check the data file's ActionCard 'type' attributes to ensure they match the class names");
            //e.printStackTrace();
        }
        //add player to arraylist

        // on startgame, initialize players in game with setPlayers
    }

    @Deprecated
    public ImageView getPlayerImageView(AbstractPlayer p){
        return null; //new ImageView(playersToImages.get(p));
    }

    //maybe should pass game directly in constructors? to make dependency clearer?
    public AbstractGame getGame(){return myGame;}
//    public ObservableList<AbstractPlayer> getPlayers(){ return newPlayers;}

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
