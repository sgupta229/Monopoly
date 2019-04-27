package View.PopUps;

import Controller.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import java.io.File;


public abstract class Popup {
    public static final double IMAGE_WIDTH_SPACING = 2.5;
    public static final int V_BOX_SPACING = 2;
    public static final int HBOX_SPACING_TEN = 10;
    public static final int HBOX_SPACING_FORTY = 40;
    public static final int PADDING_TWENTY = 20;
    public static final int BIDDING_STEP = 20;
    public static final int POPUP_TEXT = 2;
    public static final double IMAGE_HEIGHT_SPACING = 1.5;
    public static final int PROP_COLOR_SPACING = 7;
    public static final int VBOX_SPACING = 3;



    public static final int OK = 0;
    public static final int NO = 1;


    public Popup(){
    }

    public void display() {
        Stage popUpWindow =new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle(createTitle());

        BorderPane layout = new BorderPane();
        HBox all = new HBox(HBOX_SPACING_FORTY);

        Scene scene1= new Scene(layout, Controller.WIDTH/2, Controller.HEIGHT/IMAGE_HEIGHT_SPACING);
        scene1.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());

        Label label1= new Label(createMessage());
        label1.setWrapText(true);
        label1.setId("popupMessage");
        label1.setPrefSize(Controller.WIDTH/4, Controller.HEIGHT/2.25 );

        VBox text= new VBox();

        VBox image= new VBox(HBOX_SPACING_TEN);
        Pane buttons = createButtons(popUpWindow);
        Pane fullImage = createImage(scene1, popUpWindow);


        Label title = createHeader();
        if (title!=null){
            title.setId("popupTitle");
            all.getChildren().addAll(image, text);
            layout.setTop(title);
            layout.setAlignment(title, Pos.CENTER);
            layout.setMargin(title, new Insets(PADDING_TWENTY,PADDING_TWENTY,OK,PADDING_TWENTY));
            image.getChildren().addAll(fullImage);
            image.setAlignment(Pos.CENTER_LEFT);
            text.getChildren().addAll(label1, buttons);
        }
        else {
            all.getChildren().addAll(image);
            image.getChildren().addAll(fullImage);
            image.setAlignment(Pos.TOP_LEFT);
        }
        layout.setCenter(all);
        all.setAlignment(Pos.CENTER);
        popUpWindow.setScene(scene1);
        popUpWindow.show();
    }

    protected abstract Pane createImage(Scene scene, Stage popUpWindow);

    protected abstract String createMessage();

    protected abstract String createTitle();

    protected abstract Pane createButtons(Stage window);

    protected abstract Label createHeader();

}
