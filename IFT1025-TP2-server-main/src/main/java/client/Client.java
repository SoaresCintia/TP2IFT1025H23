package client;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import server.models.Course;

public class Client {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final Socket clientSocket;
    private ArrayList<Course> courses;

    private Scanner scanner;

    public Client(int port) throws UnknownHostException, IOException {
        clientSocket = new Socket("127.0.0.1", port);
    }

    public void charger() throws IOException {
        System.out.println("***Bienvenue au portail d'inscription de cours de l'UdeM***");

        displaySessions();

        displayCourses(courses);

        chooseCoursesInscription();

    }

    private void sendServer(String session) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        String arg = LOAD_COMMAND + " " + session;
        objectOutputStream.writeObject(arg);

        try {

            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            courses = (ArrayList<Course>) objectInputStream.readObject();

        } catch (IOException e) {
            System.out.println("Erreur à l'ouverture du fichier ou objet");
        } catch (ClassNotFoundException ex) {
            System.out.println("La classe lue n'existe pas dans le programme");
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            System.out.println("Problème dans le cast");
            ex.printStackTrace();
        }

    }

    private void displaySessions() throws IOException {
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.println("1. Automne");
        System.out.println("2. Hiver");
        System.out.println("3. Ete");
        System.out.print("Choix: ");

        scanner = new Scanner(System.in);

        int choix = scanner.nextInt();

        String session;
        switch (choix) {
            case 1:
                session = "Automne";
                break;
            case 2:
                session = "Hiver";
                break;
            default:
                session = "Ete";
                break;
        }

        System.out.println("Les cours offerts pendant la session d'" + session + " sont");

        sendServer(session);
    }

    private void displayCourses(ArrayList<Course> courses) {
        for (int i = 0; i < courses.size(); i++) {
            int j = i + 1;
            System.out.println(j + ". " + courses.get(i).getName() + "  " + courses.get(i).getCode());
        }
    }

    private void chooseCoursesInscription() throws IOException {
        System.out.println("Choix:");

        System.out.println("1. Consulter les cours offerts pour une autre session");
        System.out.println("2. Inscription à un cours");

        scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                this.displaySessions();
                break;
            default:
                this.inscription();
                break;
        }

    }

    public void inscription() {

    }

}