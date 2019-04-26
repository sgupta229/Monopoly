package View;

import Model.AbstractPlayer;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;


public class AbstractPlayerCell extends ListCell<AbstractPlayer> {
        private HBox myHBox;
        private ImageView myImage;
        private Label label = new Label("");
        private Pane pane = new Pane();
        private Button button = new Button("X");
        transient private ObservableList<String> availableTokens;

        public AbstractPlayerCell(ObservableList<String> tokens) {
            super();
            availableTokens = tokens;
            myImage = new ImageView();

            myHBox = new HBox(4);
            myHBox.setAlignment(Pos.CENTER_LEFT);
            myHBox.getChildren().addAll(myImage, label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
            button.setOnAction(event -> {
                availableTokens.add(getItem().getImage());
                getListView().getItems().remove(getItem());
            });
        }

        @Override
        protected void updateItem(AbstractPlayer item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            setGraphic(null);

            if (item != null && !empty) {
                label.setText(item.getName());
                myImage.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream(item.getImage()),
                        Board.TOKEN_SPACING,Board.TOKEN_SPACING,false,true));
                setGraphic(myHBox);
            }
        }
    }
