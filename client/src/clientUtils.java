package src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class clientUtils {
    public static String login(Socket socket) throws IOException {
        DataOutputStream outClient = new DataOutputStream(socket.getOutputStream()); 
        DataInputStream inServer = new DataInputStream(socket.getInputStream());

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String serverMessageUsername = inServer.readUTF();
        System.out.println(serverMessageUsername);

        String username = scanner.nextLine().trim();
        outClient.writeUTF(username);

        String serverMessagePassWord = inServer.readUTF();
        System.out.println(serverMessagePassWord);

        String password = scanner.nextLine().trim();
        outClient.writeUTF(password);

        String serverResponse = inServer.readUTF();
        System.out.println(serverResponse);

        return serverResponse;
    }
}
