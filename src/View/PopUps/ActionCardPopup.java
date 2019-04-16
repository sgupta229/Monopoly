package View.PopUps;

import Controller.ConfigReader;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

public class ActionCardPopup extends Popup {

    private List actionCards;
    private Map<Point2D.Double, AbstractSpace> myIndexToName;
    private List<AbstractSpace> spaces;
    private int propLocation;
    private String name;
    private String title;

//message needs to be myGame.getCurrActionCard().getMessage();
    //actionCard.doAction() when ok is pressed

    public ActionCardPopup(int propLocation){
        super();
        this.propLocation = propLocation;
        ConfigReader spaceInfo = new ConfigReader(BoardConfigReader.CONFIG_PATH);
        actionCards = spaceInfo.parseActionCards();
        BoardConfigReader indexToName = new BoardConfigReader();
        spaces = indexToName.getSpaces();
        myIndexToName = indexToName.getIndexToName();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        String myImage = "";
        for (AbstractSpace sp : spaces) {
            if (sp.getMyLocation() == propLocation) {
                name = sp.getMyName();
                if (name.equalsIgnoreCase("CHANCE")) {
                    myImage = "chance.png";
                } else if (name.equalsIgnoreCase("COMMUNITY_CHEST")) {
                    name.replace("_"," ");
                    myImage = "chest.png";
                }
            }
            title = name;
        }
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myImage));
        ImageView image = new ImageView(imageFile);
        Pane imagePane= new Pane(image);
        return imagePane;
    }

    @Override
    protected String createMessage() {
        String myMessage = "Need this from backend?";
        return myMessage;
    }

    @Override
    protected String createTitle() {
        String myTitle = "Pay Rent";
        return myTitle;
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(10);
        Button button1= new Button("OK");
        button1.setId("button1");
        button1.setOnAction(e -> window.close());
        buttons.getChildren().add(button1);
        return buttons;
    }

//    @Override
//    protected Scene setSizeOfPopup(BorderPane layout) {
//        return new Scene(layout, Controller.WIDTH/2, Controller.HEIGHT/2);
//    }

    @Override
    protected Label createHeader() {
        return new Label(title);
    }
}
