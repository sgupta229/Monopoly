//package View.SpaceDisplay;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.Pane;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextAlignment;
//
//public class UtilityAndCardDisplay extends SpaceDisplay {
//
//    public UtilityAndCardDisplay(String propName, String baseColor, Pane boardPane, String image) {
//        super(propName, baseColor, boardPane, image);
//    }
//
//    @Override
//    public ImageView createImage() {
//        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myImage));
//        ImageView image = new ImageView(imageFile);
//
//        return image;
//    }
//
//    @Override
//    public Text createText() {
////        FlowPane textPane = new FlowPane();
//        Text t = new Text(myPropName);
//        t.setWrappingWidth((int)myBoardPane.getPrefWidth()/13);
//        t.setTextAlignment(TextAlignment.CENTER);
//        t.setId("propText");
//
//        textLocation(textPane);
//        textPane.setVgap(10);
//        textPane.setPadding(new Insets(12,0,0,0));
//        textPane.getChildren().addAll(t,priceProp);
//        return textPane;
//    }
//}
