package View.PopUps;

import Controller.Controller;
import Model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class BuildOrSellPopup extends BuyPropertyPopup {

    private TabPane tabPane;
    private Controller myController;
    private double tabPaneWidth = 1.5;
    private ResourceBundle myText;
    private String myProperty;

    //TODO: CHECK PLAYERS MONOPOLY WHEN MANAGE PROP IS HIT, IF FALSE THEN DISABLE ALL BUTTONS

    public BuildOrSellPopup(int propLocation, Controller controller) {
        super(propLocation);
        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH/tabPaneWidth);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.myController = controller;
        this.myText = super.getMessages();
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        HBox layout = new HBox();
        Tab buildTab = new Tab(myText.getString("buildTab"));
        buildTab.setContent(setBuildInfo(popUpWindow, myText.getString("buyHouse"), myText.getString("buyHotel"),myText.getString("BuildMessage")));

        Tab sellTab = new Tab(myText.getString("sellTab"));
        HBox sellInfo = new HBox(10);
        sellInfo.getChildren().addAll(setBuildInfo(popUpWindow, myText.getString("sellHouse"), myText.getString("sellHotel"),myText.getString("SellMessage")), createIndividualButton(popUpWindow,myText.getString("mortgageButton")));
        sellTab.setContent(sellInfo);
        tabPane.getTabs().addAll(buildTab,sellTab);
        layout.getChildren().add(tabPane);

        return layout;
    }


    private Pane setBuildInfo(Stage popUpWindow, String key1, String key2, String message) {
        BorderPane pane = new BorderPane();
        HBox props = createPropComboBox();
        pane.setTop(props);
        pane.setAlignment(props,Pos.CENTER);

        HBox propDetails = new HBox(HBoxSpacing);
        VBox property = new VBox(HBoxSpacing);
        property.setId("propVBox");

        VBox incrementer = new VBox();
        Pane buttons = createIndividualButton(popUpWindow,key1);
        Pane button2 = createIndividualButton(popUpWindow,key2);

        Label mess = new Label(message);
        incrementer.getChildren().addAll(mess,buttons, button2);
        incrementer.setAlignment(Pos.CENTER);
        incrementer.setId("buildIncrementerVBox");
        propDetails.getChildren().addAll(property,incrementer);
        pane.setCenter(propDetails);

        return pane;
    }

    private HBox createPropComboBox(){
        HBox combo = new HBox();
        Button button1= new Button("OK");
        ComboBox props = new ComboBox();
        for (Property p : myController.getGame().getCurrPlayer().getProperties()){
            props.getItems().add(p.getName());
        }
        props.setPromptText(myText.getString("comboBoxText"));
        button1.setOnAction(e -> {
            myProperty=props.getValue().toString();
            System.out.println("Managing this property:" + myProperty);
        });

        combo.getChildren().addAll(props,button1 );
        return combo;
    }

    @Override
    protected String createMessage() {
        return "";
    }

    @Override
    protected String createTitle() {
        return myText.getString("managePropTitle");
    }

    @Override
    protected Pane createButtons(Stage window) {
        Pane buttons = new HBox(HBoxSpacing);
        return buttons;
    }

    private Pane createIndividualButton(Stage window, String key){
        Button button1= new Button(key);
        button1.setId("button3");
        button1.setOnAction(e -> window.close());
        Pane myButtons = createButtons(window);

        myButtons.getChildren().add(button1);
        return myButtons;
    }

    @Override
    protected Label createHeader() {
        return null;
    }

}
