
import java.io.DataInputStream;
import java.net.Socket;

import environment.environment;
// Application client
public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        socket = new Socket(environment.serverAddress, environment.serverPort);
        System.out.format("Serveur lancé sur [%s:%d]", environment.serverAddress, environment.serverPort);
        
        // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
        DataInputStream in = new DataInputStream(socket.getInputStream());
        // Attente de la réception d'un message envoyé par le, server sur le canal
        String helloMessageFromServer = in.readUTF();
        System.out.println(helloMessageFromServer);
        // fermeture de La connexion avec le serveur
        socket.close();
        }
}