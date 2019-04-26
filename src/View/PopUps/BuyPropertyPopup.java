package View.PopUps;

import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import Model.spaces.SpaceGroup;
import View.BoardConfigReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.ResourceBundle;

public class BuyPropertyPopup extends Popup {

    private int propLocation;
    private String name;
    private Controller myController;
    List<AbstractSpace> allSpaces;
    List<Property> allProps;
    private AbstractSpace mySpace;
    private List myDetails;
    private ResourceBundle myText;
    private int myPrice = 0;
    private int myRent = 1;
    FlowPane textPane;
    HBox priceProp;

    public BuyPropertyPopup(int propLocation, Controller controller) {
        super();
        this.propLocation = propLocation;
        this.myController = controller;
        BoardConfigReader spaceInfo = new BoardConfigReader(myController.getGame());
        allSpaces = spaceInfo.getSpaces();
        allProps = spaceInfo.getProperties();
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(POPUP_TEXT).toString());
        for (AbstractSpace sp : allSpaces) {
            if (sp.getMyLocation() == propLocation) {
                mySpace = sp;
                name = mySpace.getMyName();
                myDetails = sp.getInfo();
            }
        }
    }

    public BuyPropertyPopup(int propLocation) {
        this.propLocation = propLocation;
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        return new StackPane(createPropImage(scene), propertyInfo(scene));
    }


    protected void setTextPane(Scene scene){
        textPane= new FlowPane();
        priceProp = new HBox();
        priceProp.setPrefWidth(scene.getWidth() / IMAGE_WIDTH_SPACING);
        priceProp.setAlignment(Pos.CENTER);
        textPane.setPrefWrapLength(scene.getWidth() / IMAGE_WIDTH_SPACING);
        textPane.setId("propPopUp");
        textPane.setPadding(new Insets(OK, OK, HBOX_SPACING_TEN, OK));
    }

    protected Pane createPropImage(Scene scene){
        Pane imagePane = new Pane();
        Rectangle rectangle = new Rectangle(scene.getWidth() / IMAGE_WIDTH_SPACING, scene.getHeight() / IMAGE_HEIGHT_SPACING);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        if (mySpace.getMyGroup().equals(SpaceGroup.RAILROAD)) {
            var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myText.getString("buyRailroad")));
            ImageView image = new ImageView(imageFile);
            imagePane = new Pane(rectangle, image);
        } else if (mySpace.getMyGroup().equals(SpaceGroup.UTILITY)) {
            imagePane = new Pane(rectangle);
        } else {
            Rectangle propColor = new Rectangle(scene.getWidth() / IMAGE_WIDTH_SPACING, scene.getHeight() / PROP_COLOR_SPACING);
            propColor.setStroke(Color.BLACK);
            propColor.setFill(Color.web(myDetails.get(0).toString()));
            imagePane = new Pane(rectangle, propColor);
        }
        return imagePane;
    }

    @Override
    protected String createMessage() {
        return myText.getString("buyMessage");
    }

    @Override
    protected String createTitle() {
        return myText.getString("buyTitle");
    }

    @Override
    protected Pane createButtons(Stage popUpWindow) {
        HBox buttons = new HBox(HBOX_SPACING_TEN);
        Button button1 = new Button(myText.getString("yesButton"));
        Button button2 = new Button(myText.getString("noButton"));
        button1.setId("button2");
        button2.setId("button2");
        button2.setOnAction(e -> {
            List<AbstractPlayer> players = myController.getGame().getPlayers();
            AbstractPlayer currPlayer = myController.getGame().getCurrPlayer();
            AuctionPopup myPopup = new AuctionPopup(propLocation, name, myController, popUpWindow, players,currPlayer);
            myPopup.display();
        });
        button1.setOnAction(e -> {
            mySpace.doAction(myController.getGame(), OK);
            popUpWindow.close();
        });
        buttons.getChildren().addAll(button1, button2);
        return buttons;
    }

    private FlowPane propertyInfo(Scene scene) {
        setTextPane(scene);
        if (mySpace.getMyGroup().equals(SpaceGroup.RAILROAD)) {
            createRailRoadInfo(priceProp, textPane);
        } else if (mySpace.getMyGroup().equals(SpaceGroup.UTILITY)) {
            createUtilityInfo(priceProp,textPane);
        } else {
            createColorProp(priceProp,textPane);
        }
        textPane.setVgap(V_BOX_SPACING);
        textPane.setAlignment(Pos.BOTTOM_CENTER);
        return textPane;
    }

    private void createRailRoadInfo(HBox priceProp, FlowPane textPane){
        priceProp.getChildren().add(new Text(myText.getString("price") + myDetails.get(myPrice)));
        Text rent = new Text(myText.getString("rent")+ myDetails.get(myRent));
        Text rent1House = new Text(myText.getString("railroadRent2") + myDetails.get(2));
        Text rent2House = new Text(myText.getString("railroadRent3") + myDetails.get(3));
        Text rent3House = new Text(myText.getString("railroadRent4") + myDetails.get(4));
        Text mortgage = new Text(myText.getString("mortgage") + myDetails.get(5));
        textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, mortgage);
    }

    private void createUtilityInfo(HBox priceProp, FlowPane textPane){
        priceProp.getChildren().add(new Text(myText.getString("price") + myDetails.get(myPrice)));
        Text rent = new Text(myText.getString("oneUtility") + myDetails.get(myRent).toString().replace(".0", "") + "x dice");
        Text rent2House = new Text(myText.getString("twoUtility") + myDetails.get(2).toString().replace(".0", "") + "x dice.");
        Text mortgage = new Text(myText.getString("mortgage") + myDetails.get(3));
        textPane.getChildren().addAll(priceProp, rent, rent2House, mortgage);
    }

    private void createColorProp(HBox priceProp, FlowPane textPane){
        priceProp.getChildren().add(new Text(myText.getString("price") + myDetails.get(Integer.parseInt(myText.getString("priceNum")))));
        Text rent = new Text(myText.getString("rent") + myDetails.get(Integer.parseInt(myText.getString("rentNum"))));
        Text rent1House = new Text(myText.getString("rent1House") +myText.getString("building1") +": $" + myDetails.get(Integer.parseInt(myText.getString("rent1"))));
        Text rent2House = new Text(myText.getString("rent2House") +myText.getString("building1") +"s: $" + myDetails.get(Integer.parseInt(myText.getString("rent2"))));
        Text rent3House = new Text(myText.getString("rent3House") +myText.getString("building1") +"s: $" + myDetails.get(Integer.parseInt(myText.getString("rent3"))));
        Text rent4Houses = new Text(myText.getString("rent4House") +myText.getString("building1") +"s: $" + myDetails.get(Integer.parseInt(myText.getString("rent4"))));
        Text rentHotel = new Text(myText.getString("rentHotel") +myText.getString("building2") +": $" + myDetails.get(Integer.parseInt(myText.getString("hotelRent"))));
        Text costHouse = new Text(myText.getString("costHouse") +myText.getString("building1") +": $" + myDetails.get(Integer.parseInt(myText.getString("houseCost"))));
        Text mortgage = new Text(myText.getString("mortgage") + myDetails.get(Integer.parseInt(myText.getString("mortgageNum"))));
        textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, rent4Houses, rentHotel, costHouse, mortgage);
    }

    @Override
    protected Label createHeader() {
        return new Label(myText.getString("buyHeader")+ name + "!");
    }


}
