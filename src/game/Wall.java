package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class Wall extends GameElement {

    private boolean chewed;
    private int chews;

    public Wall() {
        super();
        super.setImage(new Image(getClass().getResource("../images/wall.png").toExternalForm()));
    }

    public void chew() {
        chews++;
        if (chews >= 5) {
            chewed = true;
            super.setImage(new Image(getClass().getResource("../images/chewedWall.png").toExternalForm()));
        }
    }

    public boolean isChewed() {
        return chewed;
    }

    @Override
    public void setHover() {
        super.setImage(new Image(getClass().getResource("../images/wallOutline.png").toExternalForm()));
        hover();
    }
}
