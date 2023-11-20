package ui;

import responses.LoginResponse;
import java.util.Scanner;


public class PostloginUI {
    private int returnStatus = 1;
    private LoginResponse loginResponse;

    private static String welcomeMessage = "Logged in as ";
    private static String farewellMessage = "Thanks for playing!";
    private String promptMessage = "[LOGGED_IN] >>> ";
    private static String helpMessage = """
              create <NAME> - a game
              list - games
              join <ID> [WHITE|BLACK|<empty>] - a game
              observe <ID> - a game
              logout - when you are done
              quit - playing chess
              help - with possible commands
            """;

    //TODO: Make sure that when quit is called, you logout first.

    public int run() {
        while (returnStatus == 1) {
            System.out.printf(promptMessage);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] args = line.split(" ");

            switch (args[0]) {
                case "create" -> create();
                case "list" -> list();
                case "join" -> join();
                case "observe" -> observe();
                case "logout" -> logout();
                case "quit" -> quit();
                case "help" -> help();
            }
        }
        return returnStatus;
    }

    private void create() {
        //TODO: Call ServerFacade create function
    }

    private void list() {
        //TODO: Call ServerFacade list function
    }

    private void join() {
        //TODO: Call ServerFacade join function
        // Display default chess board
        // In the future, enter gameplay mode
        displayInitialBoard();
    }

    private void observe() {
        //TODO: Call ServerFacade observe function
        // Display default chess board
        // In the future, enter gameplay mode
        displayInitialBoard();
    }

    private void logout() {
        //TODO: Call ServerFacade logout function
        returnStatus = 0;
    }

    private void quit() {
        logout();
        System.out.println(farewellMessage);
        returnStatus = 2;
    }

    private void help() {
        System.out.println(helpMessage);
    }

    private void displayInitialBoard() {
        ChessBoardDisplay cBD = new ChessBoardDisplay();
        cBD.drawBoard();
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        System.out.printf(welcomeMessage);
        System.out.println((String) loginResponse.getUsername());
        this.loginResponse = loginResponse;
    }
}
