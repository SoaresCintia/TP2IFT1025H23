package clientGraphique.vue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Vue {

    public static class Intgraph extends Application {

        @Override
        public void start(Stage primaryStage) {

            primaryStage.setTitle("Inscription UdeM");

            BorderPane root = new BorderPane();

            Scene scene = new Scene(root, 400, 400);

            VBox vBoxLeft = new VBox();

            VBox vBoxRight = new VBox();

            root.setLeft(vBoxLeft);
            root.setRight(vBoxRight);
            // root.setCenter(new Separator(null)); vertical Separator

            TextArea textArea = new TextArea();

            HBox firstName = new HBox(textArea);

            Text texteLC = new Text("Liste des Cours");
            vBoxLeft.getChildren().add(texteLC);

            Text texteFI = new Text("Formulaire d'inscription");
            vBoxRight.getChildren().add(texteFI);

            primaryStage.setScene(scene);
            primaryStage.show();

        }

    }

    public static void main(String[] args) {
        Application.launch(Intgraph.class);
    }
}