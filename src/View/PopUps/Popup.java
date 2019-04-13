package View.PopUps;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;

import java.io.File;


public abstract class Popup {

    private String myTitle;
    private String myMessage;
    private ImageView myImageView;

    public Popup(String title, String message){
        this.myTitle = title;
        this.myMessage = message;
    }

    public Popup(String title){
        this.myTitle = title;
    }


    public void display() {
        Stage popUpWindow =new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);

        popUpWindow.setTitle(myTitle);


        BorderPane layout = new BorderPane();
        HBox all = new HBox(40);

//        Scene scene1= new Scene(layout, Controller.WIDTH/2, Controller.HEIGHT/1.5);
//        Scene popUpScene = setSizeOfPopup(layout);
        Scene scene1= new Scene(layout, Controller.WIDTH/2, Controller.HEIGHT/1.5);
        scene1.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());

        Label label1= new Label(myMessage);
        label1.setWrapText(true);
        label1.setId("popupMessage");
        label1.setPrefSize(Controller.WIDTH/4, Controller.HEIGHT/2.25 );

        VBox text= new VBox();
        Pane buttons = createButtons(popUpWindow);
        text.getChildren().addAll(label1, buttons);

        VBox image= new VBox(10);
        image.setAlignment(Pos.CENTER_LEFT);
        Pane fullImage = createImage(scene1);
        image.getChildren().addAll(fullImage);

        Label title = createHeader();
        title.setId("popupTitle");
        all.getChildren().addAll(image, text);

        layout.setCenter(all);
        all.setAlignment(Pos.CENTER);
        layout.setTop(title);
        layout.setAlignment(title, Pos.CENTER);
        layout.setMargin(title, new Insets(20,20,0,20));

        popUpWindow.setScene(scene1);
        popUpWindow.showAndWait();
    }

    protected abstract Pane createImage(Scene scene);

    protected abstract Pane createButtons(Stage window);

//    protected abstract Scene setSizeOfPopup(BorderPane layout);

    protected abstract Label createHeader();

}
