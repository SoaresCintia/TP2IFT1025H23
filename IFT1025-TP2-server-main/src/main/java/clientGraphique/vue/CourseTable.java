package clientGraphique.vue;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseTable extends Application {

    private TableView<Course> table;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create the table columns
        TableColumn<Course, String> codeColumn = new TableColumn<>("Code");
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

        TableColumn<Course, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("course"));

        // create the table and add the columns
        table = new TableView<>();
        table.getColumns().addAll(codeColumn, courseColumn);

        // add some sample data to the table
        ObservableList<Course> courses = FXCollections.observableArrayList(
                new Course("CSE101", "Introduction to Computer Science"),
                new Course("CSE201", "Data Structures and Algorithms"),
                new Course("CSE301", "Database Systems"),
                new Course("CSE401", "Software Engineering"));
        table.setItems(courses);

        // create the main layout
        VBox root = new VBox();
        root.getChildren().add(table);

        // set the scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Course Table");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Course class for table data
    public static class Course {
        private String code;
        private String course;

        public Course(String code, String course) {
            this.code = code;
            this.course = course;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCourse() {
            return course;
        }

        public void setCourse(String course) {
            this.course = course;
        }
    }
}
