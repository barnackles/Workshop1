package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        startApp();

    }

    public static void startApp() {

        System.out.println("Starting...");
        Scanner s = new Scanner(System.in);
        String pathName = "tasks.csv";

        boolean exit = false;
        String [][] loadedTasks = tasks(pathName);


        while (!exit) {
            System.out.println("Choose one from following options: \n");
            showMenu();
            System.out.println("Please select an option: ");

            String input = s.nextLine();

            switch (input) {
                   case "add" -> loadedTasks = addNewItem(loadedTasks);
                   case "remove" -> loadedTasks = removeItem(loadedTasks);
                   case "list" -> showList(loadedTasks);
                   case "exit" -> {System.out.println("Exiting..."); saveTasks(loadedTasks, pathName); exit = true;}

            }
     }


    }

    private static void saveTasks(String [][] savedTasks, String pathName) {

        Path path = Paths.get(pathName);
        StringBuilder save = new StringBuilder();

        for (String[] savedTask : savedTasks) {
            for (String s : savedTask) {
                save.append(s).append(", ");
            }
            save.append("\n");
        }
        String savedString = save.toString();




        try {
            Files.write(path, Collections.singleton(savedString));
        } catch (IOException ex) {
            System.out.println("Unable to save.");
        }


    }

    private static String [][] removeItem(String[][] loadedTasks) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please provide number of the item to be removed.");
        int input = Integer.parseInt(s.nextLine());

        return ArrayUtils.remove(loadedTasks, input);
    }

    private static void showList(String [][] arr) {

        for (int i = 0; i < arr.length; i++) {
            StringBuilder itemsOnIndex = new StringBuilder();
            for (int j = 0; j < arr[i].length; j++){
                itemsOnIndex.append(arr[i][j]).append(" ");

            }
            System.out.println(i + ": " + itemsOnIndex.toString());

        }

    }

    private static String [][] addNewItem(String [][] arr) {
        Scanner s = new Scanner(System.in);
        StringBuilder newTask = new StringBuilder();
        System.out.println("Please add task description");
        newTask.append(s.nextLine()).append(",");

        System.out.println("PLease add task due date");
        newTask.append(s.nextLine()).append(",");

        System.out.println("Is your task important: true / false");
        newTask.append(s.nextLine());

        arr = Arrays.copyOf(arr, arr.length +1);
        arr[arr.length-1] = newTask.toString().split(",");


        return arr;
    }

    private static String[][] tasks(String pathName) {

        File file = new File(pathName);

        return csvToArray(pathName);

    }

        private static String [][] csvToArray(String pathName) {
        Path path = Path.of(pathName);
        String [][] fileArray = new String[1][1];
        try {
            String wholeFile = Files.readString(path);
            String [] tmpArray = wholeFile.split("\n");
            fileArray = Arrays.copyOf(fileArray, tmpArray.length);

            for (int i = 0; i < tmpArray.length; i++){

                String [] tmpArray2 = tmpArray[i].split(", ");
                fileArray[i] = tmpArray2;

                System.arraycopy(tmpArray2, 0, fileArray[i], 0, tmpArray2.length);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileArray;
    }

    private static void showMenu() {

        System.out.println("""
                add
                remove
                list
                exit
                """);
    }



}
