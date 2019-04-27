package View;

import Controller.AbstractGame;
import Model.actioncards.AbstractActionCard;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ActionCardCell extends ListCell<AbstractActionCard> {
    private AbstractGame myGame;
    private VBox myVBox;
    private Pane pane = new Pane();
    private Label label = new Label("");
    private Button use = new Button("USE");

    public ActionCardCell(AbstractGame game){
        myGame = game;
        myVBox = new VBox();
        myVBox.setSpacing(3);
        myVBox.setAlignment(Pos.CENTER);
        myVBox.getChildren().addAll(label, pane,use);
        VBox.setVgrow(pane, Priority.ALWAYS);

        label.setPrefSize(100,80);
        label.setFont(Font.font(10));
        label.setWrapText(true);

        use.setOnAction(e -> {
            getItem().doCardAction(myGame);
            getListView().getItems().remove(getItem());
        });
        use.setFont(Font.font(10));
    }

    @Override
    protected void updateItem(AbstractActionCard item, boolean empty) {
        super.updateItem(item, empty);
        setText(null);
        setGraphic(null);

        if (item != null && !empty) {
            label.setText(item.getMyMessage());
            setGraphic(myVBox);
        }
    }
}
