package server;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientSimple {

    public final static int PORT = 1337;

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("127.0.0.1", PORT);

            OutputStreamWriter os = new OutputStreamWriter(clientSocket.getOutputStream());

            BufferedWriter writer = new BufferedWriter(os);

            writer.flush();
            writer.close();

            // F1: fonctionnalité qui permet au client de récupérer la liste
            // des cours disponibles pour une session donnée. Le client envoie une requête
            // charger au serveur. Le serveur doit récupérer la liste des cours du fichier
            // cours.txt et l’envoie au client. Le client récupère les cours et les affiche.

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
