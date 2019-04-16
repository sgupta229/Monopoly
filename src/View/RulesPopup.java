package View;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.ToggleSwitch;

import java.io.File;

public class RulesPopup {
    private VBox myLayout;

    public void display() {
        Stage window =new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Rules");

        myLayout = new VBox(20);
        myLayout.getChildren().addAll(new ToggleSwitch());

        Scene scene1= new Scene(myLayout, Controller.WIDTH/2, Controller.HEIGHT/1.5);
        scene1.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());

        window.setScene(scene1);
        window.showAndWait();
    }



    //even building boolean
    //free parking boolean
    //jail rolls int
    //start funds double
    //jail bail double
    //pass go double

    //apply rules button
    //cancel button

}
