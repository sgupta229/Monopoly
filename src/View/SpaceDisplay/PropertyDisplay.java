package View.SpaceDisplay;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public abstract class PropertyDisplay {

    public static final int SPACING=10;
    public static final int ROTATION_90 = 90;
    public static final int ROTATION_180 = 180;
    public static final int NO_ROTATION = 0;
    public static final int PROP_SIZE = 4;

    private Pane myPropertyPane;
    protected Pane myPropStackPane;
    private Pane myImagePane;

    private String myPropName;
    private String myPropPrice;
    private String myImage;
    private String myPropColor;
    private String myBaseColor;
    private HBox propColor;
    protected Rectangle myRectangle;
    protected int myBoardDimen;
    protected int myBoardHeight;
    private int textPadding = 12;

    public PropertyDisplay(String propName, String propPrice, String propColor, String baseColor, int boardHeight, int boardDimen){
        this.myPropName = propName;
        this.myPropPrice = propPrice;
        this.myPropColor = propColor;
        this.myBaseColor = baseColor;
        myPropertyPane = new Pane();
        this.myBoardDimen = boardDimen;
        this.myBoardHeight = boardHeight;

        myPropStackPane = new StackPane();
    }

    public PropertyDisplay(String baseColor, String image, int boardHeight, int boardDimen){
        this.myBaseColor = baseColor;
        this.myImage = image;
        this.myBoardDimen = boardDimen;
        this.myBoardHeight = boardHeight;
        myPropertyPane = new Pane();
        myPropStackPane = new StackPane();
    }


    public Rectangle createBaseRectangle(int w, int h){
        myRectangle = new Rectangle(w,h);
        myRectangle.setFill(Color.web(this.myBaseColor));
        myRectangle.setStroke(Color.BLACK);

        return myRectangle;
    }

    public Pane createText(){
        FlowPane textPane = new FlowPane();
        Text t = new Text(myPropName);
        t.setWrappingWidth(myBoardHeight/myBoardDimen);
        t.setTextAlignment(TextAlignment.CENTER);
        Text priceProp = new Text("$"+myPropPrice);
        textPane.setId("propText");
        textPane.setPrefWrapLength(myBoardHeight/myBoardDimen);
        textPane.setAlignment(Pos.CENTER);
        textLocation(textPane);
        textPane.setVgap(SPACING);
        textPane.setPadding(new Insets(textPadding,0,0,0));
        textPane.getChildren().addAll(t,priceProp);
        return textPane;
    }

    public Pane createPropColor(int w, int h){
        createBaseRectangle(w,h);
        if (myPropColor==null){
            myPropertyPane.getChildren().addAll(myRectangle);
        }
        else{
            propColor = new HBox();
            propColorLocation(propColor);
            propColor.setId("propColor");
            propColor.setStyle(  " -fx-background-color:" + myPropColor);
            myPropertyPane.getChildren().addAll(myRectangle,propColor);
        }

        return myPropertyPane;
    }

    public Pane createImagePane(int w, int h){
        myImagePane = new Pane();
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myImage));
        ImageView image = new ImageView(imageFile);
        image.setFitWidth(w);
        image.setFitHeight(h);
        myImagePane.getChildren().addAll(createBaseRectangle(w,h),image);
        imageLocation(myImagePane);
        return myImagePane;
    }

    public abstract void imageLocation(Pane image);

    public abstract void propColorLocation(HBox propColor);

    public abstract void textLocation(FlowPane textPane);

    public Pane getMyPropStackPane(){ return myPropStackPane; }

}
