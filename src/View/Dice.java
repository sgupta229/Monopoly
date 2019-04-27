package View;
import javafx.animation.RotateTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Dice extends StackPane {

    protected final SimpleIntegerProperty valueProperty = new SimpleIntegerProperty();

    public Dice() {
        Rectangle rect = new Rectangle(80, 80);
        rect.setArcWidth(30.0);
        rect.setArcHeight(20.0);
        rect.setStroke(Color.BLACK);
        rect.setFill(Color.LINEN);
        Text text = new Text();
        text.setFill(Color.BLACK);
        text.setId("header2");
        text.textProperty().bind(valueProperty.asString());
        this.setAlignment(Pos.CENTER);
        getChildren().addAll(rect, text);
    }

    public void roll() {
        RotateTransition rt = new RotateTransition(Duration.seconds(0.5), this);
        rt.setFromAngle(0);
        rt.setToAngle(360);
        rt.play();
    }


}