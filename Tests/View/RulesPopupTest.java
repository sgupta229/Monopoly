package View;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RulesPopupTest {

    private Stage stage;
    private Scene scene;
    private boolean done = false;

    @Before
    public void setUp() {
        stage = new Stage();
        scene = new Scene(new Group(), 500, 500);
        stage.setScene(scene);
        stage.show();
        done = false;

    }

    @After
    public void tearDown() {
        stage.hide();

    }

    @Test
    public void testShow() {
        // test showing popup with visible parent
        Popup p1 = new Popup();
        p1.show(stage);
        assertTrue(p1.isShowing());

        // test showing popup with invisible parent
        stage.hide();
        Popup p2 = new Popup();
        p2.show(stage);
        assertFalse(p2.isShowing());

    }

    @Test
    public void testShowLocation() {
        Popup p1 = new Popup();
        p1.show(stage, 10, 20);
        assertTrue(p1.isShowing());
        assertEquals(10, p1.getX(), 1e-100);
        assertEquals(20, p1.getY(), 1e-100);
    }


}