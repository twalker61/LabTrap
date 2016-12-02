package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class PortalButton extends GameElement {

    private boolean pressed;

    public PortalButton() {
        super();
        super.setImage(new Image(getClass().getResource("../images/button.png").toExternalForm()));
    }

    public void press() {
        pressed = true;
        super.setImage(new Image(getClass().getResource("../images/buttonPressed.png").toExternalForm()));
    }

    public boolean pressed() {
        return pressed;
    }

    @Override
    public void setHover() {
        super.setImage(new Image(getClass().getResource("../images/buttonOutline.png").toExternalForm()));
        hover();
    }
}
