package game;

import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class ExitPortal extends GameElement {

    private boolean open;

    public ExitPortal(Image i) {
        super(i);
    }

    @Override
    public void react(GameElement e) {
        if (open) {
            //prompt end of round
        } else {
            if (e instanceof Player) {
                ((Player) e).setMobility(false);
            }
        }
    }
}
