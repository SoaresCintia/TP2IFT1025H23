package clientGraphique.vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Vue {

    public static class Intgraph extends Application {

        @Override
        public void start(Stage primaryStage) {
            // TODO Auto-generated method stub
            VBox root = new VBox();
            Scene scene = new Scene(root, 500, 500);

            primaryStage.show();

        }

    }

    public static void main(String[] args) {
        Application.launch(Intgraph.class);
    }
}