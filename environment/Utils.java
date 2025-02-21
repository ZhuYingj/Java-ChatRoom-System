package environment;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Utils {
    public static Pair<String, String> getServerInfo() {

        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ip address for the server (begins with 127 and is not 127.0.0.0): ");
        String serverIp = scanner.nextLine().trim();
        System.out.println("Enter the port for the server (between 5000 and 5050): ");
        String serverConst = scanner.nextLine().trim();

        return (new Pair<String, String>(serverIp, serverConst));
    }

    public static boolean isServerIpValid(String serverIp) {
        if (serverIp.endsWith(".")) {return false;}
        String[] numbers = serverIp.split("\\.");
        try {
            if(!(numbers.length == ServerConst.NUM_OF_DEC_IN_IP && Integer.parseInt(numbers[0]) == ServerConst.IP_FIRST_DEC)) return false;
            int sum = ServerConst.MIN_IP_VALUE;
            for (int i = 1; i < numbers.length; i++) {
                int dec = Integer.parseInt(numbers[i]);
                if (!(dec <= ServerConst.IP_MAX_DEC && dec >= ServerConst.IP_MIN_DEC)) return false;
                sum += dec;
            }
            return (sum != ServerConst.MIN_IP_VALUE);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isServerPortValid(String serverPort) {
        try {
            int port = Integer.parseInt(serverPort);
            return port <= ServerConst.MAX_PORT && port >= ServerConst.MIN_PORT;
        } catch (NumberFormatException e) {
            return false;
        } 
    }

}
