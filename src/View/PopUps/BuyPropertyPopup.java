//package View.PopUps;
//
//import Controller.Controller;
//import Model.properties.Property;
//import Model.spaces.AbstractSpace;
//import View.BoardConfigReader;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//public class BuyPropertyPopup extends Popup {
//
//    private Map<Integer, ArrayList> colorPropInfo;
//    private int propLocation;
//    private ArrayList propDetails;
//    private String name;
//    private Controller myController;
//    List<AbstractSpace> allSpaces;
//    List<Property> allProps;
//    private AbstractSpace mySpace;
//    private ResourceBundle myText;
//
//    public BuyPropertyPopup(int propLocation, Controller controller) {
//        super();
//        this.propLocation = propLocation;
//        this.myText = super.getMessages();
//        BoardConfigReader spaceInfo = new BoardConfigReader();
//        colorPropInfo = spaceInfo.getColorPropInfo();
//        allSpaces = spaceInfo.getSpaces();
//        allProps = spaceInfo.getProperties();
//        this.myController = controller;
//        for (AbstractSpace sp : allSpaces) {
//            if (sp.getMyLocation() == propLocation) {
//                mySpace = sp;
//            }
//        }
//    }
//
//    public BuyPropertyPopup(int propLocation) {
//        super();
//        this.propLocation = propLocation;
//        BoardConfigReader spaceInfo = new BoardConfigReader();
//        colorPropInfo = spaceInfo.getColorPropInfo();
//    }
//
//    @Override
//    protected Pane createImage(Scene scene, Stage popUpWindow) {
//        propDetails = new ArrayList();
//        Pane imagePane = new Pane();
//        Rectangle rectangle = new Rectangle(scene.getWidth() / 2.5, scene.getHeight() / 1.5);
//        rectangle.setFill(Color.WHITE);
//        rectangle.setStroke(Color.BLACK);
//        for (Map.Entry<Integer, ArrayList> key : colorPropInfo.entrySet()) {
//            if ((key.getKey()).equals(propLocation)) {
//                for (Object item : key.getValue()) {
//                    propDetails.add(item);
//                }
//            }
//        }
//
//        if (propLocation == 5 || propLocation == 15 || propLocation == 25 || propLocation == 35) {
//            var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myText.getString("buyRailroad")));
//            ImageView image = new ImageView(imageFile);
//            imagePane = new Pane(rectangle, image);
//            name = propDetails.get(6).toString().replace("_", " ");
//        } else if (propLocation == 12 || propLocation == 28) {
//            imagePane = new Pane(rectangle);
//            name = propDetails.get(4).toString().replace("_", " ");
//        } else {
//            Rectangle propColor = new Rectangle(scene.getWidth() / 2.5, scene.getHeight() / 7);
//            propColor.setStroke(Color.BLACK);
//            propColor.setFill(Color.web(propDetails.get(0).toString()));
//            imagePane = new Pane(rectangle, propColor);
//            name = propDetails.get(10).toString().replace("_", " ");
//
//        }
//        return new StackPane(imagePane, propertyInfo(scene));
//    }
//
//    @Override
//    protected String createMessage() {
//        return myText.getString("buyMessage");
//    }
//
//    @Override
//    protected String createTitle() {
//        return myText.getString("buyTitle");
//    }
//
//    @Override
//    protected Pane createButtons(Stage popUpWindow) {
//        HBox buttons = new HBox(HBoxSpacing);
//        Button button1 = new Button(myText.getString("yesButton"));
//        Button button2 = new Button(myText.getString("noButton"));
//        button1.setId("button2");
//        button2.setId("button2");
//        button2.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                Popup myPopup = new AuctionPopup(propLocation, name, myController, popUpWindow);
//                myPopup.display();
//            }
//        });
//
//        button1.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                mySpace.doAction(myController.getGame(), OK);
//                popUpWindow.close();
//            }
//        });
//        buttons.getChildren().addAll(button1, button2);
//
//        return buttons;
//    }
//
//
//    private FlowPane propertyInfo(Scene scene) {
//        FlowPane textPane = new FlowPane();
//        HBox priceProp = new HBox();
//        priceProp.setPrefWidth(scene.getWidth() / 2.5);
//        priceProp.setAlignment(Pos.CENTER);
//        Text rent;
//        Text mortgage;
//        Text rent1House;
//        Text rent2House;
//        Text rent3House;
//        if (name.toLowerCase().contains("railroad")) {
//            priceProp.getChildren().add(new Text(myText.getString("price") + propDetails.get(0)));
//            rent = new Text(myText.getString("rent") + propDetails.get(1));
//            rent1House = new Text(myText.getString("railroadRent2") + propDetails.get(2));
//            rent2House = new Text(myText.getString("railroadRent3") + propDetails.get(3));
//            rent3House = new Text(myText.getString("railroadRent4") + propDetails.get(4));
//            mortgage = new Text(myText.getString("mortgage") + propDetails.get(5));
//            textPane.setVgap(2);
//            textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, mortgage);
//        } else if (name.toLowerCase().contains("works") || name.toLowerCase().contains("company")) {
//            priceProp.getChildren().add(new Text(myText.getString("price") + propDetails.get(0)));
//            rent = new Text(myText.getString("oneUtility") + propDetails.get(1).toString().replace(".0", "") + "x dice");
//            rent2House = new Text(myText.getString("twoUtility") + propDetails.get(2).toString().replace(".0", "") + "x dice.");
//            mortgage = new Text(myText.getString("mortgage") + propDetails.get(3));
//            textPane.setVgap(3);
//            textPane.getChildren().addAll(priceProp, rent, rent2House, mortgage);
//        } else {
//            priceProp.getChildren().add(new Text(myText.getString("price") + propDetails.get(1)));
//            rent = new Text(myText.getString("rent") + propDetails.get(2));
//            rent1House = new Text(myText.getString("rent1House") + propDetails.get(3));
//            rent2House = new Text(myText.getString("rent2House") + propDetails.get(4));
//            rent3House = new Text(myText.getString("rent3House") + propDetails.get(5));
//            Text rent4Houses = new Text(myText.getString("rent4House") + propDetails.get(6));
//            Text rentHotel = new Text(myText.getString("rentHotel") + propDetails.get(7));
//            Text costHouse = new Text(myText.getString("costHouse") + propDetails.get(8));
//            mortgage = new Text(myText.getString("mortgage") + propDetails.get(9));
//            textPane.setVgap(2);
//            textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, rent4Houses, rentHotel, costHouse, mortgage);
//        }
//        textPane.setAlignment(Pos.BOTTOM_CENTER);
//        textPane.setPrefWrapLength(scene.getWidth() / 2.5);
//        textPane.setId("propPopUp");
//        textPane.setPadding(new Insets(OK, OK, HBoxSpacing, OK));
//        return textPane;
//    }
//
//
//    @Override
//    protected Label createHeader() {
//        return new Label(myText.getString("buyHeader")+ name + "!");
//    }
//}

package View.PopUps;

import Controller.Controller;
import Model.AbstractPlayer;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
import Model.spaces.SpaceGroup;
import View.BoardConfigReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    public BuyPropertyPopup(int propLocation, Controller controller) {
        super();
        this.propLocation = propLocation;
        this.myController = controller;
        BoardConfigReader spaceInfo = new BoardConfigReader(myController.getGame());
        allSpaces = spaceInfo.getSpaces();
        allProps = spaceInfo.getProperties();
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(2).toString());
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
        Pane imagePane = new Pane();
        Rectangle rectangle = new Rectangle(scene.getWidth() / 2.5, scene.getHeight() / 1.5);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        if (mySpace.getMyGroup().equals(SpaceGroup.RAILROAD)) {
            var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myText.getString("buyRailroad")));
            ImageView image = new ImageView(imageFile);
            imagePane = new Pane(rectangle, image);
        } else if (mySpace.getMyGroup().equals(SpaceGroup.UTILITY)) {
            imagePane = new Pane(rectangle);
        } else {
            Rectangle propColor = new Rectangle(scene.getWidth() / 2.5, scene.getHeight() / 7);
            propColor.setStroke(Color.BLACK);
            propColor.setFill(Color.web(myDetails.get(0).toString()));
            imagePane = new Pane(rectangle, propColor);
        }
        return new StackPane(imagePane, propertyInfo(scene));
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
        HBox buttons = new HBox(HBoxSpacing);
        Button button1 = new Button(myText.getString("yesButton"));
        Button button2 = new Button(myText.getString("noButton"));
        button1.setId("button2");
        button2.setId("button2");
        button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                List<AbstractPlayer> players = myController.getGame().getPlayers();
                AbstractPlayer currPlayer = myController.getGame().getCurrPlayer();
                AuctionPopup myPopup = new AuctionPopup(propLocation, name, myController, popUpWindow, players,currPlayer);
                myPopup.display();
            }
        });

        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mySpace.doAction(myController.getGame(), OK);
                popUpWindow.close();
            }
        });
        buttons.getChildren().addAll(button1, button2);

        return buttons;
    }


    private FlowPane propertyInfo(Scene scene) {
        FlowPane textPane = new FlowPane();
        HBox priceProp = new HBox();
        priceProp.setPrefWidth(scene.getWidth() / 2.5);
        priceProp.setAlignment(Pos.CENTER);
        Text rent;
        Text mortgage;
        Text rent1House;
        Text rent2House;
        Text rent3House;
        if (mySpace.getMyGroup().equals(SpaceGroup.RAILROAD)) {
            priceProp.getChildren().add(new Text(myText.getString("price") + myDetails.get(0)));
            rent = new Text(myText.getString("rent")+ myDetails.get(1));
            rent1House = new Text(myText.getString("railroadRent2") + myDetails.get(2));
            rent2House = new Text(myText.getString("railroadRent3") + myDetails.get(3));
            rent3House = new Text(myText.getString("railroadRent4") + myDetails.get(4));
            mortgage = new Text(myText.getString("mortgage") + myDetails.get(5));
            textPane.setVgap(2);
            textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, mortgage);
        } else if (mySpace.getMyGroup().equals(SpaceGroup.UTILITY)) {
            priceProp.getChildren().add(new Text(myText.getString("price") + myDetails.get(0)));
            rent = new Text(myText.getString("oneUtility") + myDetails.get(1).toString().replace(".0", "") + "x dice");
            rent2House = new Text(myText.getString("twoUtility") + myDetails.get(2).toString().replace(".0", "") + "x dice.");
            mortgage = new Text(myText.getString("mortgage") + myDetails.get(3));
            textPane.setVgap(3);
            textPane.getChildren().addAll(priceProp, rent, rent2House, mortgage);
        } else {
            priceProp.getChildren().add(new Text(myText.getString("price") + myDetails.get(1)));
            rent = new Text(myText.getString("rent") + myDetails.get(2));
            rent1House = new Text(myText.getString("rent1House") +myText.getString("building1") +": $" + myDetails.get(3));
            rent2House = new Text(myText.getString("rent2House") +myText.getString("building1") +"s: $" + myDetails.get(4));
            rent3House = new Text(myText.getString("rent3House") +myText.getString("building1") +"s: $" + myDetails.get(5));
            Text rent4Houses = new Text(myText.getString("rent4House") +myText.getString("building1") +"s: $" + myDetails.get(6));
            Text rentHotel = new Text(myText.getString("rentHotel") +myText.getString("building2") +": $" + myDetails.get(7));
            Text costHouse = new Text(myText.getString("costHouse") +myText.getString("building1") +": $" + myDetails.get(8));
            mortgage = new Text(myText.getString("mortgage") + myDetails.get(9));
            textPane.setVgap(2);
            textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, rent4Houses, rentHotel, costHouse, mortgage);
        }
        textPane.setAlignment(Pos.BOTTOM_CENTER);
        textPane.setPrefWrapLength(scene.getWidth() / 2.5);
        textPane.setId("propPopUp");
        textPane.setPadding(new Insets(OK, OK, HBoxSpacing, OK));
        return textPane;
    }

    @Override
    protected Label createHeader() {
        return new Label(myText.getString("buyHeader")+ name + "!");
    }


}
