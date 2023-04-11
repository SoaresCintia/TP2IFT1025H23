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

    public Client(int port) throws UnknownHostException, IOException {
        clientSocket = new Socket("127.0.0.1", port);
    }

    /**
     * // // F1: fonctionnalité qui permet au client de récupérer la liste
     * // // des cours disponibles pour une session donnée.
     * // // Le client envoie une requête charger au serveur.
     * // // Le serveur doit récupérer la liste des cours du fichier cours.txt et
     * // l’envoie
     * // // au client.
     * // // Le client récupère les cours et les affiche.
     * 
     * @throws IOException
     */
    public void charger() throws IOException {
        System.out.println("***Bienvenue au portail d'inscription de cours de l'UdeM***");
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.println("1. Automne");
        System.out.println("2. Hiver");
        System.out.println("3. Ete");
        System.out.print("Choix: ");

        Scanner scanner = new Scanner(System.in);

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

        OutputStream outputStream = clientSocket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

        String arg = LOAD_COMMAND + " " + session;
        objectOutputStream.writeObject(arg);

        try {

            InputStream inputStream = clientSocket.getInputStream();

            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            ArrayList<Course> courses = (ArrayList<Course>) objectInputStream.readObject();

            System.out.println(courses.size());

        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Erreur à l'ouverture du fichier ou objet");

        } catch (ClassNotFoundException ex) {
            System.out.println("La classe lue n'existe pas dans le programme");
            ex.printStackTrace();
        } catch (ClassCastException ex) {
            System.out.println("Problème dans le cast");
            ex.printStackTrace();
        }
    }

    // F2: une deuxième fonctionnalité qui permet au client de faire une demande d’
    // inscription à un cours.

    // Le client envoie une requête inscription au serveur.

    // Les informations suivantes sont données nécessaires (voir le format du
    // fichier inscription.txt ci-dessus) en arguments. Le choix du cours doit être
    // valide c.à.d le code du cours doit être présent dans la liste des cours
    // disponibles dans la session en question. Le serveur ajoute la ligne
    // correspondante au fichier inscription.txt et envoie un message de réussite au
    // client.

    // Le client affiche cemessage (ou celui de l’échec en cas d’exception).

    public void inscrire() {

    }

}