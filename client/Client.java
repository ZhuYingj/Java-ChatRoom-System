
import java.io.DataInputStream;
import java.net.Socket;

import environment.serverConst;

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        socket = new Socket(serverConst.serverAddress, serverConst.serverPort);
        System.out.format("Serveur lancé sur [%s:%d]", serverConst.serverAddress, serverConst.serverPort);

        
        DataInputStream in = new DataInputStream(socket.getInputStream());
        
        String helloMessageFromServer = in.readUTF();
        System.out.println(helloMessageFromServer);
        
        socket.close();
        }
}