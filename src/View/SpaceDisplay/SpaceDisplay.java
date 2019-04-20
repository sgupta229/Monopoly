package View.SpaceDisplay;

import View.Board;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class SpaceDisplay {

    protected String myBaseColor;
    protected String myImage;
    protected Rectangle myRectangle;
    protected StackPane myPropertyStackPane;
    protected Pane myImagePane;


    public SpaceDisplay(String baseColor,String image) {
        this.myBaseColor = baseColor;
        this.myImage = image;
        myPropertyStackPane = new StackPane();
        myPropertyStackPane.getChildren().addAll(createProp(Board.BOARD_HEIGHT/13,Board.BOARD_HEIGHT/13));

    }

    public Pane createProp(int w, int h){
        myImagePane = new Pane();
        myRectangle = new Rectangle(w,h);
        myRectangle.setFill(Color.web(this.myBaseColor));
        myRectangle.setStroke(Color.BLACK);
        ImageView image = createImage();
        image.setFitWidth(w);
        image.setFitHeight(h);
        myImagePane.getChildren().addAll(myRectangle,image);

        return myImagePane;
    }

    public abstract ImageView createImage();

    public abstract Text createText();

    public StackPane getMyPropertyStackPane() {
        return myPropertyStackPane;
    }
}
