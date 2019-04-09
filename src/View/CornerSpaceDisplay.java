package View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CornerSpaceDisplay extends SpaceDisplay {

    private Pane imagePane;

    public CornerSpaceDisplay(String propName, String propColor){
        super(propName, propColor);
    }

    @Override
    public Text createText(String propName, String price) {
        return null;
    }

    private Pane createCornerSpaces(int width, int height){
        super.createBaseRectangle(width, height);
        imagePane = super.getMyPropertyPane();
        myRectangle.setFill(Color.web(this.myPropColor));
        imagePane.getChildren().addAll(myRectangle);

        return imagePane;
    }


    public Pane createGoSpaces(int width, int height) {
        createCornerSpaces(width,height);
        var goSpace = new Image(this.getClass().getClassLoader().getResourceAsStream("go.png"));
        ImageView go = new ImageView(goSpace);
        go.setFitWidth(width);
        go.setFitHeight(height);
        imagePane.getChildren().addAll(go);
        return imagePane;
    }

    public Pane createFreeParkingSpaces(int width, int height) {
        createCornerSpaces(width,height);
        var parking = new Image(this.getClass().getClassLoader().getResourceAsStream("freeParking.png"));
        ImageView go = new ImageView(parking);
        go.setFitWidth(width);
        go.setFitHeight(height);
        imagePane.getChildren().addAll(go);
        return imagePane;
    }

    public Pane createGoToJailSpaces(int width, int height) {
        createCornerSpaces(width,height);
        var jail = new Image(this.getClass().getClassLoader().getResourceAsStream("goToJail.png"));
        ImageView go = new ImageView(jail);
        go.setFitWidth(width);
        go.setFitHeight(height);
        imagePane.getChildren().addAll(go);
        return imagePane;
    }

    public Pane createInJailSpaces(int width, int height) {
        createCornerSpaces(width,height);
        var jail = new Image(this.getClass().getClassLoader().getResourceAsStream("jail.png"));
        ImageView go = new ImageView(jail);
        go.setFitWidth(width);
        go.setFitHeight(height);
        imagePane.getChildren().addAll(go);
        return imagePane;
    }



}
