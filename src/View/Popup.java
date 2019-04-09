package View;

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


    public void display() {
        Stage popUpWindow =new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle(myTitle);

        Label label1= new Label(myMessage);

        Button button1= new Button("YES");
        Button button2= new Button("NO");
        button2.setOnAction(e -> popUpWindow.close());

        BorderPane layout = new BorderPane();
        HBox all = new HBox(40);
        VBox text= new VBox();
        HBox buttons = new HBox(10);
        VBox image= new VBox(10);


        Scene scene1= new Scene(layout, GameView.WIDTH/2, GameView.HEIGHT/1.5);
        scene1.getStylesheets().add(( new File("resources/GUI.css") ).toURI().toString());
        button1.setId("button2");
        button2.setId("button2");
        label1.setWrapText(true);
        label1.setId("popupMessage");
        label1.setPrefSize(GameView.WIDTH/4,GameView.HEIGHT/2.25 );
        buttons.getChildren().addAll(button1,button2);
        text.getChildren().addAll(label1, buttons);
        image.setAlignment(Pos.CENTER_LEFT);
        image.getChildren().addAll(createImage(scene1, "Connecticut Avenue"));
        Label title = createHeader(scene1, "Connecticut Avenue");
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

    protected abstract Pane createImage(Scene scene, String prop);


    protected abstract Label createHeader(Scene scene, String prop);

}
