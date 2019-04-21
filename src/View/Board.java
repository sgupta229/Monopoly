package View;

import Controller.*;
import Model.*;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import Model.spaces.ActionCardSpace;
import Model.spaces.ClassicPropSpace;
import Model.spaces.TaxSpace;
import View.PopUps.*;
import View.SpaceDisplay.*;
import View.SpaceDisplay.CornerDisplay;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Board implements PropertyChangeListener {
    //Todo: needs to be refactored but wanted to make it work, all data is read in from file

    public static final int BOARD_HEIGHT = 700;
    private Controller myController;
    private AbstractGame myGame;
    private GridPane myGridPane;
    private ImageView boardLogo;
    private Map<Point2D.Double, AbstractSpace> indexToName;
    private Map<String,String> nameToColor;
    private Map<String,Integer> nameToPrice;
    private List<Node> imagesOnBoard = new ArrayList<>();
    private List<Property> myProps;
    private List<AbstractSpace> allSpaces;
    private AbstractSpace myAbstractSpace;
    private Property myProperty;
    private ResourceBundle boardInfo;
    private int boardDimension;
    private String baseColor;

    public Board(Controller controller, AbstractGame myGame) {
        this.myController = controller;
        this.myGame = myGame;
        boardInfo = ResourceBundle.getBundle("classicBoardDisplay");
        boardDimension = Integer.parseInt(boardInfo.getString("dimension"));
        for (AbstractPlayer p : controller.getPlayers()) {
            p.addPropertyChangeListener("currentLocation",this);
        }

        myGridPane = new GridPane();
        myGridPane.setGridLinesVisible(true);
        setUpGridConstraints();
        setUpBoardConfig();
        baseColor = boardInfo.getString("baseColor");
        createSpaces(baseColor);
        addTokensToGo();
    }

    public void addTokenToIndex(int i, Node image){
        BoardConfigReader reader = new BoardConfigReader();
        Map<Integer, Point2D.Double> myPoint = reader.getIndexToCoord();
        myGridPane.add(image,(int)myPoint.get(i).getX(),(int)myPoint.get(i).getY());
        imagesOnBoard.add(image);
    }

    public void renderPlayers(){
        for (Node i : imagesOnBoard){
            myGridPane.getChildren().remove(i);
        }
        int playerLocation = 0;
        for (AbstractPlayer pl : myController.getPlayers()){
            addTokenToIndex(pl.getCurrentLocation(),new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(pl.getImage()),
                    40.0,40.0,false,true)));
        }
        Popup myPopup;
        playerLocation = myGame.getCurrPlayer().getCurrentLocation();
        //board -> get space at
        //space.generatePopup.displa


        if (playerLocation==2 || playerLocation==7 || playerLocation==17 || playerLocation==22 || playerLocation==33 || playerLocation==36){
            myPopup = new ActionCardPopup( playerLocation, myController);
        }
        else if (playerLocation==4 || playerLocation==38){
            myPopup = new TaxPopup(playerLocation,myController);
        }
        else if (playerLocation==0 || playerLocation==10 || playerLocation==20 || playerLocation==30){
            myPopup = new CornerPopup(playerLocation, myController);
        }
        else {

            for (AbstractSpace sp : allSpaces){
                if (sp.getMyLocation()==playerLocation){
                    myAbstractSpace = sp;
                }
            }
            for (Property p : myProps){
                if (myAbstractSpace.getMyName().equalsIgnoreCase(p.getName())){
                    myProperty = p;
                }
            }

            if (myController.getGame().getBank().propertyOwnedBy(myProperty)!= null && myController.getGame().getBank().propertyOwnedBy(myProperty)!=myGame.getCurrPlayer()){
                myPopup = new PayRentPopup(playerLocation, myController);
            }
            else if (myController.getGame().getBank().propertyOwnedBy(myProperty)!= null && myController.getGame().getBank().propertyOwnedBy(myProperty)==myGame.getCurrPlayer()) {
                myPopup = null;
            }
            else{
                myPopup = new BuyPropertyPopup(playerLocation, myController);
            }
        }
        if (myPopup!=null){
            myPopup.display();
        }
    }

    private void addTokensToGo(){
        for (AbstractPlayer p : myController.getPlayers()){
            Node img = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(p.getImage()),
                    40.0,40.0,false,true));
            addTokenToIndex(0,img);
            imagesOnBoard.add(img);
        }
    }

    private void createSpaces(String baseColor){
        for (Map.Entry<Point2D.Double, AbstractSpace> entry : indexToName.entrySet()) {
            String name = entry.getValue().getMyName().replace("_", " ");
            if (entry.getValue() instanceof ClassicPropSpace) {
                String price = nameToPrice.get(name).toString();
                String color = nameToColor.get(name);
                if (entry.getKey().getY() == boardDimension-1) {
                    BottomPropertyDisplay propSpaces = new BottomPropertyDisplay(name, price, color, baseColor);
                    myGridPane.add(propSpaces.getMyPropStackPane(), (int) entry.getKey().getX(), boardDimension-1);
                }
                if (entry.getKey().getX() == 0) {
                    LeftPropertyDisplay propSpaces = new LeftPropertyDisplay(name, price, color,baseColor);
                    myGridPane.add(propSpaces.getMyPropStackPane(), 0, (int) entry.getKey().getY());
                }
                if (entry.getKey().getY() == 0) {
                    TopPropertyDisplay propSpaces = new TopPropertyDisplay(name, price, color, baseColor);
                    myGridPane.add(propSpaces.getMyPropStackPane(), (int) entry.getKey().getX(), 0);
                }
                if (entry.getKey().getX() == boardDimension-1) {
                    RightPropertyDisplay propSpaces = new RightPropertyDisplay(name, price, color,baseColor);
                    myGridPane.add(propSpaces.getMyPropStackPane(), boardDimension-1, (int) entry.getKey().getY());
                }
            }
            if (entry.getValue() instanceof TaxSpace) {
                if (entry.getKey().getY() == boardDimension-1) {
                    BottomPropertyDisplay propSpaces = new BottomPropertyDisplay(baseColor, "tax.png");
                    myGridPane.add(propSpaces.getMyPropStackPane(), (int) entry.getKey().getX(), boardDimension-1);
                }
                if (entry.getKey().getX() == boardDimension-1) {
                    RightPropertyDisplay propSpaces = new RightPropertyDisplay(baseColor, "tax.png");
                    myGridPane.add(propSpaces.getMyPropStackPane(), boardDimension-1, (int) entry.getKey().getY());
                }
            }
            if (entry.getValue() instanceof ActionCardSpace) {
                String image;
                if (name.equals("COMMUNITY CHEST")) {
                    image = "chest.png";
                } else {
                    image = "chance.png";
                }
                if (entry.getKey().getY() == boardDimension-1) {
                    BottomPropertyDisplay propSpaces = new BottomPropertyDisplay(baseColor, image);
                    myGridPane.add(propSpaces.getMyPropStackPane(), (int) entry.getKey().getX(), boardDimension-1);
                }
                if (entry.getKey().getX() == boardDimension-1) {
                    RightPropertyDisplay propSpaces = new RightPropertyDisplay(baseColor, image);
                    myGridPane.add(propSpaces.getMyPropStackPane(), boardDimension-1, (int) entry.getKey().getY());
                }
                if (entry.getKey().getX() == 0) {
                    LeftPropertyDisplay propSpaces = new LeftPropertyDisplay(baseColor, image);
                    myGridPane.add(propSpaces.getMyPropStackPane(), 0, (int) entry.getKey().getY());
                }
                if (entry.getKey().getY() == 0) {
                    TopPropertyDisplay propSpaces = new TopPropertyDisplay(baseColor, image);
                    myGridPane.add(propSpaces.getMyPropStackPane(), (int) entry.getKey().getX(), 0);
                }
            } else {
               createCornerSpaces();
            }
        }
    }


    private void createCornerSpaces(){
        CornerDisplay goSpace = new CornerDisplay(baseColor, "go.png");
        myGridPane.add(goSpace.getMyPropertyStackPane(), boardDimension-1, boardDimension-1);
        CornerDisplay parkingSpace = new CornerDisplay(baseColor, "freeParking.png");
        myGridPane.add(parkingSpace.getMyPropertyStackPane(), 0, 0);
        CornerDisplay jailSpace = new CornerDisplay(baseColor,"jail.png");
        myGridPane.add(jailSpace.getMyPropertyStackPane(), 0, boardDimension-1);
        CornerDisplay goToJail = new CornerDisplay(baseColor, "goToJail.png");
        myGridPane.add(goToJail.getMyPropertyStackPane(), boardDimension-1, 0);
    }

    private void setUpGridConstraints(){
        final int numCols = boardDimension;
        final int numRows = boardDimension;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(BOARD_HEIGHT / numCols);
            myGridPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(BOARD_HEIGHT / numRows);
            myGridPane.getRowConstraints().add(rowConst);
        }
    }

    private void setUpBoardConfig(){
        BoardConfigReader configs = new BoardConfigReader();
        indexToName = configs.getIndexToName();
        nameToColor = configs.getNameToColor();
        nameToPrice = configs.getNameToPrice();
        allSpaces = configs.getSpaces();
        myProps = new ArrayList<>(myController.getGame().getBank().getUnOwnedProps());
    }

    public Pane getGridPane() { return myGridPane; }

    public ImageView getLogo() {
        var logo = new Image(this.getClass().getClassLoader().getResourceAsStream(boardInfo.getString("boardLogo")));
        boardLogo = new ImageView(logo);
        boardLogo.setFitWidth((705/ 13) * 9);
        boardLogo.setFitHeight((705 / 13) * 9);
        boardLogo.setId("boardLogo");
        return boardLogo;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) { renderPlayers(); }
}