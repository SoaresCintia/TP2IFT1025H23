package server;

public class TestServer {
    public final static int PORT = 1337;

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();//
            System.out.println("Server is running...");
            String s = "Automne";
            server.handleLoadCourses(s);
            // server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
