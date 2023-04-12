package client;

import java.io.IOException;
import java.util.Scanner;

public class ClientLaucher {

    public final static int PORT = 1337;

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";

    public static void main(String[] args) throws IOException {

        System.out.println("***Bienvenue au portail d'inscription de cours de l'UdeM***");

        displaySessions();

    }

    static void sendRequest(String request, String session) {
        Client client;
        try {
            client = new Client(PORT);//

            if (request.equals(LOAD_COMMAND)) {
                client.charger(session);
            } else {
                client.inscription();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void displaySessions() throws IOException {
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

        sendRequest(LOAD_COMMAND, session);

        chooseCoursesInscription();
    }

    static void chooseCoursesInscription() throws IOException {
        System.out.println("Choix:");

        System.out.println("1. Consulter les cours offerts pour une autre session");
        System.out.println("2. Inscription à un cours");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displaySessions();
                break;
            default:
                sendRequest(REGISTER_COMMAND, null);
                break;
        }

    }

}