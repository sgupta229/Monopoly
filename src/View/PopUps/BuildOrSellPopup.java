package View.PopUps;

import Controller.Controller;
import View.BoardConfigReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class BuildOrSellPopup extends BuyPropertyPopup {

    private TabPane tabPane;
    private Map<Integer, ArrayList> colorPropInfo;
    private ArrayList propDetails;
    private String property;
    private Controller myController;

    //TODO: CHECK PLAYERS MONOPOLY WHEN MANAGE PROP IS HIT, IF FALSE THEN DISABLE ALL BUTTONS

    public BuildOrSellPopup(int propLocation, Controller controller) {
        super(propLocation);

        BoardConfigReader spaceInfo = new BoardConfigReader();
        colorPropInfo = spaceInfo.getColorPropInfo();

        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH/1.5);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        this.myController = controller;
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        HBox layout = new HBox();



        Tab buildTab = new Tab("BUILD");
        buildTab.setContent(setBuildInfo(scene, popUpWindow));

        Tab sellTab = new Tab("SELL");
        Label example2 = new Label("Hello!!!!!");
        sellTab.setContent(example2);

        tabPane.getTabs().addAll(buildTab,sellTab);


        layout.getChildren().add(tabPane);

        return layout;
    }


    private Pane setBuildInfo(Scene scene, Stage popUpWindow) {
        BorderPane pane = new BorderPane();

        ComboBox props = new ComboBox();
        props.setOnAction(e -> this.property =  props.getSelectionModel().getSelectedItem().toString());


        props.getItems().add("BOARDWALK");
        props.setPromptText("Choose Your Property");
        pane.setTop(props);
        pane.setAlignment(props,Pos.CENTER);

        HBox propDetails = new HBox(10);
        VBox property = new VBox(10);
        property.setPadding(new Insets(20,10,20,10));
        property.getChildren().add(super.createImage(scene, popUpWindow));

        VBox incrementer = new VBox(15);

        Spinner<Integer> spinner = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 0);
        spinner.setValueFactory(valueFactory);

        VBox spinnerHouses = new VBox();
        spinnerHouses.getChildren().addAll(new Label("# of Houses:"), spinner);

        Spinner<Integer> spinner2 = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 4, 0);
        spinner2.setValueFactory(valueFactory2);

        VBox spinnerHouses2 = new VBox();
        spinnerHouses.getChildren().addAll(new Label("# of Hotels:"), spinner2);

        VBox totalAmt = new VBox();
        TextField total = new TextField();
        total.setEditable(false);
        totalAmt.getChildren().addAll(new Label("TOTAL:"), total);

        Pane buttons = createButtons(popUpWindow);
        incrementer.getChildren().addAll(spinnerHouses, spinnerHouses2, totalAmt, buttons);
        incrementer.setAlignment(Pos.CENTER);
        incrementer.setPadding(new Insets(0,20,0,40));

        propDetails.getChildren().addAll(property,incrementer);
        pane.setCenter(propDetails);



        return pane;
    }

    @Override
    protected String createTitle() {
        String myTitle = "Manage Property";
        return myTitle;
    }

    @Override
    protected Pane createButtons(Stage window) {
        HBox buttons = new HBox(10);
        Button button1= new Button("BUY");
        button1.setId("button4");
        button1.setOnAction(e -> window.close());
        buttons.getChildren().add(button1);
        return buttons;
    }

    @Override
    protected Label createHeader() {
        return null;
    }

}
