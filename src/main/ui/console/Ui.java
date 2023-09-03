package ui.console;

import model.User;
import model.ListOfUser;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

// Represent listofuser  application
public class Ui {
    private User user;
    private User tester;
    private ListOfUser listOfUser;

    private String name;
    private String sex; //W for Woman, M for Man
    private int age;
    private double height;
    private double weight;
    private int exercisedPerWeek;
    private int id;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/ListOfUserTemp.json";


    private Scanner input;

    // EFFECTS: runs the teller application
    public Ui() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runUi();

    }




    // MODIFIES: this
    // EFFECTS: processes user input
    private void runUi() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("quit")) {
                saveListOfUser();
                keepGoing = false;
            } else {
                processCommand(command);

            }
        }

        System.out.println("\nSee you next time!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("new")) {
            createUser();
        } else if (command.equals("remove")) {
            removeUser();
        } else if (command.equals("forget")) {
            forgetUser();
        } else if (command.equals("bmi")) {
            calculateBmi();
        } else if (command.equals("bmr")) {
            calculateBmr();
        } else if (command.equals("list0")) {
            showNameOfUserList();
        } else if (command.equals("info0")) {
            showInfo();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\tnew -> Create new User");
        System.out.println("\tremove -> Remove your user information from system");
        System.out.println("\tforget -> Find back your id by name and age");
        System.out.println("\tbmi -> Calculate your BMI");
        System.out.println("\tbmr -> Calculate your BMR");
        System.out.println("\tquit -> quit system");
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        tester = new User("tester", "W", 13, 1.6, 50, 1, 0);
        listOfUser = new ListOfUser();
        listOfUser.addUser(tester);
        loadListOfUser();


        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: create new user
    private void createUser() {
        System.out.println("\n Welcome to the system, please enter following information");
        if (listOfUser.isFull()) {
            System.out.println("\nThe system list is full, please remove a user");
        } else {
            getName1();
            getSex();
            getAge();
            getHeight1();
            getWeight();
            getExerciseFrequency();
            getId();
            user = new User(name, sex, age, height, weight, exercisedPerWeek, id);
            boolean b = listOfUser.addUser(user);
            if (b) {
                System.out.println("\nNew User created, Welcome!");
                saveListOfUser();
            } else {
                System.out.println("\nThe system list is full, please remove a user");
            }
        }

    }

    //************************** createUser Helper ********************************************
    //EFFECTS: return the name
    private String getName1() {
        System.out.println("\nWhat's your name");
        String str = input.next(); //input string
        if ((str.length() == 0) || (Character.isSpace(str.charAt(0)))) {
            System.out.println("\nName can't be empty!");
            getName1();
        } else {
            this.name = str;
        }

        return str;
    }

    //EFFECTS: return the sex
    private void getSex() {
        System.out.println("\nWhat's your gender at birth, W for woman, M for man");
        String str = input.next(); //input string
        if (str.equals("M") | str.equals("W")) {
            this.sex = str;
        } else {
            System.out.println("Gender not valid....");
            getSex();
        }
    }

    //EFFECTS: return the age
    private void getAge() {
        System.out.println("\nHow old are you (please only input Integer)");
//        int int0 = input.nextInt(); //input int
        try {
            int int0 = input.nextInt();
            if (int0 > 0) {
                this.age = int0;
            } else {
                System.out.println("Age can't be negative or 0");
                getAge();
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error,age only can be Integer!\nTry again!" + "\033[0m");
            input.next();
            getAge();
        }

    }

    //EFFECTS: return height
    private void getHeight1() {
        System.out.println("\nHow tall are you(m)");
        try {
            double dou = input.nextDouble(); //input double
            if (dou > 0) {
                this.height = dou;
            } else {
                System.out.println("Height can't be negative or 0");
                getHeight1();
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error,height only can be Number!\nTry again!" + "\033[0m");
            input.next();
            getHeight1();
        }


    }

    //EFFECTS: return weight
    private void getWeight() {
        System.out.println("\nHow much do you weigh(Kg)");
        try {
            double dou = input.nextDouble(); //input double
            if (dou > 0) {
                this.weight = dou;
            } else {
                System.out.println("Weight can't be negative or 0");
                getWeight();
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error,weight only can be Number!\nTry again!" + "\033[0m");
            input.next();
            getWeight();
        }

    }

    //EFFECTS: return the exercise frequency
    private void getExerciseFrequency() {
        System.out.println("\nHow many times a week do you exercise");
        try {
            int int0 = input.nextInt(); //input int
            if (int0 >= 0) {
                this.exercisedPerWeek = int0;
            } else {
                System.out.println("Frequency can't be negative");
                getExerciseFrequency();
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error,ExerciseFrequency only can be Integer!\nTry again!" + "\033[0m");
            input.next();
            getExerciseFrequency();
        }


    }


    //EFFECTS: return id
    private void getId() {
        System.out.println("\nEnter a integer for your ID(remember it, it's' important)");
        try {
            int int0 = input.nextInt(); //input int
            if (listOfUser.idIsUsed(int0)) {
                System.out.println("\nThis Id already be used");
                getId();
            } else {
                this.id = int0;
            }

        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error,ID only can be Integer!\nTry again!" + "\033[0m");
            input.next();
            getId();
        }

    }
    //************************** createUser Helper ********************************************

    //EFFECTS: remove user from system
    private void removeUser() {
        try {
            System.out.println("\nplease enter the user id which you want to remove from system");
            int int0 = input.nextInt(); //input int
            if (listOfUser.removeUserOfID(int0)) {
                System.out.println("\nUser removed!");
                saveListOfUser();
            } else {
                System.out.println("\n User doesn't exist!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error! \nPlease input ID (integer)" + "\033[0m");
            input.next();
            removeUser();
        }

    }

    //EFFECTS: help user ind their id
    private void forgetUser() {
        try {
            System.out.println("\nPlease provide the user's name and age to get back the Id");
            System.out.println("\n Name?");
            String str = input.next(); //input string
            System.out.println("\n Age?");
            int int0 = input.nextInt();

            if (listOfUser.findIdOfNameAndAge(str, int0) == -7355608) {
                System.out.println("\nDoesn't find matched user!");
            } else {
                System.out.println("\nYour Id is:\n" + listOfUser.findIdOfNameAndAge(str, int0) + "\nRemember It!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error! \nPlease input valid ID (integer)" + "\033[0m");
            input.next();
            forgetUser();
        }

    }

    //EFFECTS: calculate user's BMI
    private void calculateBmi() {
        try {
            System.out.println("\nPlease provide your id to calculate your bmi");
            int int0 = input.nextInt();
            if (listOfUser.getBmiOfID(int0) == -1) {
                System.out.println("\nDoesn't find matched user");
            } else {
                System.out.println("\nYour Body mass index is\n" + listOfUser.getBmiOfID(int0));
                if (listOfUser.getBmiOfID(int0) < 19) {
                    System.out.println("You are Underweight");
                } else if (19 <= listOfUser.getBmiOfID(int0) && listOfUser.getBmiOfID(int0) < 25) {
                    System.out.println("You are Normal weight");
                } else if (25 <= listOfUser.getBmiOfID(int0) && listOfUser.getBmiOfID(int0) < 30) {
                    System.out.println("You are Overweight");
                } else if (30 <= listOfUser.getBmiOfID(int0)) {
                    System.out.println("You are Obesity");
                }


            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error! \nPlease input valid ID (integer)" + "\033[0m");
            input.next();
            calculateBmi();
        }

    }

    //EFFECTS: calculate user's BMR
    private void calculateBmr() {
        try {
            System.out.println("\nPlease provide your id to calculate your bmr");
            int int0 = input.nextInt();
            if (listOfUser.getBmrOfID(int0) == -1) {
                System.out.println("\nDoesn't find matched user");
            } else {
                System.out.println("\nYour Basal Metabolic Rate is\n" + listOfUser.getBmrOfID(int0));
                if (listOfUser.getBmrOfID(int0) <= 0) {
                    System.out.println("\033[31;1m" + "User's Information is Abnormal!!!" + "\033[0m");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error! \nPlease input valid ID (integer)" + "\033[0m");
            input.next();
            calculateBmr();
        }
    }

    // EFFECTS: saves the listofuser to file
    private void saveListOfUser() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfUser);
            jsonWriter.close();
            System.out.println("Save Success!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads listofuser from file
    private void loadListOfUser() {
        try {
            listOfUser = jsonReader.read();
            System.out.println("Loaded Success!");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //******************************* Admin Comment ********************************************

    //EFFECTS: help admin check the list
    private void showNameOfUserList() {
        System.out.println("\nWelcome,Administrator");
        System.out.println("The users' name in this system is show below:");
        if (listOfUser.isEmpty()) {
            System.out.println("\nThere is no user in system!");
        } else {
            for (int i = 0; i < listOfUser.length(); i++) {
                System.out.println("\nName:\t" + listOfUser.getListOfUser(i).getName());
                System.out.println("ID:\t" + listOfUser.getListOfUser(i).getId());

            }
        }

    }

    //EFFECTS: help admin check user's information
    private void showInfo() {
        try {
            System.out.println("\nWelcome,Administrator\nEnter the user's id to show all user's Information");
            int int0 = input.nextInt();
            if (listOfUser.idIsUsed(int0)) {
                for (int i = 0; i < listOfUser.length(); i++) {
                    if (int0 == listOfUser.getListOfUser(i).getId()) {
                        System.out.println("\nName:\t" + listOfUser.getListOfUser(i).getName());
                        System.out.println("Gender:\t" + listOfUser.getListOfUser(i).getSex());
                        System.out.println("Age:\t" + listOfUser.getListOfUser(i).getAge());
                        System.out.println("Height:\t" + listOfUser.getListOfUser(i).getHeight());
                        System.out.println("Weight:\t" + listOfUser.getListOfUser(i).getWeight());
                        System.out.println("ExercisedPerWeek:\t" + listOfUser.getListOfUser(i).getExercisedPerWeek());
                        System.out.println("Id:\t" + listOfUser.getListOfUser(i).getId());
                    }
                }
            } else {
                System.out.println("\nDoesn't find matched user!");
            }
        } catch (InputMismatchException e) {
            System.out.println("\033[31;1m" + "Error! \nPlease input valid ID (integer)" + "\033[0m");
            input.next();
            showInfo();
        }
    }

    //******************************* Admin Comment ********************************************

}
