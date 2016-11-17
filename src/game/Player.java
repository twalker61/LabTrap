package game;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Player {

    //Point2D object for velocity?
    //what is imageview.setViewport

    private boolean canMove;
    private Image img;
    private Bounds boundary;
    private double posX;
    private double posY;

    public Player(Image i, double x, double y) {
        //super(i);
        img = i;
        canMove = true;
        posX = x;
        posY = y;
        boundary = new BoundingBox(posX, posY, img.getWidth(), img.getHeight());
    }

    public void setMobility(boolean m) {
        canMove = m;
    }

    public boolean canMove() {
        return canMove;
    }

    public Bounds getBoundary() {
        return boundary;
    }

    /*@Override
    public void react(GameElement e) {
        // do nothing
    }*/
}
