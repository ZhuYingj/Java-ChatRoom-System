package server;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import environment.serverPort;
import server.src.ClientHandler;

public class Serveur {
    private static ServerSocket Listener; // Application Serveur
    public static void main(String[] args) throws Exception {
        int clientNumber = 0;
        
        Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(serverPort.serverAddress);
        
        Listener.bind(new InetSocketAddress(serverIP, serverPort.serverPort));
        System.out.format("The server is running on %s:%d%n", serverPort.serverAddress, serverPort.serverPort);
        try {
            while (true) {
                new ClientHandler(Listener.accept(), clientNumber++).start();
            }
        } finally {
            Listener.close();
        }
    }
}