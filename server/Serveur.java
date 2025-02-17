package server;


import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import environment.environment;
import server.src.ClientHandler;

public class Serveur {
    private static ServerSocket Listener; // Application Serveur
    public static void main(String[] args) throws Exception {
        int clientNumber = 0;
        
        Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(environment.serverAddress);
        // Association de l'adresse et du port à la connexien
        Listener.bind(new InetSocketAddress(serverIP, environment.serverPort));
        System.out.format("The server is running on %s:%d%n", environment.serverAddress, environment.serverPort);
        try {
        // À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
        // run() de l'objet ClientHandler
        while (true) {
        // Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte
        // Une nouvetle connection : on incémente le compteur clientNumber new ClientHandler(Listener.accept(), clientNumber++).start();
        new ClientHandler(Listener.accept(), clientNumber++).start();    
    }
        } finally {
        // Fermeture de la connexion
        Listener.close();
        }   
    } 
}