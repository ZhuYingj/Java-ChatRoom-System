package server.src;

import environment.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ServerUtils {
    public static Pair<Boolean, Boolean> logIn(String name, String password) {
        Boolean isNameValid = false;
        Boolean isPasswordValid = false;
        try (Scanner myReader = new Scanner(new File(DataPathConst.USERS_DATA_PATH))) {
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                if (data[0].equals(name)) { 
                    isNameValid = true;
                    isPasswordValid = data[1].equals(password);
                    break;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return new Pair<Boolean, Boolean>(isNameValid, isPasswordValid);
    }

}
