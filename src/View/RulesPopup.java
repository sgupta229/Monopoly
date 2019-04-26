package View;

import Controller.AbstractGame;
import Controller.Controller;
import View.PopUps.Popup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.NumberSpinner;

import java.io.File;
import java.util.ResourceBundle;

public class RulesPopup {
    private AbstractGame myGame;
    private Stage window;
    private VBox myLayout1;
    private VBox myLayout2;
    private Pane myNewLayout;
    private HBox myHBox;
    private ToggleGroup buildingToggle;
    private ToggleGroup freeParkingToggle;
    private ToggleGroup landOnGoBonus;
    private NumberSpinner snakeEyesMoney;
    private NumberSpinner jailRolls;
    private NumberSpinner startFunds;
    private NumberSpinner jailBail;
    private NumberSpinner passGo;
    private NumberSpinner bankFunds;
    private Button applyButton;

    private ResourceBundle messages;

    public RulesPopup(AbstractGame game){
        messages = ResourceBundle.getBundle("Messages");
        myGame = game;
    }

    public void display() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Edit Rules");

        setUpLayout();

        Scene scene1= new Scene(myNewLayout, Controller.WIDTH*.75, Controller.HEIGHT);
        scene1.getStylesheets().add(( new File("data/GUI.css") ).toURI().toString());

        window.setScene(scene1);
        window.showAndWait();
    }

    //pane
    //hbox
    //vbox

    private void setUpLayout(){

        myNewLayout = new Pane();
        myHBox = new HBox(Popup.PADDING_TWENTY);

        myLayout1 = new VBox(Popup.HBOX_SPACING_TEN);
        myLayout2 = new VBox(Popup.HBOX_SPACING_TEN);

        buildingToggle = makeToggleGroup("YES","NO");
        freeParkingToggle = makeToggleGroup("YES","NO");
        landOnGoBonus = makeToggleGroup("YES","NO");
        snakeEyesMoney = new NumberSpinner((int)myGame.getSnakeEyes(),1);
        jailRolls = new NumberSpinner(myGame.getRollsInJailRule(),1);
        startFunds = new NumberSpinner((int)myGame.getStartFunds(),100);    //TODO is it okay to cast to int
        jailBail = new NumberSpinner((int)myGame.getJailBail(),50);
        passGo = new NumberSpinner((int)myGame.getPassGo(),50);
        bankFunds = new NumberSpinner((int)myGame.getBankFunds(),500);

        applyButton = new Button("Apply");
        applyButton.setOnAction(new ApplyButtonHandler());
        myLayout1.getChildren().addAll(makeRuleView(messages.getString("buildingRule"),makeToggleBox(buildingToggle)),
                makeRuleView(messages.getString("freeParkingRule"),makeToggleBox(freeParkingToggle)),
                makeRuleView(messages.getString("landOnGo"),makeToggleBox(landOnGoBonus)),
                makeRuleView(messages.getString("jailRollsRule"),jailRolls));
        myLayout2.getChildren().addAll(makeRuleView(messages.getString("startingFundsRule"),startFunds),
                makeRuleView(messages.getString("jailBailRule"),jailBail),
                makeRuleView(messages.getString("passGoRule"),passGo),
                makeRuleView(messages.getString("bankFundsRule"),bankFunds),
                makeRuleView(messages.getString("snakeEyesRule"),snakeEyesMoney),
                applyButton);
        myHBox.getChildren().addAll(myLayout1,myLayout2);
        myNewLayout.getChildren().addAll(myHBox);

    }

    private VBox makeRuleView(String rule, Node buttons){
        VBox ret = new VBox(Popup.VBOX_SPACING);
        Label label = new Label(rule);
        ret.getChildren().addAll(label,buttons);
        return ret;
    }

    private ToggleGroup makeToggleGroup(String opt1, String opt2){
        ToggleGroup group = new ToggleGroup();
        ToggleButton on = new ToggleButton(opt1);
        ToggleButton off = new ToggleButton(opt2);
        on.setToggleGroup(group);
        off.setToggleGroup(group);
        group.selectToggle(on);
        return group;
    }
    private HBox makeToggleBox(ToggleGroup group){
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(Popup.VBOX_SPACING);
        for (Toggle toggle: group.getToggles()){
            buttonBox.getChildren().add((ToggleButton)toggle);
        }
        return buttonBox;
    }

    class ApplyButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            String buildingChoiceString = ((ToggleButton)buildingToggle.getSelectedToggle()).getText();
            String freeParkingChoiceString = ((ToggleButton)freeParkingToggle.getSelectedToggle()).getText();
            String landOnGoChoiceString = ((ToggleButton)landOnGoBonus.getSelectedToggle()).getText();

            boolean buildingChoice = false;
            boolean freeParkingChoice = false;
            boolean landOnGoChoice = false;
            if (buildingChoiceString.equalsIgnoreCase("yes")) buildingChoice = true;
            if (freeParkingChoiceString.equalsIgnoreCase("yes")) freeParkingChoice = true;
            if (landOnGoChoiceString.equalsIgnoreCase("yes")) landOnGoChoice = true;

            int jailRollsChoice = jailRolls.getNumber();
            int startFundsChoice = startFunds.getNumber();
            int jailBailChoice = jailBail.getNumber();
            int passGoChoice = passGo.getNumber();
            int bankFundsChoice = bankFunds.getNumber();
            int snakeEyesChoice = snakeEyesMoney.getNumber();

            myGame.setEvenBuildingRule(buildingChoice);
            myGame.setFreeParkingRule(freeParkingChoice);
            myGame.setLandOnGoMult(landOnGoChoice);
            myGame.setRollsInJailRule(jailRollsChoice);
            myGame.setStartFunds(startFundsChoice);
            myGame.setJailBail(jailBailChoice);
            myGame.setPassGo(passGoChoice);
            myGame.setBankFunds(bankFundsChoice);
            myGame.setSnakeEyes(snakeEyesChoice);

            window.close();
        }
    }
}
