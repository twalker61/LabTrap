package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Wall extends GameElement {

    public Wall() {
        super();
        super.setImage(new Image(getClass().getResource("../images/wallFull.png").toExternalForm()));
    }
}
