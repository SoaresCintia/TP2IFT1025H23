    /**
     Lire un fichier texte contenant des informations sur les cours et les transofmer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        try {
           List <Course> courses = new ArrayList <Course>();
           BufferedReader reader = new BufferedReader (new FileReader ("courses.txt"));
           String line = reader.readLine();
           while (line !=null) {
             Course course= Course.parse (line);
             if (course,getSession().equals(arg)){
                courses.add(course);

             }
             reader.close();
             objectOutputStream.writeObject(courses);
           } catch (IOException e) {
                e.printStackTrace();
             }
        }


    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        try {
            RegistrationForm form= (RegistrationForm) objectInputStream.readObject();
            BufferedWriter writer = new BufferedWriter (new FileWriter ("inscription.txt", true));
            writer.write(form.toString());
            writer.newlINE();
            writer.close();
            objectOutputStream.writeObject("Inscription enregistrée!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }