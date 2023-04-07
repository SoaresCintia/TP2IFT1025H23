import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final String CoursFilePath = "data/cours.txt";
    private static final String InscriptionFilePath = "data/inscription.txt";
    private static List <Cours> coursList;
    private static ServerSocket serverSocket;

    private static void main (String [] args){
        try{

            coursList= Cours.loadCoursFromFile (CoursFilePath);

            severSocket  = new ServerSocket(8888);
            System.out.println("Serveur a démarré sur le port 8888");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Un client s'est connecté:" + clientSocket);

                ClientHandler clientHandler = NEW ClientHandler (clientSocket, coursList, InscriptionFilePath);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }        
}

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final List <Cours> coursList;
    private final String inscriptionFilePath;

    public ClientHandler (Socket clientSocket,List <Cours>, coursList, String inscriptionFilePath){ 
        this.clientSocket = clientSocket;
        this.coursList = coursList;
        this.inscriptionFilePath = inscriptionFilePath;
    }

    @Override
    public void run (){
        try{
           BufferedReader in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream()));  
           PrintWriter out = new PrintWriter (clientSocket.getOutputStream(), true);

           String requeteClient = in.readLine();
           System.out.println("Requête du client: " + requeteClient);

           switch (requeteClient){
               case "charger":

                    StringBuilder coursStringBuilder = new StringBuilder();
                    for (Cours cours : coursList) {
                    coursStringBuilder.append(cours.getCodeCours()).append("\t").append(cours.getNom()).append("\n");
                    }
                    out.println(coursStringBuilder.toString());
                    break;

                case "inscription":
                    String[] InfoInscription = in.readLine().split ("\t");
                    String session = InfoInscription[0];
                    String codeCours = InfoInscription[1];
                    String matricule = InfoInscription[2];
                    String prenom = InfoInscription[3];
                    String nom = InfoInscription[4];
                    String email = InfoInscription[5];

                    Cours cours = Cours.findCoursByCodeCoursEtSession (codeCours, session, coursList);
                    if (cours==null){
                        out.println ("Le code de cours spécifié n'est pas disponible pour cette session");
                    }else{
                        Inscription inscription = new Inscription (session, codeCours, matricule, prenom, nom, email);
                        inscription.saveToFile (inscriptionFilePath);
                        out.println ("Félicitations! Inscription réussie de "+ prenom+ "au cours" + codeCours+".");
                    }
                    break;
                default:
                    System.out.println ("Commande client invalide.");
                    break;
        }
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("Client déconnecté: " + clientSocket);
    } catch (IOException e) {
        e.printStackTrace();
    }
 } 