package View.PopUps;

import Controller.Controller;
import Model.properties.Property;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ResourceBundle;

import static Model.properties.BuildingType.*;

public class BuildOrSellPopup extends BuyPropertyPopup {

    private TabPane tabPane;
    private Controller myController;
    private ResourceBundle myText;
    private Property myProperty;
    private Button myMortgage;
    private Button button2;
    private Button button;
    private Button button3;
    private Button button4;


    //TODO: CHECK PLAYERS MONOPOLY WHEN MANAGE PROP IS HIT, IF FALSE THEN DISABLE ALL BUTTONS

    public BuildOrSellPopup(int propLocation, Controller controller) {
        super(propLocation);
        tabPane = new TabPane();
        tabPane.setPrefWidth(Controller.WIDTH/IMAGE_HEIGHT_SPACING);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.myController = controller;
        this.myText = ResourceBundle.getBundle(myController.getGame().getFrontEndFiles().get(2).toString());
    }

    @Override
    protected Pane createImage(Scene scene, Stage popUpWindow) {
        HBox layout = new HBox();
        Tab buildTab = new Tab(myText.getString("buildTab"));
        buildTab.setContent(setBuildInfo(popUpWindow, myText.getString("buyHouse"), myText.getString("buyHotel"),myText.getString("BuildMessage")));

        Tab sellTab = new Tab(myText.getString("sellTab"));
        HBox sellInfo = new HBox(HBOX_SPACING_TEN);
        myMortgage = createIndividualButton(popUpWindow,myText.getString("mortgageButton"));
        myMortgage.setDisable(true);
        myMortgage.setOnAction(e -> {
            myController.getGame().getBank().mortgageProperty(myProperty);
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            if (myProperty.getIsMortgaged()){
                a.setContentText("You mortgaged " + myProperty.getName() + "!");
            }
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.show();
        });
        sellInfo.getChildren().addAll(setSellInfo(popUpWindow, myText.getString("sellHouse"), myText.getString("sellHotel"),myText.getString("SellMessage")),myMortgage);
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

        HBox propDetails = new HBox(HBOX_SPACING_TEN);
        VBox property = new VBox(HBOX_SPACING_TEN);
        property.setId("propVBox");

        VBox incrementer = new VBox();

        Pane myButtons = createButtons(popUpWindow);

        button = createIndividualButton(popUpWindow,key1);
        button.setDisable(true);
        button.setOnAction(e -> {
            myController.getGame().getBank().build(myProperty,HOUSE);
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("You bought a " + myText.getString("building1") + " for " + myProperty.getName() + "! " + "You have a total of " +myProperty.getNumBuilding(HOUSE)+ " " + myText.getString("building1")+ "(s) on this property." );
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.show();
        });
        button2 = createIndividualButton(popUpWindow,key2);
        button2.setDisable(true);
        button2.setOnAction(e -> {
            myController.getGame().getBank().build(myProperty,HOTEL);
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("You bought a " + myText.getString("building2") + " for " + myProperty.getName() + "! " + "You have a total of " +myProperty.getNumBuilding(HOTEL)+ " " + myText.getString("building2")+ "(s) on this property." );
            a.setAlertType(Alert.AlertType.INFORMATION);
            a.show();
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

        button3 = createIndividualButton(popUpWindow,key1);
        button3.setDisable(true);
        button3.setOnAction(e -> {
            myController.getGame().getBank().sellBackBuildings(myProperty,HOUSE);
            //TODO implement sell here
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("You sold a " + myText.getString("building1") + " on " + myProperty.getName() + "! " + "You have a total of " +myProperty.getNumBuilding(HOUSE)+ " " + myText.getString("building1")+ "(s) on this property." );
            a.show();
        });
        button4 = createIndividualButton(popUpWindow,key2);
        button4.setDisable(true);
        button4.setOnAction(e -> {
            myController.getGame().getBank().sellBackBuildings(myProperty,HOTEL);
            popUpWindow.close();
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setContentText("You sold a " + myText.getString("building2") + " on " + myProperty.getName() + "! " + "You have a total of " +myProperty.getNumBuilding(HOTEL)+ " " + myText.getString("building2")+ "(s) on this property." );
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
        Button button1= new Button("OK");
        ComboBox props = new ComboBox();
        for (Property p : myController.getGame().getCurrPlayer().getProperties()){
            props.getItems().add(p.getName());
        }
        props.setPromptText(myText.getString("comboBoxText"));
        button1.setOnAction(e -> {
            for (Property p : myController.getGame().getCurrPlayer().getProperties()){
                if (p.getName().equals(props.getValue())){
                    myProperty = p;
                }
            }
            if (!myProperty.getIsMortgaged()){
                myMortgage.setDisable(false);
            }
            if (myController.getGame().getBank().checkIfCanBuild(myProperty, HOUSE) || myController.getGame().getBank().checkIfCanBuild(myProperty,BEACH_CHAIR)){
                button.setDisable(false);
            }
            if (myController.getGame().getBank().checkIfCanBuild(myProperty, HOTEL) || myController.getGame().getBank().checkIfCanBuild(myProperty,CABANA)){
                button2.setDisable(false);
            }
            if (myProperty.getNumBuilding(HOUSE)>0 || myProperty.getNumBuilding(BEACH_CHAIR)>0){
                button3.setDisable(false);
            }
            if (myProperty.getNumBuilding(HOTEL)>0 || myProperty.getNumBuilding(CABANA)>0){
                button4.setDisable(false);
            }
            //TODO check if houses on prop then enable sell button
            //TODO unmortgage
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
        Pane buttons = new HBox(HBOX_SPACING_TEN);
        return buttons;
    }

    private Button createIndividualButton(Stage window, String key){
        Button button1= new Button(key);
        button1.setId("button3");

        return button1;
    }

    @Override
    protected Label createHeader() {
        return null;
    }

}
