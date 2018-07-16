package exercise2;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PongFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5.5);
        pane.setVgap(5.5);

        pane.add(new Label("P O N G !"), 0,0);
        Button btStart = new Button("Start Game");
        pane.add(btStart, 0, 1);
        Button btCancel = new Button("Exit");
        pane.add(btCancel, 1, 1);

        Scene scene = new Scene(pane);
        primaryStage.setTitle("Pong!");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
