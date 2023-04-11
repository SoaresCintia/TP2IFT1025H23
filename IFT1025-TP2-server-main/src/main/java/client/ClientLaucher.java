package client;

public class ClientLaucher {

    public final static int PORT = 1337;

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client(PORT);//

            client.charger();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}