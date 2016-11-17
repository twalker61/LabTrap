package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Wall extends GameElement {

    public Wall(Image i) {
        super(i);
    }

    @Override
    public void react(Player e) {
        e.setMobility(false);
    }
}
