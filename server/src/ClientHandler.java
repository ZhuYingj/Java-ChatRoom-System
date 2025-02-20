package server.src;

import environment.Pair;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private int clientNumber;
    private String username;
    private String password;
    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber; 
        System.out.println("New connection with client#" + clientNumber + " at " + socket);
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
                    username = inClient.readUTF();
                    outServer.writeUTF("Enter your password: ");
                    password = inClient.readUTF();

                    Pair<Boolean, Boolean> logInResult = ServerUtils.logIn(username, password);
                    if (logInResult.getKey() && logInResult.getValue()) {
                        isConnected = true;
                        outServer.writeUTF("You are connected");
                        System.out.println("The client #" + clientNumber + " is loggedIn.");
                    } else if (!logInResult.getKey()) {
                        outServer.writeUTF("L'utilisateur n'existe pas, création du compte de " + username + " dans la base de donnée.\n");
                        //creer lutilisateur
                    } else if(!logInResult.getValue()) {
                        outServer.writeUTF("Erreur dans la saisie du mot de passe\n");
                    } else saveUser();
                }
                //add message
                DataInputStream inClientMessage = new DataInputStream(socket.getInputStream());
                String message = inClientMessage.readUTF();
                if(message.equalsIgnoreCase("exit")) {
                    outServer.writeUTF("Deconnection a implementer");
                    break;
                }
                if(!message.isEmpty()) {
                    outServer.writeUTF("message saved");
                    saveMessage(username, socket.getInetAddress().toString(), Integer.toString(socket.getPort()), message);
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
    private synchronized void saveUser() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(DataPathConst.USERS_DATA_PATH,true))) {
            writer.newLine();
            writer.write(username + "." + password);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void saveMessage(String username, String ip, String port, String message) {
        String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String messageLog = String.format("%s,%s,%d,%s,%s%n", username, ip, port, timestamp, message);
        File file = new File(DataPathConst.MESSAGES_DATA_PATH);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(messageLog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}