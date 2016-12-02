package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Floor extends GameElement {

    private boolean chewed;
    private int chews;

    public Floor() {
        super();
        super.setImage(new Image(getClass().getResource("../images/floor.png").toExternalForm()));
    }

    @Override
    public Rectangle2D getBoundary() {
        return new Rectangle2D(getPositionX(), getPositionY(), getWidth(), getHeight()/4);
    }

    public void chew() {
        chews++;
        if (chews >= 5) {
            chewed = true;
            super.setImage(new Image(getClass().getResource("../images/chewedFloor.png").toExternalForm()));
        }
    }

    public boolean isChewed() {
        return chewed;
    }

    @Override
    public void setHover() {
        super.setImage(new Image(getClass().getResource("../images/floorOutline.png").toExternalForm()));
        hover();
    }
}
