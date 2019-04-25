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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.ResourceBundle;

public class JuniorBuyPropertyPopup extends BuyPropertyPopup {

    private int propLocation;
    private String name;
    private Controller myController;
    List<AbstractSpace> allSpaces;
    List<Property> allProps;
    private AbstractSpace mySpace;
    private List myDetails;
    private ResourceBundle myText;

    public JuniorBuyPropertyPopup(int propLocation, Controller controller) {
        super(propLocation, controller);
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
                for (Object o : myDetails){
                    System.out.println(o.toString());
                }
            }
        }
    }

    public JuniorBuyPropertyPopup(int propLocation){
        super(propLocation);
    }

    private FlowPane propertyInfo(Scene scene) {
        FlowPane textPane = new FlowPane();
        HBox priceProp = new HBox();
        priceProp.setPrefWidth(scene.getWidth() / 2.5);
        priceProp.setAlignment(Pos.CENTER);
        Text price = new Text(myText.getString("price") + myDetails.get(1));
        Text rent = new Text(myText.getString("rent") + myDetails.get(2));
        priceProp.getChildren().add(price);
        textPane.setVgap(2);
        textPane.getChildren().addAll(priceProp, rent);
        textPane.setAlignment(Pos.CENTER);
        textPane.setLayoutY(scene.getHeight()/2);
        textPane.setPrefWrapLength(scene.getWidth() / 2.5);
        textPane.setId("propPopUp");
        textPane.setPadding(new Insets(OK, OK, HBoxSpacing, OK));
        return textPane;
    }


    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        return new StackPane(super.createPropImage(scene), propertyInfo(scene));
    }

    @Override
    protected String createMessage() {
        return super.createMessage();
    }

    @Override
    protected String createTitle() {
        return super.createTitle();
    }

    @Override
    protected Pane createButtons(Stage popUpWindow) {
        HBox buttons = new HBox(HBoxSpacing);
        Button button1 = new Button(myText.getString("yesButton"));
        button1.setId("button2");

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mySpace.doAction(myController.getGame(), OK);
                popUpWindow.close();
            }
        });
        buttons.getChildren().addAll(button1);

        return buttons;
    }


}
