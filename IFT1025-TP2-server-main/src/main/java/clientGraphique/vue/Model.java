package clientGraphique.vue;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import server.models.Course;
import server.models.RegistrationForm;

public class Model {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final Socket clientSocket;

    public Model(int port) throws UnknownHostException, IOException {
        clientSocket = new Socket("127.0.0.1", port);
    }

    public ArrayList<Course> charger(String session, ArrayList<Course> courses) throws IOException {

        OutputStream outputStream = clientSocket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        String arg = LOAD_COMMAND + " " + session;
        objectOutputStream.writeObject(arg);

        try {

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            courses = (ArrayList<Course>) objectInputStream.readObject();

            // System.out.println(courses.get(0).getCode());

        } catch (IOException e) {
            System.out.println("Erreur à l'ouverture du fichier ou objet");
        } catch (ClassNotFoundException ex) {
            System.out.println("La classe lue n'existe pas dans le programme");
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            System.out.println("Problème dans le cast");
            ex.printStackTrace();
        }
        return courses;

    }

    // public void displayCourses(ArrayList<Course> courses) {
    // for (int i = 0; i < courses.size(); i++) {
    // int j = i + 1;
    // System.out.println(j + ". " + courses.get(i).getName() + " " +
    // courses.get(i).getCode());
    // }
    // }

    public void inscription(RegistrationForm registrationForm, String session)
            throws IOException, ClassNotFoundException {

        OutputStream outputStream = clientSocket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        objectOutputStream.writeObject(REGISTER_COMMAND);

        objectOutputStream.writeObject(session);

        objectOutputStream.writeObject(registrationForm);

        InputStream inputStream = clientSocket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        String message = (String) objectInputStream.readObject();

        System.out.println(message);

    }

}