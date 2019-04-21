package View;

import Controller.Controller;
import Controller.Die;
import Model.AbstractPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class DiceRoller {
    protected Die myDie;
    protected HBox myHBox;
    protected Controller myController;
    protected  Button rollButton;
    protected List<Label> diceValues;
    private Button myEndTurn;


    public DiceRoller(Controller controller, Button endTurn){
        myController = controller;
        myDie = new Die(6);
        diceValues = new ArrayList<>();
        myHBox = new HBox();
        myHBox.setSpacing(40.0);
        myEndTurn = endTurn;
        createDiceView();
        rollButton = new Button("Roll");
        rollButton.setId("button1");
        rollButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                rollDice();
            }
        });

        myHBox.getChildren().addAll(rollButton);
        myHBox.setPrefWidth(Controller.WIDTH * 0.4);
        myHBox.setAlignment(Pos.CENTER);
    }

    public HBox getDiceRollerView(){
        return myHBox;
    }

    private void createDiceView(){
        for(int i = 0; i < myController.getGame().getDiceHistory().size(); i++) {
            Label diceValue = new Label("");
            diceValue.setId("header2");
            diceValues.add(diceValue);
            myHBox.getChildren().add(diceValue);
        }
    }

    private void rollDice(){
        List<Integer> rolls = myController.getGame().rollDice();
        for(int i = 0; i < rolls.size(); i++) {
            diceValues.get(i).setText(Integer.toString(rolls.get(i)));
        }
        if(!(myController.getGame().checkDoubles())) {
            rollButton.setDisable(true);
            myEndTurn.setDisable(false);
        }
    }

    public void setDisable(boolean bool) {rollButton.setDisable(bool);}
}
