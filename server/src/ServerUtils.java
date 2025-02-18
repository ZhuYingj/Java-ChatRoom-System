package server.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ServerUtils {
    public static boolean logIn(String name, String password) {
        try {
            File userDataFile = new File("server\\environment\\userData.txt");
            @SuppressWarnings("resource")
            Scanner myReader = new Scanner(userDataFile);

            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("\\.");
                if(data[0].contentEquals(name) && data[1].contentEquals(password)) {
                    return true;
                }
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return false;
    }
}
