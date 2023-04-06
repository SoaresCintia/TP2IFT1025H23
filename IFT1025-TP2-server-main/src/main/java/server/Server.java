package server;

import javafx.util.Pair;
import server.models.RegistrationForm;

import java.util.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        this.addEventHandler(this::handleEvents); //
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
        // TODO: implémenter cette méthode
        // ArrayList<Course> courses = new ArrayList<Course>();

        Courses courses = new Courses();

        try {
            FileReader infoCours = new FileReader("IFT1025-TP2-server-main/src/main/java/server/data/cours.txt");

            BufferedReader reader = new BufferedReader(infoCours);

            String line;
            Course course;

            // select only line with arg
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                if (parts[2].equals(arg)) {// parts[2] has sections
                    course = new Course(parts[0], parts[1], parts[2]);
                    courses.add(course);
                }
            }

            reader.close();

        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Erreur dans l'ouverture du fichier");
        }

        try {
            FileOutputStream fileOs = new FileOutputStream("courses.dat");

            ObjectOutputStream os = new ObjectOutputStream(fileOs);

            os.writeObject(courses);

            os.close();

        } catch (IOException ex) {
            System.out.println("Erreur à l'écriture");

        }

        // Todo : renvoier la liste des cours pour une session au client en utilisant
        // l'objet 'objectOutputStream'.
    }

    /**
     * Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant
     * 'objectInputStream', l'enregistrer dans un fichier texte
     * et renvoyer un message de confirmation au client.
     * La méthode gére les exceptions si une erreur se produit lors de la lecture de
     * l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        // TODO: implémenter cette méthode

        try {
            FileInputStream fileIs = new FileInputStream("registrationForm.dat");
            ObjectInputStream is = new ObjectInputStream(fileIs);

            RegistrationForm registrationForm = (RegistrationForm) is.readObject();

            FileOutputStream fileOutputStream = new FileOutputStream("inscription.txt");

            DataOutputStream output = new DataOutputStream(fileOutputStream);

            output.writeUTF(registrationForm.toString());

            output.close();

            // FileOutputStream fileOs = new FileOutputStream("inscription.dat");

            // ObjectOutputStream os = new ObjectOutputStream(fileOs);

            // os.writeObject(registrationForm);

            // os.close();

        } catch (ClassNotFoundException e) {
            System.out.println("La classe lu n'existe pas dans le programme");
        } catch (IOException e) {
            System.out.println("Erreur à la lecture ou à ecriture du fichier");
        }

    }

}
