package game;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;

import java.util.List;
import java.util.Stack;

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
    private GameElement hoverImage;
    private String pieceKey;
    private Stack<String> pieceKeys;
    private boolean builderMode;
    private static Main main;
    private AudioClip buildSound;

    public GameScreen(Main m, boolean mode) {
        builderMode = mode;
        main = m;
        pieceKeys = new Stack<>();
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
        buildSound = new AudioClip(getClass().getResource("../sounds/buildSound.wav").toExternalForm());

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

    private GameElement selectPiece(boolean clicked, String key) {
        if (key.equals("w")) {
            Wall w = new Wall();
            if (clicked) {
                main.addWall(w);
                walls = main.getWallList();
            }
           return w;
        }
        if (key.equals("f")) {
            Floor f = new Floor();
            if (clicked) {
                main.addFloor(f);
                floorTiles = main.getFloorList();
            }
            return f;
        }
        if (key.equals("b")) {
            PortalButton b = new PortalButton();
            if (clicked) {
                main.addButton(b);
                buttons = main.getButtonList();
            }
            return b;
        }
        if (key.equals("e")) {
            ExitPortal e = new ExitPortal();
            if (clicked) {
                main.addExit(e);
                exits = main.getExitPortals();
            }
            return e;
        }
        return null;
    }

    public void setHoverImage() {
        if (pieceKey != null) {
            hoverImage = selectPiece(false, pieceKey);
        }
        if (hoverImage != null) {
            AnchorPane.setTopAnchor(hoverImage, mouseY);
            AnchorPane.setLeftAnchor(hoverImage, mouseX);
            hoverImage.setPositionX(mouseX);
            hoverImage.setPositionY(mouseY);
            hoverImage.setHover();
            if (((GameElement)backgroundElements.getChildren().get(backgroundElements.getChildren().size() - 1)).hovering()) {
                backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
            }
            backgroundElements.getChildren().add(hoverImage);
        }
    }

    private void setMouseListener() {
        this.setOnMouseMoved(e -> {
            mouseX = e.getX();
            mouseY = e.getY();
        });

        this.setOnMouseClicked(e -> {
            if (pieceKey != null ) {
                buildSound.play();
                lastPiece = selectPiece(true, pieceKey);
                if (lastPiece != null) {
                    pieceKeys.add(pieceKey);
                    AnchorPane.setTopAnchor(lastPiece, mouseY);
                    AnchorPane.setLeftAnchor(lastPiece, mouseX);
                    lastPiece.setPositionX(mouseX);
                    lastPiece.setPositionY(mouseY);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                    backgroundElements.getChildren().add(lastPiece);
                }
            }
        });
    }

    public void updatePiece(GameElement g) {
        backgroundElements.getChildren().remove(g);
        AnchorPane.setTopAnchor(g, g.getPositionY());
        AnchorPane.setLeftAnchor(g, g.getPositionX());
        backgroundElements.getChildren().add(g);

    }

    public void setPieceSelector(KeyEvent e) {
        KeyCode k = e.getCode();
        if (k.getName().toLowerCase().equals("u")) {
            String p = null;
            if (pieceKeys.size() > 0) {
                p = pieceKeys.pop();
            }
            if (p != null) {
                if (p.equals("f") && floorTiles.size() > 0) {
                    floorTiles.remove(floorTiles.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
                if (p.equals("w") && walls.size() > 0) {
                    walls.remove(walls.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
                if (p.equals("b") && buttons.size() > 0) {
                    buttons.remove(buttons.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
                if (p.equals("e") && exits.size() > 0) {
                    exits.remove(exits.size() - 1);
                    backgroundElements.getChildren().remove(backgroundElements.getChildren().size() - 1);
                }
            }
        } else {
            pieceKey = k.getName().toLowerCase();
        }
    }
}

//Better design would have been to have a single background elements list that I add new pieces to, and in the
//gamepane, I would loop through and react to each if collision detected, and that reaction would determine all the logic
//like grounded or buttonpressed or piece chewed and what not.