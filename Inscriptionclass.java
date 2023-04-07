public class Inscription {
    private String session;
    private String codeCours;
    private int matricule;
    private String prenom;
    private String nom;
    private String email;


    public Inscription (String session, String codeCours, int matricule, String prenom, String nom, String email ) {
        this.session = session;
        this.codeCours = codeCours;
        this.matricule = matricule;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public String getSession() {
        return session;
    }

     public String getCodeCours() {
        return codeCours;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getPrenom() {
        return prenom;
    }

     public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}

