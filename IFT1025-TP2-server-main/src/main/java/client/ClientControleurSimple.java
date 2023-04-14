package client;

import java.util.ArrayList;

import server.models.Course;
import server.models.RegistrationForm;

public class ClientControleurSimple {

    public final static int PORT = 1337;

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";

    private ArrayList<Course> courses = new ArrayList<Course>();

    private String session;

    private Client client;

    private RegistrationForm registrationForm;

    ClientInterfaceSimple interfaceSimple = new ClientInterfaceSimple();

    public ClientControleurSimple() {
        chooseCourse();
        registrationForm = interfaceSimple.enterInformation(courses);
        // System.out.println(registrationForm.getCourse().getCode());
        sendRequest(REGISTER_COMMAND);

    }

    private void chooseCourse() {

        while (true) {
            session = interfaceSimple.initialisation();
            sendRequest(LOAD_COMMAND);

            client.displayCourses(courses);
            int choix = interfaceSimple.chooseCoursesInscription();
            if (choix == 2) {
                break;
            }
        }
    }

    private void sendRequest(String request) {

        try {
            client = new Client(PORT);//

            if (request.equals(LOAD_COMMAND)) {
                courses = client.charger(session, courses);
            } else {
                client.inscription(registrationForm, session);
                // TODO ne pas oublier de faire les validations
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
