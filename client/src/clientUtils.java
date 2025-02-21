package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import environment.clientConst;

public class clientUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static String login(Socket socket) throws IOException {
        DataOutputStream outClient = new DataOutputStream(socket.getOutputStream()); 
        DataInputStream inServer = new DataInputStream(socket.getInputStream());

        String serverMessageUsername = inServer.readUTF();
        System.out.println(serverMessageUsername);

        String username = "";
        username = getLoginInfo(username, clientConst.usernameErrorMessage);
        outClient.writeUTF(username);

        String serverMessagePassWord = inServer.readUTF();
        System.out.println(serverMessagePassWord);

        String password = "";
        password = getLoginInfo(password, clientConst.passwordErrorMessage);
        outClient.writeUTF(password);

        String serverResponse = inServer.readUTF();
        System.out.println(serverResponse);

        return serverResponse;
    }

    public static String getLoginInfo(String info, String errorMessage) {
        while (true) {
            info = scanner.nextLine().trim();
            if (!isLoginInfoValid(info)) {
                System.out.println(errorMessage);
            } else {
                return info;
            }
        }
    }

    public static boolean isLoginInfoValid(String info) {
        return !(info.contains(",") || info.toString().isEmpty());
    }

    public static String sendMessage(Socket socket) throws IOException {
        DataOutputStream outClient = new DataOutputStream(socket.getOutputStream());
        String message = "";

        while(true) {
            message = scanner.nextLine().trim();
            if (message.length() < 200) {
                outClient.writeUTF(message);
                break;
            } else {
                System.out.print("Réessayez d'envoyer un message de taille plus petit que 200\n");
            }
        }
        return message;
    }
}
