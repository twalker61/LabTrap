package game;

import javafx.scene.image.Image;

/**
 * Created by twalker61 on 11/14/16.
 */
public class PortalButton extends GameElement {

    private boolean pressed;

    public PortalButton() {
        super();
        super.setImage(new Image(getClass().getResource("../images/portalButton.png").toExternalForm()));
    }

    public void press() {
        pressed = true;
    }

    public boolean pressed() {
        return pressed;
    }
}
