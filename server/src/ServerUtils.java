package server.src;

import environment.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ServerUtils {
    public static Pair<Boolean, Boolean> logIn(String name, String password) {
        Boolean isNameValid = false;
        Boolean isPasswordValid = false;
        try {
            File userDataFile = new File(DataPathConst.USERS_DATA_PATH);
            @SuppressWarnings("resource")
            Scanner myReader = new Scanner(userDataFile);

            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split("\\.");
                if(data[0].contentEquals(name)) {
                    isNameValid = true;
                    isPasswordValid = data[1].contentEquals(password);
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new Pair<Boolean, Boolean>(isNameValid, isPasswordValid);
    }

}
