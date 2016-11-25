package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Wall extends GameElement {

    public Wall(Image i) {
        super();
        super.setImage(new Image(getClass().getResource("wall.png").toExternalForm()));
    }

    /*@Override
    public void react(Player e) {
        e.setMobility(false);
    }*/
}
