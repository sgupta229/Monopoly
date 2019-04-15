package View;

import Model.AbstractPlayer;
import Model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class PropertyCell extends ListCell<Property> {
    private HBox myHBox;
    private Label label = new Label("");
    private Pane pane = new Pane();
    private Button button = new Button("...");

    public PropertyCell() {
        super();

        myHBox = new HBox();
        myHBox.setAlignment(Pos.CENTER_LEFT);
        myHBox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        button.setOnAction(event -> getListView().getItems());  //TODO: set to show more info?
    }

    @Override
    protected void updateItem(Property item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        if (item != null && !empty) {
            label.setText(item.getName());
            setGraphic(myHBox);
        }
    }
}
