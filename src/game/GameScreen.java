package game;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by twalker61 on 11/14/16.
 */
public class GameScreen extends StackPane {

    private AnchorPane backgroundElements;
    private HBox background;
    private List<PortalButton> buttons;
    private List<Floor> floorTiles;
    private List<Wall> walls;
    private List<ExitPortal> exits;
    private double mouseX;
    private double mouseY;
    private GameElement lastPiece;
    private List<GameElement> lastPieceList;
    private String pieceKey;
    private boolean builderMode;
    private static Main main;

    public GameScreen(Main m, boolean mode) {
        builderMode = mode;
        main = m;
        backgroundElements = new AnchorPane();
        background = new HBox();
        Floor starter = new Floor();
        AnchorPane.setTopAnchor(starter, 550 - (starter.getImage().getHeight()));
        AnchorPane.setLeftAnchor(starter, 0.0);
        starter.setPositionY(550 - (starter.getImage().getHeight())); //should be 550 - (starter.getImage().getHeight()) ??
        starter.setPositionX(10);
        backgroundElements.getChildren().add(starter);

        ImageView img = new ImageView(getClass().getResource("../images/labtrapBackgroundCombined.png").toExternalForm());
        background.getChildren().add(img);
        backgroundElements.setPrefHeight(550);
        backgroundElements.setMaxHeight(550);

        this.getChildren().addAll(background, backgroundElements);

        buttons = main.getButtonList();
        floorTiles = main.getFloorList();
        walls = main.getWallList();
        exits = main.getExitPortals();

        if (!builderMode) {
            restorePieces();
        } else {
            main.addFloor(starter);
            floorTiles = main.getFloorList();
            setMouseListener();
        }
    }

    public ImageView getBackgroundImage() {
        return (ImageView) background.getChildren().get(0);
    }

    private void restorePieces() {
        for (int i = 1; i < floorTiles.size(); i++) {
            AnchorPane.setTopAnchor(floorTiles.get(i), floorTiles.get(i).getPositionY());
            AnchorPane.setLeftAnchor(floorTiles.get(i), floorTiles.get(i).getPositionX());
            backgroundElements.getChildren().add(floorTiles.get(i));
        }
        for (Wall w : walls) {
            AnchorPane.setTopAnchor(w, w.getPositionY());
            AnchorPane.setLeftAnchor(w, w.getPositionX());
            backgroundElements.getChildren().add(w);
        }
        for (PortalButton b : buttons) {
            AnchorPane.setTopAnchor(b, b.getPositionY());
            AnchorPane.setLeftAnchor(b, b.getPositionX());
            backgroundElements.getChildren().add(b);
        }
        for (ExitPortal e : exits) {
            AnchorPane.setTopAnchor(e, e.getPositionY());
            AnchorPane.setLeftAnchor(e, e.getPositionX());
            backgroundElements.getChildren().add(e);
        }
    }

    private void setMouseListener() {
        this.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        this.setOnMouseClicked(e -> {
            if (pieceKey != null ) {
                if (pieceKey.equals("w")) {
                    lastPiece = new Wall();
                    //lastPieceList = walls;
                    main.addWall((Wall) lastPiece);
                    walls = main.getWallList();
                } else if (pieceKey.equals("f")) {
                    lastPiece = new Floor();
                    main.addFloor((Floor) lastPiece);
                    floorTiles = main.getFloorList();
                } else if (pieceKey.equals("b")) {
                    lastPiece = new PortalButton();
                    main.addButton((PortalButton) lastPiece);
                    buttons = main.getButtonList();
                } else if (pieceKey.equals("e")) {
                    lastPiece = new ExitPortal();
                    main.addExit((ExitPortal) lastPiece);
                    exits = main.getExitPortals();
                }
                AnchorPane.setTopAnchor(lastPiece, mouseY);
                AnchorPane.setLeftAnchor(lastPiece, mouseX);
                lastPiece.setPositionX(mouseX);
                lastPiece.setPositionY(mouseY);
                backgroundElements.getChildren().add(lastPiece);
            }
        });
    }

    public void setPieceSelector(KeyEvent e) {
        KeyCode k = e.getCode();
        if (k.getName().toLowerCase().equals("u")) {
            if (pieceKey != null) {
                if (pieceKey.equals("f") && floorTiles.size() > 0) {
                    floorTiles.remove(floorTiles.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
                if (pieceKey.equals("w") && walls.size() > 0) {
                    walls.remove(walls.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
                if (pieceKey.equals("b") && buttons.size() > 0) {
                    buttons.remove(buttons.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
                if (pieceKey.equals("e") && exits.size() > 0) {
                    exits.remove(exits.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
            }
        } else {
            pieceKey = k.getName().toLowerCase();
        }
    }
}
