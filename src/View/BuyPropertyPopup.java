package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BuyPropertyPopup extends Popup {

    public BuyPropertyPopup(String title, String message) {
        super(title, message);
    }

    @Override
    protected Pane createImage(Scene scene, String property) {
        Rectangle rectangle = new Rectangle(scene.getWidth()/2.5, scene.getHeight()/1.5);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        Rectangle propColor = new Rectangle(scene.getWidth()/2.5, scene.getHeight()/7);
        propColor.setStroke(Color.BLACK);
        propColor.setFill(Color.ALICEBLUE);
        Pane imagePane = new Pane( rectangle, propColor );
        StackPane fullImage = new StackPane(imagePane, propertyInfo(scene));

        return fullImage;
    }

    private FlowPane propertyInfo(Scene scene){
        FlowPane textPane = new FlowPane();

        Text name = new Text("Connecticut Avenue");
        name.setUnderline(true);

        Text rent = new Text("Rent: $$$");
        Text rent1House = new Text("Rent w/ 1 House: $$$");
        Text rent2House = new Text("Rent w/ 2 Houses: $$$");
        Text rent3House = new Text("Rent w/ 3 Houses: $$$");
        Text rent4Houses = new Text("Rent w/ 4 Houses: $$$");
        Text mortgage = new Text("Mortgage: $$$");
        Text costHouse = new Text("Cost of 1 House: $$$");
        Text costhotel = new Text("Cost of 1 Hotel: $$$");

        textPane.getChildren().addAll(name,rent,rent1House,rent2House,rent3House,rent4Houses,mortgage,costHouse,costhotel);
        textPane.setPrefWrapLength(scene.getWidth()/2.5);
        textPane.setAlignment(Pos.BOTTOM_CENTER);
        textPane.setVgap(1);
        textPane.setPadding(new Insets(0,0,10,0));
        return textPane;
    }


    @Override
    protected Label createHeader(Scene scene, String properyName) {
        Label title = new Label("Welcome to " + properyName + "!");
        return title;
    }


}
