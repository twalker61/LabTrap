package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Floor extends GameElement {

    public Floor() {
        super();
        super.setImage(new Image(getClass().getResource("../images/floor.png").toExternalForm()));
    }
}
