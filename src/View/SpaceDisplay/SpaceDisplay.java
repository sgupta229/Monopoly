package View.SpaceDisplay;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public abstract class SpaceDisplay {

    private String myBaseColor;
    protected String myImage;
    private StackPane myPropertyStackPane;
    private int myBoardHeight;


    public SpaceDisplay(String baseColor,String image,int boardDimen, int boardHeight) {
        this.myBaseColor = baseColor;
        this.myImage = image;
        myPropertyStackPane = new StackPane();
        this.myBoardHeight = boardHeight;
        myPropertyStackPane.getChildren().addAll(createProp(myBoardHeight/boardDimen,myBoardHeight/boardDimen));

    }

    public Pane createProp(int w, int h){
        Pane myImagePane = new Pane();
        Rectangle myRectangle = new Rectangle(w,h);
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
