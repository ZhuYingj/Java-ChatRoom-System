import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import environment.Utils;
import environment.Pair;
import src.clientUtils;

public class Client {
    private static Pair<String, String> serverInfo = new Pair<String, String>("", "");
    private static boolean isConnected = false;
    private static boolean isLoggedIn = false;
    private static Socket socket;
    public static void main(String[] args) throws Exception {

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
        System.out.format("Server launched on [%s:%d]", ip, port);

        DataInputStream inServer = new DataInputStream(socket.getInputStream());
        String helloMessageFromServer = inServer.readUTF();
        System.out.println("\n" + helloMessageFromServer);

        while(!isLoggedIn) {
            String serverResponse = clientUtils.login(socket);
            String history;
            if (serverResponse.equals("You are connected") || serverResponse.contains("création du compte")) {
                if (serverResponse.contains("création du compte")) {
                    String connectedMessage = inServer.readUTF();
                    System.out.println(connectedMessage);
                }
                isLoggedIn = true;
                System.out.print("Voici l'historique des derniers messages :\n");
                history = inServer.readUTF();
                System.out.println(history);
            }
        }

        Thread messageReceiverThread = new Thread(() -> {
            try {
                while (isLoggedIn) {
                    String serverMessage = inServer.readUTF();
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                
            }
        });
        messageReceiverThread.start();
        
        while (isLoggedIn) {
           String message = clientUtils.sendMessage(socket);
           if(message.equalsIgnoreCase("exit")) {
            isLoggedIn = false;
           }
        }
            
        socket.close();
       
    }
}