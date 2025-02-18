package server.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
public class ClientHandler extends Thread {
    
    private Socket socket; 
    private int clientNumber; 
    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber; 
        System.out.println("New connection with client#" + clientNumber + " at" + socket);
    }
    
    public void run() { 
        try {
            DataOutputStream outServer = new DataOutputStream(socket.getOutputStream()); 
            outServer.writeUTF("Hello from server - you are client#" + clientNumber);
            boolean isConnected = false;

            while(true){
                while(!isConnected) {
                    DataInputStream inClient = new DataInputStream(socket.getInputStream());
                    outServer.writeUTF("Enter your username: ");
                    String username = inClient.readUTF();
                    outServer.writeUTF("Enter your password: ");
                    String password = inClient.readUTF();
                    
                    if (ServerUtils.logIn(username, password)) {
                        isConnected = true;
                        outServer.writeUTF("You are connected");
                        System.out.println("The client #" + clientNumber + " is loggedIn.");
                    } else {
                        outServer.writeUTF("Wrong username or password, try again. \n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
            socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close a socket, what's going on?");
            }
            System.out.println("Connection with client# " + clientNumber+ " closed");
        }
    }
}