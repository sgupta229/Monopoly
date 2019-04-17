package View.PopUps;

import Controller.Controller;
import Model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BuildOrSellPopup extends BuyPropertyPopup {

    private TabPane tabPane;
    private Controller myController;
    private double tabPaneWidth = 1.5;

    //TODO: CHECK PLAYERS MONOPOLY WHEN MANAGE PROP IS HIT, IF FALSE THEN DISABLE ALL BUTTONS

    public BuildOrSellPopup(int propLocation, Controller controller) {
        super(propLocation);
        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH/tabPaneWidth);
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

        for (Property p : myController.getGame().getCurrPlayer().getProperties()){
            props.getItems().add(p.getName());

        }
        props.setPromptText("Choose Your Property");
        pane.setTop(props);
        pane.setAlignment(props,Pos.CENTER);

        HBox propDetails = new HBox(HBoxSpacing);
        VBox property = new VBox(HBoxSpacing);
        property.getChildren().add(super.createImage(scene, popUpWindow));
        property.setId("propVBox");

        VBox incrementer = new VBox();

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
        incrementer.setId("buildIncrementerVBox");

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
        HBox buttons = new HBox(HBoxSpacing);
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
