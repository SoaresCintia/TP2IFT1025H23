package clientGraphique.vue;

import java.util.ArrayList;

import javafx.collections.ObservableList;
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
    private Course course = new Course(null, null, null);
    private String courseCode;
    private RegistrationForm registrationForm = new RegistrationForm(null, null, null, null, course);
    private String session;

    private Model model;

    private ObservableList<Course> coursesVue;

    Text text;

    public Controleur(ObservableList<Course> coursesVue, String courseCode) {
        session = "Automne";
        this.coursesVue = coursesVue;
        this.courseCode = courseCode;
    }

    public void chooseCourse() {

        sendRequest(LOAD_COMMAND);
        this.updateCoursesVue();
        coursesVue.setAll(courses);

    }

    private void updateCoursesVue() {
        this.coursesVue.setAll(courses);
    }

    public void doInscription(String firstName, String lastName, String email, String matricule) {
        this.findCourse();
        registrationForm.setPrenom(firstName);
        registrationForm.setNom(lastName);
        registrationForm.setEmail(email);
        registrationForm.setMatricule(matricule);
        registrationForm.setCourse(course);
        sendRequest(REGISTER_COMMAND);
    }

    private void findCourse() {
        for (int i = 0; i < courses.size(); i++) {
            course = courses.get(i);
            if (courses.get(i).getCode() == courseCode) {
                break;
            }
        }
    }

    private void sendRequest(String request) {

        try {
            model = new Model(PORT);//

            if (request.equals(LOAD_COMMAND)) {
                courses = model.charger(session, courses);
            } else {
                model.inscription(registrationForm, session);
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