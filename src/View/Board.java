package View;

import Controller.AbstractGame;
import Controller.Controller;
import Model.AbstractPlayer;
import Model.spaces.AbstractSpace;
import Model.spaces.SpaceGroup;
import View.PopUps.Popup;
import View.SpaceDisplay.PropertyDisplay;
import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Board implements PropertyChangeListener {
    protected static final double TOKEN_SPACING=40.0;
    protected static final int ZERO=0;

    private int myBoardHeight;
    private Controller myController;
    private AbstractGame myGame;
    private GridPane myGridPane;
    private Map<Point2D.Double, AbstractSpace> indexToName;
    private Map<String,String> nameToColor;
    private Map<String,Integer> nameToPrice;
    private List<Node> imagesOnBoard = new ArrayList<>();
    private ResourceBundle boardInfo;
    private int boardDimension;
    private String baseColor;
    private List myFiles;

    public Board(Controller controller, AbstractGame myGame) {
        this.myController = controller;
        this.myGame = myGame;
        setUpBoardConfig();
        boardInfo = ResourceBundle.getBundle(myFiles.get(1).toString());

        myBoardHeight = Integer.parseInt(boardInfo.getString("boardHeight"));
        boardDimension = Integer.parseInt(boardInfo.getString("dimension"));
        for (AbstractPlayer p : controller.getGame().getPlayers()) {
            p.addPropertyChangeListener("currentLocation",this);
        }

        myGridPane = new GridPane();
        myGridPane.setGridLinesVisible(true);
        setUpGridConstraints();
        baseColor = boardInfo.getString("baseColor");
        createSpaces();
        addTokensToGo();
    }

    public void addTokenToIndex(int i, Node image){
        BoardConfigReader reader = new BoardConfigReader(myGame);
        Map<Integer, Point2D.Double> myPoint = reader.getIndexToCoord();
        myGridPane.add(image,(int)myPoint.get(i).getX(),(int)myPoint.get(i).getY());
        imagesOnBoard.add(image);
    }

    public void renderPlayers(){
        for (Node i : imagesOnBoard){
            myGridPane.getChildren().remove(i);
        }
        int playerLocation = 0;
        for (AbstractPlayer pl : myController.getGame().getPlayers()){
            addTokenToIndex(pl.getCurrentLocation(),new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(pl.getImage()),
                    TOKEN_SPACING,TOKEN_SPACING,false,true)));
        }
        Popup myPopup;
        playerLocation = myGame.getCurrPlayer().getCurrentLocation();
        AbstractSpace playersSpace = myGame.getBoard().getSpaceAt(playerLocation);

        try {
            String popClass = playersSpace.getPopString(myController.getGame());
            System.out.println(popClass);
            if(popClass!=null){
                myPopup = (Popup) Class.forName("View.PopUps." + popClass+"Popup").getConstructor(int.class, Controller.class).newInstance(playerLocation,
                        myController);
                PauseTransition pause = new PauseTransition(Duration.seconds(0.75));
                pause.setOnFinished(event -> myPopup.display());
                pause.play();
            }
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }

    private void addTokensToGo(){
        for (AbstractPlayer p : myController.getGame().getPlayers()){
            Node img = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(p.getImage()),
                    TOKEN_SPACING,TOKEN_SPACING,false,true));
            addTokenToIndex(ZERO,img);
            imagesOnBoard.add(img);
        }
    }

    private void createSpaces() {
        for (Map.Entry<Point2D.Double, AbstractSpace> entry : indexToName.entrySet()) {
            String name = entry.getValue().getMyName().replace("_", " ");
            String boardEdge = this.getBoardEdge(entry.getKey().getX(), entry.getKey().getY());
            if (entry.getValue().getMyGroup().equals(SpaceGroup.COLOR) || entry.getValue().getMyGroup().equals(SpaceGroup.RAILROAD) || entry.getValue().getMyGroup().equals(SpaceGroup.UTILITY)) {
                createColorProp(name, boardEdge, entry);
            } else if (entry.getValue().getMyGroup().equals(SpaceGroup.TAX) || entry.getValue().getMyGroup().equals(SpaceGroup.ACTION)) {
                createTaxAndActionSpaces(boardEdge,entry);
            } else {
                createCornerSpaces(boardEdge,entry);
            }
        }
    }


    private void createColorProp(String name, String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry){
        String price = nameToPrice.get(name).toString();
        String color = nameToColor.get(name);
        try {
            PropertyDisplay propSpace = (PropertyDisplay) Class.forName("View.SpaceDisplay." + boardEdge).getConstructor(String.class, String.class, String.class, String.class, int.class, int.class).newInstance(name, price, color, baseColor, myBoardHeight, boardDimension);
            myGridPane.add(propSpace.getMyPropStackPane(), (int) entry.getKey().getX(), (int) entry.getKey().getY());
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }

    private void createTaxAndActionSpaces(String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry) {
            String image = "";
            if (entry.getValue().getMyGroup().equals(SpaceGroup.TAX)) {
                image = boardInfo.getString("taxSpace");
            }
            else if (entry.getValue().getMyGroup().equals(SpaceGroup.ACTION) && entry.getValue().getMyName().equalsIgnoreCase(boardInfo.getString("actionCard1Name"))) {
                image = boardInfo.getString("actionCard1Image");
            }
            else{
                image = boardInfo.getString("actionCard2Image");
            }
            createNonPropSpace(image,boardEdge,entry);
    }

        private void createNonPropSpace (String image,String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry) {
            try {
            PropertyDisplay propSpace = (PropertyDisplay) Class.forName("View.SpaceDisplay." + boardEdge).getConstructor(String.class, String.class, int.class, int.class).newInstance(baseColor, image, myBoardHeight, boardDimension);
            myGridPane.add(propSpace.getMyPropStackPane(), (int) entry.getKey().getX(), (int) entry.getKey().getY());
        } catch(InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException  e){
            e.printStackTrace();
        }
    }
    
    private void createCornerSpaces(String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry){
        String image;
        if (entry.getValue().getMyGroup().equals(SpaceGroup.GO)) {
            image = boardInfo.getString("goSpace");
        }
        else if (entry.getValue().getMyGroup().equals(SpaceGroup.JAIL)){
            image = boardInfo.getString("jailSpace");
        }
        else if (entry.getValue().getMyGroup().equals(SpaceGroup.FREE_PARKING)){
            image = boardInfo.getString("freeParkingSpace");
        }
        else{
            image = boardInfo.getString("goToJailSpace");
        }
        createNonPropSpace(image,boardEdge,entry);
}

    private void setUpGridConstraints(){
        final int numCols = boardDimension;
        final int numRows = boardDimension;
        for (int i = ZERO; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setMaxWidth(myBoardHeight / numCols);
            myGridPane.getColumnConstraints().add(colConst);
        }
        for (int i = ZERO; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setMaxHeight(myBoardHeight / numRows);
            myGridPane.getRowConstraints().add(rowConst);
        }
    }

    private void setUpBoardConfig(){
        BoardConfigReader configs = new BoardConfigReader(myGame);
        indexToName = configs.getIndexToName();
        nameToColor = configs.getNameToColor();
        nameToPrice = configs.getNameToPrice();
        myFiles = configs.getFiles();
    }

    public Pane getGridPane() { return myGridPane; }

    public ImageView getLogo() {
        var logo = new Image(this.getClass().getClassLoader().getResourceAsStream(boardInfo.getString("boardLogo")));
        ImageView boardLogo = new ImageView(logo);
        boardLogo.setFitWidth(((myBoardHeight)/ (boardDimension)) * (boardDimension-2));
        boardLogo.setFitHeight(((myBoardHeight)/ (boardDimension)) * (boardDimension-2));
        boardLogo.setId("boardLogo");
        return boardLogo;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { renderPlayers(); }

    public String getBoardEdge(double x, double y){
        if (y == boardDimension-1) {
            return "BottomPropertyDisplay";
        }
        if (x == boardDimension-1) {
            return "RightPropertyDisplay";
        }
        if (y == ZERO) {
            return "TopPropertyDisplay";
        }
        if (x == ZERO) {
            return "LeftPropertyDisplay";
        }
        else{
            return "CornerDisplay";
        }
    }

}