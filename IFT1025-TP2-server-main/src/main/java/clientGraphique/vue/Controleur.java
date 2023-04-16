package clientGraphique.vue;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import server.models.Course;
import server.models.RegistrationForm;

public class Controleur {

    public Controleur(Model model, Vue vue) {
    }

    public void submit(TextField firstName, TextField lastName, TextField email, TextField matricule) {

    }

    public final static int PORT = 1337;

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";

    private ArrayList<Course> courses = new ArrayList<Course>();

    private String session;

    private Model model;

    private RegistrationForm registrationForm;

    // Vue vue = new Vue();

    public Controleur() {
        session = "Automne";
    }

    public ArrayList<Course> chooseCourse() {

        // this.session = session;
        sendRequest(LOAD_COMMAND);
        return courses;

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
        System.out.println(session);
    }

}
