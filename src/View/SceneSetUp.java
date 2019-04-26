package View;

import Controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SceneSetUp {
    private static final double FRAMES_PER_SECOND = 1;
    private static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;

    private Timeline animation = new Timeline();

    public SceneSetUp(double width, double height, Controller controller) {
        setupAnimation();
    }

    private void setupAnimation(){
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> this.step());
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
    }

    private void step() {
    }
}
