package View;

import Model.AbstractPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class AbstractPlayerCell extends ListCell<AbstractPlayer> {
        private HBox myHBox = new HBox();
        private Label label = new Label("");
        private Pane pane = new Pane();
        private Button button = new Button("X");

        public AbstractPlayerCell() {
            super();

            myHBox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(event -> getListView().getItems().remove(getItem()));
        }

        @Override
        protected void updateItem(AbstractPlayer item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item.getName());
                setGraphic(myHBox);
            }
        }
    }
