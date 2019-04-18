package View.PopUps;

import Controller.Controller;
import Model.properties.Property;
import Model.spaces.AbstractSpace;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyPropertyPopup extends Popup {

    private Map<Integer, ArrayList> colorPropInfo;
    private int propLocation;
    private ArrayList propDetails;
    private String name;
    private Controller myController;
    List<AbstractSpace> allSpaces;
    List<Property> allProps;
    private AbstractSpace mySpace;

    public BuyPropertyPopup(int propLocation, Controller controller) {
        super();
        this.propLocation = propLocation;
        BoardConfigReader spaceInfo = new BoardConfigReader();
        colorPropInfo = spaceInfo.getColorPropInfo();
        allSpaces = spaceInfo.getSpaces();
        allProps = spaceInfo.getProperties();
        this.myController = controller;
        for (AbstractSpace sp : allSpaces) {
            if (sp.getMyLocation() == propLocation) {
                mySpace = sp;
            }
        }

    }

    public BuyPropertyPopup(int propLocation) {
        super();
        this.propLocation = propLocation;
        BoardConfigReader spaceInfo = new BoardConfigReader();
        colorPropInfo = spaceInfo.getColorPropInfo();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        propDetails = new ArrayList();
        Pane imagePane = new Pane();
        Rectangle rectangle = new Rectangle(scene.getWidth() / 2.5, scene.getHeight() / 1.5);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        for (Map.Entry<Integer, ArrayList> key : colorPropInfo.entrySet()) {
            if ((key.getKey()).equals(propLocation)) {
                for (Object item : key.getValue()) {
                    propDetails.add(item);
                }
            }
        }

        if (propLocation == 5 || propLocation == 15 || propLocation == 25 || propLocation == 35) {
            var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream("railroad.png"));
            ImageView image = new ImageView(imageFile);
            imagePane = new Pane(rectangle, image);
            name = propDetails.get(6).toString().replace("_", " ");
        } else if (propLocation == 12 || propLocation == 28) {
            imagePane = new Pane(rectangle);
            name = propDetails.get(4).toString().replace("_", " ");
        } else {
            Rectangle propColor = new Rectangle(scene.getWidth() / 2.5, scene.getHeight() / 7);
            propColor.setStroke(Color.BLACK);
            propColor.setFill(Color.web(propDetails.get(0).toString()));
            imagePane = new Pane(rectangle, propColor);
            name = propDetails.get(10).toString().replace("_", " ");

        }
        return new StackPane(imagePane, propertyInfo(scene));
    }

    @Override
    protected String createMessage() {
        String myMessage = "Would you like to purchase this property?";
        return myMessage;
    }

    @Override
    protected String createTitle() {
        String myTitle = "Property";
        return myTitle;
    }

    @Override
    protected Pane createButtons(Stage popUpWindow) {
        HBox buttons = new HBox(HBoxSpacing);
        Button button1 = new Button("YES");
        Button button2 = new Button("NO");
        button1.setId("button2");
        button2.setId("button2");
        button2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Popup myPopup = new AuctionPopup(propLocation, name, myController, popUpWindow);
                myPopup.display();
            }
        });

        button1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                mySpace.doAction(myController.getGame(), OK);
                popUpWindow.close();
//                System.out.println("CURR PLAYER PROPS " + myController.getGame().getCurrPlayer().getProperties().toString());

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
        if (name.toLowerCase().contains("railroad")) {
            priceProp.getChildren().add(new Text("Price: $" + propDetails.get(0)));
            rent = new Text("Rent: $" + propDetails.get(1));
            rent1House = new Text("Rent if 2 owned: $" + propDetails.get(2));
            rent2House = new Text("Rent if 3 owned: $" + propDetails.get(3));
            rent3House = new Text("Rent if 4 owned: $" + propDetails.get(4));
            mortgage = new Text("Mortgage: $" + propDetails.get(5));
            textPane.setVgap(2);
            textPane.getChildren().addAll(priceProp, rent, rent1House, rent2House, rent3House, mortgage);
        } else if (name.toLowerCase().contains("works") || name.toLowerCase().contains("company")) {
            priceProp.getChildren().add(new Text("Price: $" + propDetails.get(0)));
            rent = new Text("If 1 owned, \nrent is " + propDetails.get(1).toString().replace(".0", "") + "x dice");
            rent2House = new Text("If both owned, \nrent is " + propDetails.get(2).toString().replace(".0", "") + "x dice.");
            mortgage = new Text("Mortgage: $" + propDetails.get(3));
            textPane.setVgap(3);
            textPane.getChildren().addAll(priceProp, rent, rent2House, mortgage);
        } else {
            priceProp.getChildren().add(new Text("Price: $" + propDetails.get(1)));
            rent = new Text("Rent: $" + propDetails.get(2));
            rent1House = new Text("Rent w/ 1 House: $" + propDetails.get(3));
            rent2House = new Text("Rent w/ 2 Houses: $" + propDetails.get(4));
            rent3House = new Text("Rent w/ 3 Houses: $" + propDetails.get(5));
            Text rent4Houses = new Text("Rent w/ 4 Houses: $" + propDetails.get(6));
            Text rentHotel = new Text("Rent w/ Hotel: $" + propDetails.get(7));
            Text costHouse = new Text("Cost of 1 House: $" + propDetails.get(8));
            mortgage = new Text("Mortgage: $" + propDetails.get(9));
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
        Label title = new Label("Welcome to " + name + "!");
        return title;
    }


}