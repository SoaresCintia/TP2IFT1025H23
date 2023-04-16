package clientGraphique.vue;

import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import server.models.Course;
import server.models.RegistrationForm;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/*
 * Dans cette classe nous definissons les éléments graphiques de notre
 * application.
 * 
 * Elle sert également comme le point d'entrée de l'application.
 */
public class Vue extends Application {

    // create a new terminal and do mvn javafx:run

    private TableView<Course> table;

    @Override
    public void start(Stage primaryStage) {

        Controleur controleur = new Controleur();

        ArrayList<Course> courses;

        String session = "Automne";
        courses = controleur.chooseCourse();
        System.out.println(courses.size());

        BorderPane root = new BorderPane();

        // Create the left panel with the course list
        VBox coursePane = new VBox();

        // create the table columns
        TableColumn<Course, String> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Course, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        // create the table and add the columns
        table = new TableView<>();
        table.getColumns().addAll(codeColumn, courseColumn);

        // add some sample data to the table
        ObservableList<Course> coursesVue = FXCollections.observableArrayList(courses);
        table.setItems(coursesVue);

        coursePane.getChildren().addAll(table);

        String[] sessions = { "Automne", "Hiver", "Éte" };

        ChoiceBox<String> optionSessions = new ChoiceBox<String>();

        optionSessions.getItems().setAll(sessions);

        optionSessions.setOnAction(event -> controleur.setSession(optionSessions.getValue()));

        coursePane.getChildren().addAll(optionSessions);

        Button loadButton = new Button("Charger");
        loadButton.setOnAction(event -> controleur.chooseCourse());

        // le controleur doit dire a la vue de charcher les cours
        coursePane.getChildren().addAll(loadButton);

        // Create the grid pane for the form layout
        GridPane formPane = new GridPane();
        // gridPane.setPadding(new Insets(10));
        formPane.setHgap(6);
        formPane.setVgap(6);

        // Add the form fields
        Label firstNameLabel = new Label("Prénom:");
        TextField firstName = new TextField();
        formPane.addRow(0, firstNameLabel, firstName);

        Label lastNameLabel = new Label("Nom:");
        TextField lastName = new TextField();
        formPane.addRow(0, lastNameLabel, lastName);

        Label emailLabel = new Label("Email:");
        TextField email = new TextField();
        formPane.addRow(1, emailLabel, email);

        Label matriculeLabel = new Label("Matricule:");
        TextField matricule = new TextField();
        formPane.addRow(2, matriculeLabel, matricule);

        // Add the submit button
        Button submitButton = new Button("envoyer");

        submitButton.setOnAction(e -> {
            // Handle form submission here
            // controleur.submit(firstName, lastName, email, matricule);
        });
        formPane.addRow(3, submitButton);

        root.setLeft(coursePane);
        root.setRight(formPane);

        // Set up the scene and show the stage
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.setTitle("Course List App");
        primaryStage.show();

        // primaryStage.setTitle("Formulaire d'inscription");

    }

}
