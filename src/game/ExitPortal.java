package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class ExitPortal extends GameElement {

    private boolean open;

    public ExitPortal(Image i) {
        super(i);
    }

    /*@Override
    public void react(Player e) {
        if (open) {
            //prompt end of round
        } else {
           e.setMobility(false);
        }
    }*/
}
