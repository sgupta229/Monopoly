package View.SpaceDisplay;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CornerDisplay extends SpaceDisplay {

    public CornerDisplay(String baseColor,String image) {
        super(baseColor,image);
    }

    @Override
    public ImageView createImage() {
        var imageFile = new Image(this.getClass().getClassLoader().getResourceAsStream(myImage));
        ImageView image = new ImageView(imageFile);

        return image;
    }

    @Override
    public Text createText() {
        return null;
    }
}
