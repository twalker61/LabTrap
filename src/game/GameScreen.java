package game;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BurtonGuster on 11/14/16.
 */
public class GameScreen extends AnchorPane {

    private List<PortalButton> buttons;

    public GameScreen() {
        buttons = new ArrayList<>();
    }

    public int getButtonCount() {
        if (buttons.size() == 0) {
            return 0;
        }
        return buttons.get(0).getNumPressed();
    }
}
