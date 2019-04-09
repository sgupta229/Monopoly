package View;

import Controller.Die;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class DiceRoller {
    protected Die myDie;
    protected HBox myHBox;

    public DiceRoller(){
        myDie = new Die(6);

        myHBox = new HBox();
        myHBox.setSpacing(40.0);
        createDiceView();
        Button rollButton = new Button("Roll");
        rollButton.setId("button1");
        rollButton.setOnAction(e -> rollDice());
        myHBox.getChildren().addAll(rollButton);
        myHBox.setPrefWidth(GameView.WIDTH * 0.4);
        myHBox.setAlignment(Pos.CENTER);
    }

    public HBox getDiceRollerView(){
        return myHBox;
    }
    private void createDiceView(){
        Text diceValue = new Text("2");
        diceValue.setId("header2");
        myHBox.getChildren().add(diceValue);
    }
    private void rollDice(){
        updateDiceView(myDie.rollDie() + myDie.rollDie());
        //TODO: change to use Game's rollDie()
    }
    private void updateDiceView(int val){
        Text newVal = new Text(Integer.toString(val));
        newVal.setId("header2");
        myHBox.getChildren().set(0,newVal);
    }
}
