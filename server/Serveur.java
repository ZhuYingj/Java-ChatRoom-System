package server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import environment.utils;
import javafx.util.Pair;
import server.src.ClientHandler;

public class Serveur {
    private static ServerSocket Listener; 

    public static void main(String[] args) throws Exception {
        int clientNumber = 0;
        Pair<String, String> serverInfo = new Pair<String, String>("", "");
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
        String ip = serverInfo.getKey();
        int port = Integer.parseInt(serverInfo.getValue());
        InetAddress serverIP = InetAddress.getByName(ip);
    
        Listener.bind(new InetSocketAddress(serverIP, port));
        System.out.format("The server is running on %s:%d%n", ip, port);
        try {
            while (true) {
                new ClientHandler(Listener.accept(), clientNumber++).start();
            }
        } finally {
            Listener.close();
        }
    }
}