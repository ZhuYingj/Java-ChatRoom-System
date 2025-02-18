import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import environment.utils;
import javafx.util.Pair;

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        Pair<String, String> serverInfo = new Pair<String, String>("", "");
        boolean isConnected = false;

        while (!isConnected) {
            serverInfo = utils.getServerInfo();
            if (utils.isServerIpValid(serverInfo.getKey()) && utils.isServerPortValid(serverInfo.getValue())) {
                try {
                    socket = new Socket(serverInfo.getKey(), Integer.parseInt(serverInfo.getValue()));
                    isConnected = true;
                } catch (IOException e) {
                    System.out.println("There is no server on the ip address or the port given, try gain.");
                }
            } else {
                System.out.println("The format of the serverIp or the serverPort is invalid, try again.");
            }
        }
        String ip = serverInfo.getKey();
        int port = Integer.parseInt(serverInfo.getValue());
        
        System.out.format("Serveur lancé sur [%s:%d]", ip, port);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        
        String helloMessageFromServer = in.readUTF();
        System.out.println(helloMessageFromServer);
        
        socket.close();
        }
}