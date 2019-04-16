package View;

import Model.AbstractPlayer;
import Model.properties.ColorProperty;
import Model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


public class PropertyCell extends ListCell<Property> {
    private HBox myHBox;
    private Rectangle color;
    private Label label = new Label("");
    private Pane pane = new Pane();
//    private Button button = new Button("...");

    public PropertyCell() {
        super();

        color = new Rectangle();
        myHBox = new HBox();
        myHBox.setSpacing(10);
        myHBox.setAlignment(Pos.CENTER_LEFT);
        myHBox.getChildren().addAll(label, pane, color);
        HBox.setHgrow(pane, Priority.ALWAYS);
//        button.setOnAction(event -> getListView().getItems());  //TODO: set to show more info?
    }

    @Override
    protected void updateItem(Property item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        if (item != null && !empty) {
            label.setText(item.getName());
            if (item instanceof ColorProperty){
                color.setWidth(60);
                color.setHeight(20);
                color.setFill(Paint.valueOf(item.getColor()));
            }
            setGraphic(myHBox);
        }
    }
}