package View;

import Controller.AbstractGame;
import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import Model.spaces.SpaceGroup;
import View.PopUps.*;
import View.SpaceDisplay.PropertyDisplay;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
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
            myProperty = myAbstractSpace.getMyProp();
            System.out.println("NEW TURN: ");


            System.out.println("CHECKING IF " + myProperty.getName()+ " STILL MORTGAGED********** "+myProperty.getIsMortgaged() + " " + myProperty);
//            System.out.println("CHECKING IF STILL MORTGAGED********** "+myController.getGame().getCurrPlayer().getProperties() + " " + myProperty);
            for (Property pr: myController.getGame().getCurrPlayer().getProperties()){
                System.out.println("LOOK HERE***** "+ pr + " " + pr.getIsMortgaged());
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
            PropertyDisplay propSpace = (PropertyDisplay) Class.forName("View.SpaceDisplay." + boardEdge).getConstructor(String.class, String.class, String.class, String.class).newInstance(name, price, color, baseColor);
            myGridPane.add(propSpace.getMyPropStackPane(), (int) entry.getKey().getX(), (int) entry.getKey().getY());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTaxAndActionSpaces(String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry) {
            String image = "";
            if (entry.getValue().getMyGroup().equals(SpaceGroup.TAX)) { image = boardInfo.getString("taxSpace"); }
            else if (entry.getValue().getMyGroup().equals(SpaceGroup.ACTION) && entry.getValue().getMyName().equalsIgnoreCase(boardInfo.getString("actionCard1Name"))) { image = boardInfo.getString("actionCard1Image"); }
            else{ image = boardInfo.getString("actionCard2Image"); }
            createNonPropSpace(image,boardEdge,entry);
    }

        private void createNonPropSpace (String image,String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry) {
            try {
            PropertyDisplay propSpace = (PropertyDisplay) Class.forName("View.SpaceDisplay." + boardEdge).getConstructor(String.class, String.class).newInstance(baseColor, image);
            myGridPane.add(propSpace.getMyPropStackPane(), (int) entry.getKey().getX(), (int) entry.getKey().getY());
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        } catch(InvocationTargetException e){
            e.printStackTrace();
        } catch(NoSuchMethodException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    
    private void createCornerSpaces(String boardEdge, Map.Entry<Point2D.Double, AbstractSpace> entry){
        String image;
        if (entry.getValue().getMyGroup().equals(SpaceGroup.GO)) { image = boardInfo.getString("goSpace"); }
        else if (entry.getValue().getMyGroup().equals(SpaceGroup.JAIL)){ image = boardInfo.getString("jailSpace"); }
        else if (entry.getValue().getMyGroup().equals(SpaceGroup.FREE_PARKING)){ image = boardInfo.getString("freeParkingSpace"); }
        else{ image = boardInfo.getString("goToJailSpace"); }
        createNonPropSpace(image,boardEdge,entry);
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
        BoardConfigReader configs = new BoardConfigReader(myGame);
        indexToName = configs.getIndexToName();
        nameToColor = configs.getNameToColor();
        nameToPrice = configs.getNameToPrice();
        allSpaces = configs.getSpaces();
        myProps = new ArrayList(myController.getGame().getProperties());
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

    public String getBoardEdge(double x, double y){
        if (y == boardDimension-1) { return "BottomPropertyDisplay"; }
        if (x == boardDimension-1) { return "RightPropertyDisplay"; }
        if (y == 0) { return "TopPropertyDisplay"; }
        if (x == 0) { return "LeftPropertyDisplay"; }
        else{ return "CornerDisplay"; }
    }

}