package server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import environment.Utils;
import environment.Pair;
import server.src.ClientHandler;
import java.util.*;

public class Serveur {
    private static ServerSocket Listener;
    public static List<ClientHandler> clientHandlers = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        int clientNumber = 0;
        Pair<String, String> serverInfo = new Pair<String, String>("", "");
        boolean isServerInfoValid = false;

        while (!isServerInfoValid) {
            serverInfo = Utils.getServerInfo();
            if (Utils.isServerIpValid(serverInfo.getKey()) && Utils.isServerPortValid(serverInfo.getValue())) {
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
                ClientHandler handler = new ClientHandler(Listener.accept(), clientNumber++);
                handler.start();
            }
        } finally {
            Listener.close();
        }
    }

    public static void emitMessage(String message, ClientHandler sender) {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }

    public static void broadCastMessage(String message, ClientHandler sender) {
        for (ClientHandler clientHandler : clientHandlers) {
            if(clientHandler != sender) {
                clientHandler.sendMessage(message);
            }
        }
    }

    public static void removeClient(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }
}