package game;

import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class Floor extends GameElement {

    private boolean grounded;

    public Floor(Image i) {
        super(i);
        grounded = true;
    }

    public void setGrounded(boolean g) {
        grounded = g;
    }

    @Override
    public void react(GameElement e) {
        grounded = true;
    }
}
