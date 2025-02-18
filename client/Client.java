import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import environment.Utils;
import javafx.util.Pair;
import src.clientUtils;

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        Pair<String, String> serverInfo = new Pair<String, String>("", "");
        boolean isConnected = false;

        while (!isConnected) {
            serverInfo = Utils.getServerInfo();
            if (Utils.isServerIpValid(serverInfo.getKey()) && Utils.isServerPortValid(serverInfo.getValue())) {
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

        DataInputStream inServer = new DataInputStream(socket.getInputStream());
        String helloMessageFromServer = inServer.readUTF();
        System.out.println(helloMessageFromServer);

        boolean isLoggedin = false;
        while(!isLoggedin) {
            String serverResponse = clientUtils.login(socket);
            if (serverResponse == "You are connected") {
                isLoggedin = true;
            }
        }
        
        socket.close();
        }
}