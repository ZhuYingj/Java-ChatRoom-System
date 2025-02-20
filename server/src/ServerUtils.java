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
            File userDataFile = new File("server\\environment\\userData.txt");
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

//    private static boolean isInfoValid(String username, String password) {
//        try {
//            File userDataFile = new File("server\\environment\\userData.txt");
//            @SuppressWarnings("resource")
//            Scanner myReader = new Scanner(userDataFile);
//
//            while (myReader.hasNextLine()) {
//                String[] data = myReader.nextLine().split("\\.");
//                if(data[0].contentEquals(username) && data[1].contentEquals(password)) {
//                    return true;
//                }
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    public static boolean userExists(String name) {
//        try {
//            File userDataFile = new File("server\\environment\\userData.txt");
//            @SuppressWarnings("resource")
//            Scanner myReader = new Scanner(userDataFile);
//
//            while (myReader.hasNextLine()) {
//                String[] data = myReader.nextLine().split("\\.");
//                return data[0].equalsIgnoreCase(name);
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//    public static boolean isPasswordValid(String username, String password) {
//        try {
//            File userDataFile = new File("server\\environment\\userData.txt");
//            @SuppressWarnings("resource")
//            Scanner myReader = new Scanner(userDataFile);
//
//            while (myReader.hasNextLine()) {
//                String[] data = myReader.nextLine().split("\\.");
//                return data[0].equalsIgnoreCase(username) && data[1].equalsIgnoreCase(password);
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
