package ui;

import responses.LoginResponse;
import java.util.Scanner;


public class PreloginUI {
    private int returnStatus = 0;
    private LoginResponse loginResponse;

    private static String welcomeMessage = "Welcome to 240 chess. Type 'help' to get started.";
    private static String farewellMessage = "Thanks for playing!";
    private static String promptMessage = "[LOGGED_OUT] >>> ";
    private static String helpMessage = """
              register <USERNAME> <PASSWORD> <EMAIL> - to create an account
              login <USERNAME> <PASSWORD> - to play chess
              quit - playing chess
              help - with possible commands
            """;

    public PreloginUI() {
        System.out.println(welcomeMessage);
    }

    public int run() {
        while (returnStatus == 0) {
            System.out.printf(promptMessage);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] args = line.split(" ");

            switch (args[0]) {
                case "register" -> register(args);
                case "login" -> login(args);
                case "quit" -> quit();
                case "help" -> help();
            }
        }
        return returnStatus;
    }

    public void register(String[] args) {
        if (args.length == 4) {
            String username = args[1];
            String password = args[2];
            String email = args[3];
            //TODO: Using the server facade:
                //TODO: Send RegisterRequest here.
                //TODO: Get RegisterResponse back

            //TODO: Return the RegisterResponse object
            //TODO: Move this print statement into PostloginUI
            System.out.println(String.format("Registered user: %s", username));
        }
    }

    public void login(String[] args) {
        if (args.length == 3) {
            String username = args[1];
            String password = args[2];

            //TODO: Using the server facade:
                //TODO: Send LoginRequest here.
                //TODO: Get LoginResponse back

            //TODO: Return the login response object
            // loginResponse = object returned from server
            // Only change the return status to 1 if the login was successful
            returnStatus = 1;
        }
    }

    public void quit() {
        System.out.println(farewellMessage);
        returnStatus = 2;
    }

    public void help() {
        System.out.println(helpMessage);
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}
