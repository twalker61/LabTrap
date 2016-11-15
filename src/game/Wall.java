package game;

import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class Wall extends GameElement {

    public Wall(Image i) {
        super(i);
    }

    @Override
    public void react(GameElement e) {
        if (e instanceof Player) {
            ((Player) e).setMobility(false);
        }
    }
}
