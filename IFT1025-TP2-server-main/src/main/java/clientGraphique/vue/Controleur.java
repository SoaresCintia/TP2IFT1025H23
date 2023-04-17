package clientGraphique.vue;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import server.models.Course;
import server.models.RegistrationForm;

public class Controleur {

    // public Controleur(Model model, Vue vue) {
    // }

    // public void submit(TextField firstName, TextField lastName, TextField email,
    // TextField matricule) {

    // }

    public final static int PORT = 1337;
    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";

    private ArrayList<Course> courses = new ArrayList<Course>();
    private Course course; // = new Course(null, null, null);
    private String courseCode;
    private RegistrationForm registrationForm; // = new RegistrationForm(null, null, null, null, course);
    private String session;

    private Alert alert;

    private Model model;

    private ObservableList<Course> coursesVue;

    Text text;

    public Controleur(ObservableList<Course> coursesVue, Alert alert) {
        session = "Automne";
        this.coursesVue = coursesVue;
        this.alert = alert;
    }

    public void chooseCourse() {

        sendRequest(LOAD_COMMAND);
        this.updateCoursesVue();
        coursesVue.setAll(courses);
        this.courseCode = "";
        // System.out.println(courseCode);

    }

    private void updateCoursesVue() {
        this.coursesVue.setAll(courses);
    }

    public void doInscription(String firstName, String lastName, String email, String matricule) {

        registrationForm = new RegistrationForm(firstName, lastName, email, matricule, null);

        for (int i = 0; i < courses.size(); i++) {

            if (courses.get(i).getCode().equals(courseCode)) {
                registrationForm.setCourse(courses.get(i));
                break;
            }
        }
        sendRequest(REGISTER_COMMAND);
    }

    private void sendRequest(String request) {

        try {
            model = new Model(PORT);//

            if (request.equals(LOAD_COMMAND)) {
                courses = model.charger(session, courses);
            } else {
                Boolean succes;
                succes = model.inscription(registrationForm, session);
                if (succes) {
                    alert.setContentText("Félicitations! Inscription réussie de " + registrationForm.getPrenom() +
                            "au cours " + registrationForm.getCourse().getCode());
                } else {
                    alert.setContentText("Vous devez choisir un cours.");

                }
                alert.showAndWait();
            }

        } catch (Exception e) {
            System.out.println("erreur : création du modele");
            e.printStackTrace();
        }
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

}