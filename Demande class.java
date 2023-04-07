public class Demande {
        private String session;
        private String typeDemande;
        private String codeCours;
        private String Inscription inscription;

}

        public Demande(String session, String typeDemande, String codeCours, Inscription inscription) {
            this.session = session;
            this.typeDemande = typeDemande;
            this.codeCours = codeCours;
            this.inscription = inscription;
    }

     public String getSession() {
        return session;
    }

    public String getTypeDemande() {
        return typeDemande;
    }

    public String getCodeCours() {
        return codeCours;
    }

     public Inscription getInscription() {
        return inscription;
    }
}
