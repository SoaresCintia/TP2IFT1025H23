import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private static final String ServerAddress= "localhost";
    private static final int ServerPort = 5000;

    private static void main (String [] args){
        try{
            Scanner scanner= new Scanner(System.in);

            Socket socket = new Socket(ServerAddress, ServerPort);
            System.out.println("Vous êtes connecté au serveur");

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true){
                System.out.println("Veuillez saisir la commande (charger/inscription): ");
                String command= scanner.nextLine();

                if (command.equals("charger")){
                    System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
                    String session= scanner.nextLine();

                   objectOutputStream.writeObject (new Demande (session,null));
                   objectOutputStream.flush();

                   List <Cours> cours = (List <Cours>) objectInputStream.readObject();
                   System.out.println("Les Cours offerts pendant session d'"+ session+" sont:");
                   for (Cours cours: cours){
                        System.out.println("cours.getCode()+"\t"+cours.getName());
                    }
                    
                    else if (command.equals ("inscription")){
                        System.out.println ("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours: ");
                        String session= scanner.nextLine();

                        objectOutputStream.writeObject (new Demande (session,null));
                        objectOutputStream.flush();
                        List <Cours> cours = (List <Cours>) objectInputStream.readObject();

                        System.out.println ("Veuillez saisir le code du cours: ");
                        String codeCours= scanner.nextLine();
                        System.out.println ("Veuillez saisir votre matricule: ");
                        String matricule= scanner.nextLine();
                        System.out.println ("Veuillez saisir votre prenom: ");
                        String prenom= scanner.nextLine();
                        System.out.println ("Veuillez saisir votre nom: ");
                        String nom= scanner.nextLine();
                        System.out.println ("Veuillez saisir votre email: ");
                        String email= scanner.nextLine();

                        boolean isCoursValide= false;
                        for (Cours cours : cours){
                            if (cours.getCode().equals(codeCours)){
                                isCoursValide= true;
                                break;
                            }
                        }

                        if (isCoursValide){
                            objectOutputStream.writeObject (new Demande (session,new Inscription (codeCours, matricule, prenom, nom, email)));
                            objectOutputStream.flush();
                        
                            String message= (String) objectInputStream.readObject();
                            System.out.println (message);
                        } else {
                            System.out.println ("Code de cours invalide");
                        }
                    } else {
                        System.out.println ("Commande invalide");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }        
 








                    }



                }
                
            }
        }
    }
}