package View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ChooseGameScreen {

    private static final String LOGO_PATH = "logo.png";
    private double myWidth;
    private double myHeight;
    private BorderPane myLogoPane;
    private ResourceBundle myResourceBundle;
    private List<String> gameTypeButtons;
    private Group myRoot;
    private FlowPane myFlowPane;

    public ChooseGameScreen(Group root, double screenWidth, double screenHeight) {
        myResourceBundle = ResourceBundle.getBundle("GameTypes");
        this.myWidth = screenWidth;
        this.myHeight = screenHeight;
        this.myRoot = root;
        createMonopolyLogo();
        addFlowPane();
        myRoot.getChildren().addAll(myLogoPane, myFlowPane);
    }

    private Button createButton(String text) {
        Button button = new Button(myResourceBundle.getString(text));
        button.setId("button1");
        button.setOnAction(new ButtonHandler(myResourceBundle.getString(text)));
        return button;
    }

    class ButtonHandler implements EventHandler<ActionEvent> {
        private final String gameType;

        ButtonHandler(String game) {
            this.gameType = game;
        }
        @Override
        public void handle(ActionEvent event) {
            System.out.print(gameType);
            removeSplashScreen(myRoot);
        }
    }

    private FlowPane addFlowPane() {
        myFlowPane = new FlowPane();
        myFlowPane.setVgap(50);
        myFlowPane.setHgap(50);
        myFlowPane.setLayoutX(myWidth/6);
        myFlowPane.setLayoutY(myHeight/2);
        myFlowPane.setPrefWrapLength(myWidth/1.5);
        myFlowPane.setId("flowPane");

        gameTypeButtons  = new ArrayList<>(Arrays.asList("classic", "mega", "junior", "starWars"));

        for (String type : gameTypeButtons) {
            myFlowPane.getChildren().add(createButton(type));
        }
        return myFlowPane;
    }

    private BorderPane createMonopolyLogo(){
        myLogoPane = new BorderPane();
        myLogoPane.setPrefSize(myWidth,myHeight/1.5);
        var logo = new Image(this.getClass().getClassLoader().getResourceAsStream(LOGO_PATH));
        myLogoPane.setCenter(new ImageView(logo));
        return myLogoPane;
    }

    private void removeSplashScreen(Group root) { root.getChildren().removeAll(myLogoPane,myFlowPane); }
}
