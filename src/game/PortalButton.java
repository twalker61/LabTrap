package game;

import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class PortalButton extends GameElement {

    private int numPressed;

    public PortalButton(Image i) {
        super(i);
    }

    public int getNumPressed() {
        return numPressed;
    }

    @Override
    public void react(GameElement g) {
        numPressed++;
        //set actionListener so that when numPressed > x, set ExitPortal to open
    }
}
