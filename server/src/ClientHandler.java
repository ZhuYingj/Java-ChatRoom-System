package server.src;

import environment.Pair;
import server.Serveur;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private int clientNumber;
    private String username;
    private String password;
    private DataOutputStream outServer;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        try {
            outServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("New connection with client#" + clientNumber + " at " + socket);
    }
    
    public void run() { 
        try {
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
                        System.out.println("The client #" + clientNumber + " is logged in.");
                        outServer.writeUTF(loadMessage());
                        Serveur.broadCastMessage(username + " rejoin la conversation !", this);
                    } else if (!logInResult.getKey()) {
                        outServer.writeUTF("L'utilisateur n'existe pas, création du compte de " + username + " dans la base de donnée.\n");
                        saveUser();
                    } else if(!logInResult.getValue()) {
                        outServer.writeUTF("Erreur dans la saisie du mot de passe\n");
                    }
                }
                DataInputStream inClientMessage = new DataInputStream(socket.getInputStream());
                String message = inClientMessage.readUTF();
                if(message.equalsIgnoreCase("exit")) {
                    outServer.writeUTF("disconnected");
                    Serveur.broadCastMessage(username + " a quitté la conversation !", this);
                    break;
                }
                if(!message.isEmpty()) {
                    saveMessage(username, socket.getInetAddress().toString(), Integer.toString(socket.getPort()), message);
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));
                    String formattedMsg = String.format("[%s - %s:%s - %s]: %s",
                        username,socket.getInetAddress().toString().substring(1,
                         socket.getInetAddress().toString().length()),Integer.toString(socket.getPort()), timestamp, message);
                    Serveur.emitMessage(formattedMsg, this);
                }
            }
        } catch (IOException e) {
            System.out.println("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                Serveur.removeClient(this);
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
        System.out.println("saving message");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss"));
        String messageLog = String.format("%s,%s,%s,%s,%s%n", username, ip.substring(1, ip.length()), port, timestamp, message);
        File file = new File(DataPathConst.MESSAGES_DATA_PATH);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(messageLog);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized String loadMessage() {

        File file = new File(DataPathConst.MESSAGES_DATA_PATH);
        List<String> lastLines = new ArrayList<>();
        String history = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            LinkedList<String> lines = new LinkedList<>();

            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (lines.size() > 15) {
                    lines.poll();
                }
            }
            lastLines.addAll(lines);

            for (String msg : lastLines) {
                String[] partsFromLine = msg.split(",");
                String formattedMsg = String.format("[%s - %s:%s - %s]: %s",
                        partsFromLine[0], partsFromLine[1], partsFromLine[2], partsFromLine[3], partsFromLine[4]);
                history += formattedMsg + "\n";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return history;
    }

    public synchronized void sendMessage(String message) {
        try {
            outServer.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}