package game;

import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class Button extends GameElement {

    private double numPressed;

    public Button(Image i) {
        super(i);
    }

    public double getNumPressed() {
        return numPressed;
    }

    @Override
    public void react(GameElement g) {
        numPressed++;
        //set actionListener so that when numPressed > x, set ExitPortal to open
    }
}
