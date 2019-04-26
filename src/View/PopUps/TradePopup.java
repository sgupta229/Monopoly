package View.PopUps;

import Controller.Controller;
import Model.AbstractPlayer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

public class TradePopup {
    private ResourceBundle messages;
    private Stage myStage;
    private AbstractPlayer currentPlayer;
    private Pane myLayout;

    public TradePopup(AbstractPlayer player){
        this.messages = ResourceBundle.getBundle("Messages");
        this.myStage = new Stage();
        this.currentPlayer = player;
        this.myLayout = new Pane();

        setUpLayout();

    }

    private void setUpLayout(){
        HBox myHBox = new HBox();



        myHBox.getChildren().addAll();
        myLayout.getChildren().addAll(myHBox);
    }

    private VBox playerTradeBox(){
        VBox vBox = new VBox();

        vBox.getChildren().addAll();
        return vBox;
    }

    public void display(){
        Scene scene = new Scene(myLayout, Controller.WIDTH/Popup.V_BOX_SPACING, Controller.HEIGHT/Popup.IMAGE_HEIGHT_SPACING);
        scene.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());
        myStage.setScene(scene);
        myStage.show();
    }

}
