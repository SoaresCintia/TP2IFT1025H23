public class Cours {
    private String codeCours;
    private String nomCours;
    private String session;

    public Cours (String codeCours, String nomCours,String session) {
        this.codeCours = codeCours;
        this.nomCours = nomCours;
        this.session = session;
    }

    public String getCodeCours() {
        return codeCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public String getSession() {
        return session;
    }
}
