package View;

import Controller.Controller;
import Controller.Die;
import Model.AbstractPlayer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class DiceRoller {
    protected Die myDie;
    protected HBox myHBox;
    protected Controller myController;
    protected  Button rollButton;


    public DiceRoller(Controller controller){
        myController = controller;
        myDie = new Die(6);

        myHBox = new HBox();
        myHBox.setSpacing(40.0);
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
        Text diceValue = new Text("");
        diceValue.setId("header2");
        myHBox.getChildren().add(diceValue);
    }
    private void rollDice(){
        int roll = myController.getGame().rollDice();
        System.out.println("roll: " + roll);
        updateDiceView(roll);
        rollButton.setDisable(true);
    }

    private void updateDiceView(int val){
        Text newVal = new Text(Integer.toString(val));
        newVal.setId("header2");
        myHBox.getChildren().set(0,newVal);
    }

    public void setDisable(boolean bool) {rollButton.setDisable(bool);}
}
