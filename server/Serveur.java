package server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import environment.serverConst;
import javafx.util.Pair;
import server.src.ClientHandler;
import server.src.utils;

public class Serveur {
    private static ServerSocket Listener; 

    public static void main(String[] args) throws Exception {
        int clientNumber = 0;
        Pair<String, String> serverInfo ;
        boolean isServerInfoValid = false;

        while (!isServerInfoValid) {
            serverInfo = utils.getServerInfo();
            if (utils.isServerIpValid(serverInfo.getKey()) && utils.isServerPortValid(serverInfo.getValue())) {
                isServerInfoValid = true;
            } else {
                System.out.println("The serverIp or the serverPort is invalid, try again.");
            }
        }

        Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(serverConst.serverAddress);
        
        Listener.bind(new InetSocketAddress(serverIP, serverConst.serverPort));
        System.out.format("The server is running on %s:%d%n", serverConst.serverAddress, serverConst.serverPort);
        try {
            while (true) {
                new ClientHandler(Listener.accept(), clientNumber++).start();
            }
        } finally {
            Listener.close();
        }
    }
}