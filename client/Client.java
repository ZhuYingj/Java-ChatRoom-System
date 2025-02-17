
import java.io.DataInputStream;
import java.net.Socket;

import environment.serverPort;
// Application client
public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        socket = new Socket(serverPort.serverAddress, serverPort.serverPort);
        System.out.format("Serveur lancé sur [%s:%d]", serverPort.serverAddress, serverPort.serverPort);

        // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
        DataInputStream in = new DataInputStream(socket.getInputStream());
        // Attente de la réception d'un message envoyé par le, server sur le canal
        String helloMessageFromServer = in.readUTF();
        System.out.println(helloMessageFromServer);
        // fermeture de La connexion avec le serveur
        socket.close();
        }
}