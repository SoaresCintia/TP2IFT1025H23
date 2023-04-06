package server;

import java.io.Serializable;

public class Course implements Serializable {
    private String sigle;
    private String nom;
    private String session;

    public Course(String sigle, String nom, String session) {
        this.nom = nom;
        this.session = session;
        this.sigle = sigle;
    }

    public String getNom() {
        return nom;
    }

    public String getSession() {
        return session;
    }

    public String getSigle() {
        return sigle;
    }

    public String toString() {
        return sigle + " " + nom + " " + session + "\n";
    }

}
