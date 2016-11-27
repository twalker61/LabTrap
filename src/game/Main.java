package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by twalker61
 */
public class Main extends Application {

    private Stage stage;
    private GamePane root;
    private StackPane intro;

    @Override
    public void start(Stage primaryStage) throws Exception{

        root = new GamePane();
        intro = new StackPane();
        intro.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreenHoverOff.png").toExternalForm())));

        intro.setOnMouseClicked(e -> {
            stage.setScene(new Scene (root, 1067, 600));
        });
        intro.setOnMouseMoved(e -> {
            if (e.getY() > 309 && e.getY() < 387 && e.getX() > 342 && e.getX() < 734) {
                intro.getChildren().remove(0);
                intro.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreenHoverOn.png").toExternalForm())));
            } else {
                intro.getChildren().remove(0);
                intro.getChildren().add(new ImageView(new Image(getClass().getResource("../images/introScreenHoverOff.png").toExternalForm())));
            }
        });

        stage = primaryStage;
        primaryStage.setTitle("Lab Trap!");
        primaryStage.setScene(new Scene(intro, 1067, 600));
        primaryStage.show();

        root.getCanvas().requestFocus();

    }

    public Parent welcomeScene() {
        VBox vbox = new VBox(30);
        Button startButton = new Button();
        startButton.setText("START");
        Button instructionButton = new Button();
        instructionButton.setText("INSTRUCTION");
        vbox.getChildren().addAll(startButton, instructionButton);
        vbox.setAlignment(Pos.CENTER);
        startButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                stage.setScene(new Scene (root, 1067, 600));
            }
        });
        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
