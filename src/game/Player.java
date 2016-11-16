package game;

import javafx.scene.image.Image;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class Player extends GameElement {

    //Point2D object for velocity?
    //what is imageview.setViewport

    boolean canMove;

    public Player(Image i) {
        super(i);
        canMove = true;
    }

    public void setMobility(boolean m) {
        canMove = m;
    }

    public boolean canMove() {
        return canMove;
    }

    @Override
    public void react(GameElement e) {
        // do nothing
    }
}
