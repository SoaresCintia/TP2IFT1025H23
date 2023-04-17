package client;

import java.util.ArrayList;
import java.util.Scanner;

import server.models.Course;
import server.models.RegistrationForm;

public class ClientInterfaceSimple {

    private Scanner scanner = new Scanner(System.in);

    private String session;

    public String initialisation() {

        System.out.println("***Bienvenue au portail d'inscription de cours de l'UdeM***");

        displaySessions();

        return session;
    }

    private void displaySessions() {
        System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:");
        System.out.println("1. Automne");
        System.out.println("2. Hiver");
        System.out.println("3. Ete");
        System.out.print("Choix: ");

        int choix = scanner.nextInt();

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

    }

    public Integer chooseCoursesInscription() {
        System.out.println("Choix:");

        System.out.println("1. Consulter les cours offerts pour une autre session");
        System.out.println("2. Inscription à un cours");

        System.out.print("Choix: ");

        scanner = new Scanner(System.in);
        return scanner.nextInt();

    }

    public RegistrationForm enterInformation(ArrayList<Course> courses) {

        scanner = new Scanner(System.in);

        System.out.print("Veuillez saisir votre prénom: ");
        String firstName = scanner.nextLine();

        System.out.print("Veuillez saisir votre nom: ");
        String lastName = scanner.nextLine();

        System.out.print("Veuillez saisir votre email: ");
        String email = scanner.nextLine();

        System.out.print("Veuillez saisir votre matricule: ");
        String matriculation = scanner.nextLine();

        System.out.print("Veuillez saisir le code du cours: ");
        String courseCode = scanner.nextLine();

        RegistrationForm registrationForm = new RegistrationForm(firstName, lastName, email, matriculation, null);

        Course course;
        for (int i = 0; i < courses.size(); i++) {

            course = courses.get(i);
            if (course.getCode().equals(courseCode)) {
                registrationForm.setCourse(course);
                break;
            }
        }
        return registrationForm;
    }

    // public void displayLastMessage(Boolean success, String firstName, String
    // courseCode) {
    // if (success) {
    // System.out.println("Félicitations! Inscription réussie de " + firstName + "
    // au cours " + courseCode);
    // } else {
    // System.out.println("Échec dans l'inscription pourquoi ?");
    // }
    // }

}
