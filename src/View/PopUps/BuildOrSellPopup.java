package View.PopUps;

import Controller.Controller;
import Model.properties.BuildingType;
import Model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ResourceBundle;


public class BuildOrSellPopup extends Popup {

    private TabPane tabPane;
    private Controller myController;
    private ResourceBundle myText;
    private Property myProperty;
    private Button myMortgage;
    private Button unMortgage;
    private Button button2;
    private Button button;
    private Button button3;
    private Button button4;
    private VBox sellInfo;

    public BuildOrSellPopup(Controller controller) {
        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH/IMAGE_HEIGHT_SPACING);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.myController = controller;
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(2).toString());
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        HBox layout = new HBox();
        layout.setStyle(  " -fx-background-color: skyblue");
        layout.setPrefHeight(Controller.WIDTH);

        Tab buildTab = new Tab(myText.getString("buildTab"));
        buildTab.setContent(setBuildInfo(popUpWindow, myText.getString("buyHouse"), myText.getString("buyHotel"),myText.getString("BuildMessage")));

        Tab sellTab = new Tab(myText.getString("sellTab"));
        sellInfo = new VBox(HBOX_SPACING_TEN);

        createMortgageButtons(popUpWindow);

        sellInfo.getChildren().addAll(setSellInfo(popUpWindow, myText.getString("sellHouse"), myText.getString("sellHotel"),myText.getString("SellMessage")),myMortgage, unMortgage);
        sellTab.setContent(sellInfo);
        sellInfo.setSpacing(50);
        tabPane.getTabs().addAll(buildTab,sellTab);
        layout.getChildren().add(tabPane);

        return layout;
    }

    private void createMortgageButtons(Stage popUpWindow){
        myMortgage = createIndividualButton(myText.getString("mortgageButton"));
        unMortgage = createIndividualButton(myText.getString("unMortgage"));
        unMortgage.setOnAction(e ->{
            mortgageHandler(popUpWindow,myText.getString("unMortgageText"));
        });
        myMortgage.setDisable(true);
        unMortgage.setDisable(true);
        myMortgage.setOnAction(e -> {
            mortgageHandler(popUpWindow,myText.getString("mortgageText"));
        });
    }

    private void mortgageHandler(Stage popUpWindow, String key){
        Alert a = new Alert(Alert.AlertType.NONE);
        myController.getGame().getBank().mortgageProperty(myProperty);
        a.setContentText(key + myProperty.getName() + "!");
        popUpWindow.close();
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.show();
    }

    private void buildHandler(Stage popUpWindow,String key){
        myController.getGame().getBank().build(myProperty,BuildingType.valueOf(key));
        popUpWindow.close();
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setContentText(myText.getString("bought") + key + " for " + myProperty.getName() + "! " +myText.getString("totalMessage") +myProperty.getNumBuilding(BuildingType.valueOf(key))+ " " + key + myText.getString("additional") );
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.show();

    }

    private Pane setBuildInfo(Stage popUpWindow, String key1, String key2, String message) {
        BorderPane pane = new BorderPane();
        HBox props = createPropComboBox();
        pane.setTop(props);
        pane.setAlignment(props,Pos.CENTER);

        HBox propDetails = new HBox(HBOX_SPACING_TEN);
        VBox property = new VBox(HBOX_SPACING_TEN);
        property.setId("propVBox");

        VBox incrementer = new VBox();

        Pane myButtons = createButtons(popUpWindow);

        button = createIndividualButton(key1);
        button.setDisable(true);
        button.setOnAction(e -> {
            buildHandler(popUpWindow, myText.getString("buildingLevel0"));
        });
        button2 = createIndividualButton(key2);
        button2.setDisable(true);
        button2.setOnAction(e -> {
            buildHandler(popUpWindow, myText.getString("buildingLevel1"));
        });

        myButtons.getChildren().addAll(button,button2);

        Label mess = new Label(message);
        incrementer.getChildren().addAll(mess,myButtons);
        incrementer.setAlignment(Pos.CENTER);
        incrementer.setId("buildIncrementerVBox");
        propDetails.getChildren().addAll(property,incrementer);
        pane.setCenter(propDetails);

        return pane;
    }

    private Pane setSellInfo(Stage popUpWindow, String key1, String key2, String message){
        BorderPane pane = new BorderPane();
        HBox props = createPropComboBox();
        pane.setTop(props);
        pane.setAlignment(props,Pos.CENTER);
        HBox propDetails = new HBox(HBOX_SPACING_TEN);
        VBox property = new VBox(HBOX_SPACING_TEN);
        property.setId("propVBox");
        VBox incrementer = new VBox();
        Pane myButtons = createButtons(popUpWindow);

        button3 = createIndividualButton(key1);
        button3.setDisable(true);
        button3.setOnAction(e -> {
            myController.getGame().getBank().sellBackBuildings(myProperty,BuildingType.valueOf(myText.getString("buildingLevel0")));
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("You sold a " + myText.getString("building1") + " on " + myProperty.getName() + "! " + "You have a total of " +myProperty.getNumBuilding(BuildingType.valueOf(myText.getString("buildingLevel0")))+ " " + myText.getString("building1")+ "(s) on this property." );
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.show();
        });
        button4 = createIndividualButton(key2);
        button4.setDisable(true);
        button4.setOnAction(e -> {
            myController.getGame().getBank().sellBackBuildings(myProperty,BuildingType.valueOf(myText.getString("buildingLevel1")));
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("You sold a " + myText.getString("building2") + " on " + myProperty.getName() + "! " + "You have a total of " +myProperty.getNumBuilding(BuildingType.valueOf(myText.getString("buildingLevel1")))+ " " + myText.getString("building2")+ "(s) on this property." );
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.show();
        });
        myButtons.getChildren().addAll(button3,button4);

        Label mess = new Label(message);
        incrementer.getChildren().addAll(mess,myButtons);
        incrementer.setAlignment(Pos.CENTER);
        incrementer.setId("buildIncrementerVBox");
        propDetails.getChildren().addAll(property,incrementer);
        pane.setCenter(propDetails);

        return pane;
    }

    private HBox createPropComboBox(){
        HBox combo = new HBox();
        Button okButton = createIndividualButton("OK");
        ComboBox props = new ComboBox();
        for (Property p : myController.getGame().getCurrPlayer().getProperties()){
            props.getItems().add(p.getName());
        }
        props.setPromptText(myText.getString("comboBoxText"));
        okButton.setOnAction(e -> {
            for (Property p : myController.getGame().getCurrPlayer().getProperties()){
                if (p.getName().equals(props.getValue())){
                    myProperty = p;
                }
            }
            checkIfCanMortgage();
            checkIfCanBuild();
            checkIfCanSell();
        });
        combo.getChildren().addAll(props, okButton );
        return combo;
    }

    private void checkIfCanBuild(){
        if (myController.getGame().getBank().checkIfCanBuild(myProperty, BuildingType.valueOf(myText.getString("buildingLevel0")))){
            button.setDisable(false);
        }
        if (myController.getGame().getBank().checkIfCanBuild(myProperty, BuildingType.valueOf(myText.getString("buildingLevel1")))){
            button2.setDisable(false);
        }
    }

    private void checkIfCanMortgage(){
        if (myController.getGame().getBank().checkIfCanMortgage(myProperty)){
            myMortgage.setDisable(false);
        }
        if (myProperty.getIsMortgaged()){
            unMortgage.setDisable(false);
        }
    }

    private void checkIfCanSell(){
        if (myProperty.getNumBuilding(BuildingType.valueOf(myText.getString("buildingLevel0")))>0){
            button3.setDisable(false);
        }
        if (myProperty.getNumBuilding(BuildingType.valueOf(myText.getString("buildingLevel1")))>0){
            button4.setDisable(false);
        }
    }

    @Override
    protected String createMessage() { return ""; }

    @Override
    protected String createTitle() { return myText.getString("managePropTitle"); }

    @Override
    protected Pane createButtons(Stage window) {
        Pane buttons = new HBox(HBOX_SPACING_TEN);
        return buttons;
    }

    private Button createIndividualButton(String key){
        Button button1= new Button(key);
        button1.setId("button1");
        return button1;
    }

    @Override
    protected Label createHeader() { return null; }

}
