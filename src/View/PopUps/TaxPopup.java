package View.PopUps;

import Controller.ConfigReader;
import Model.spaces.AbstractSpace;
import View.BoardConfigReader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaxPopup extends Popup {
    private List<AbstractSpace> spaces;
    private String name;
    private Integer index;
    private int propLocation;
    private Map<Integer, ArrayList> taxSpaces;


    public TaxPopup(int propLocation) {
        super();
        this.propLocation = propLocation;
        BoardConfigReader spaceInfo = new BoardConfigReader();
        spaces = spaceInfo.getSpaces();
        ConfigReader spaces = new ConfigReader(BoardConfigReader.CONFIG_PATH);
        taxSpaces = spaces.parseColorPropInfo();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        for (AbstractSpace sp : spaces) {
            if (sp.getMyLocation() == propLocation) {
                name = sp.getMyName();
                index = sp.getMyLocation();
            }
        }
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream("taxCard.png"));
        ImageView image = new ImageView(imageFile);
        Pane imagePane= new Pane(image);

        return imagePane;
    }

    @Override
    protected String createMessage() {
        String myMessage = "Time to pay your taxes!";
        return myMessage;
    }

    @Override
    protected String createTitle() {
        String myTitle = "Tax";
        return myTitle;
    }

    @Override
    protected Pane createButtons(Stage window) {
        VBox buttons = new VBox(10);
        ArrayList details = new ArrayList();

        Button button1= new Button("Pay $200");
        Button button2= new Button("Pay 10%");

        button1.setId("button3");
        button2.setId("button3");
        button1.setOnAction(e -> window.close());
        buttons.getChildren().addAll(button1,button2);
        buttons.setPadding(new Insets(0,0,50,0));
        return buttons;
    }

    @Override
    protected Label createHeader() {
        return new Label(name);
    }
}
