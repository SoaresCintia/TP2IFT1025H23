package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Serveur
 */
public class Server {
    /**
     * 
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * Constructeur
     * 
     * @param port
     * @throws IOException
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents); // c'est quoi :: ?
    }

    /**
     * Ajoute ..
     * 
     * @param h
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * 
     * @param cmd
     * @param arg
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * ouvre la connection pour attandre un client
     */

    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);

        }

    }

    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1,
                parts.length));
        return new Pair<>(cmd, args);
    }

    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     * Lire un fichier texte contenant des informations sur les cours et les
     * transofmer en liste d'objets 'Course'.
     * La méthode filtre les cours par la session spécifiée en argument.
     * Ensuite, elle renvoie la liste des cours pour une session au client en
     * utilisant l'objet 'objectOutputStream'.
     * La méthode gère les exceptions si une erreur se produit lors de la lecture du
     * fichier ou de l'écriture de l'objet dans le flux.
     * 
     * @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            FileReader infoCours = new FileReader(
                    "src/main/java/server/data/cours.txt");

            BufferedReader reader = new BufferedReader(infoCours);

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(" ");

                if (parts[2].equals(arg)) {

                    courses.add(new Course(parts[1], parts[0], parts[2]));
                }
            }

            reader.close();

        } catch (IOException ex) {
            System.out.println("Erreur dans l'ouverture du fichier");
        }

        try {
            objectOutputStream.writeObject(courses);

        } catch (IOException ex) {
            System.out.println("Erreur à l'écriture");
            ex.printStackTrace();

        }

    }

    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant
     * 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     * La méthode gére les exceptions si une erreur se produit lors de la lecture de
     * l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {

        try {

            String session = (String) objectInputStream.readObject();
            RegistrationForm registrationForm = (RegistrationForm) objectInputStream.readObject();

            FileWriter fw = new FileWriter("src/main/java/server/data/inscription.txt");

            BufferedWriter writer = new BufferedWriter(fw);

            String s = "";
            s = session + " " + registrationForm.getCourse().getCode() + " " + registrationForm.getMatricule() + " "
                    + registrationForm.getPrenom()
                    + " " + registrationForm.getNom() + " " + registrationForm.getEmail();

            // comment ecrire seulement a la fin
            writer.append(s);
            writer.close();

            // FileOutputStream fileOutputStream = new FileOutputStream("inscription.txt");
            // DataOutputStream output = new DataOutputStream(fileOutputStream);

            // System.out.println(registrationForm);
            // output.writeUTF(registrationForm.toString());

            // output.close();

        } catch (ClassNotFoundException e) {
            System.out.println("La classe lu n'existe pas dans le programme");
        } catch (IOException e) {
            System.out.println("Erreur à la lecture ou à ecriture du fichier");
        }

    }

}
