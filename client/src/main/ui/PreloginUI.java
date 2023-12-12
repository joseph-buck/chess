package ui;

import requests.LoginRequest;
import requests.RegisterRequest;
import responses.LoginResponse;
import responses.RegisterResponse;
import responses.Response;
import serverfacade.ServerFacade;

import java.util.Scanner;


public class PreloginUI {
    private ServerFacade serverFacade;
    private int returnStatus = 0;
    private Response loginResponse = null;

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
        serverFacade = new ServerFacade();
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

            RegisterRequest regReq = new RegisterRequest(username, password, email);
            RegisterResponse regRes = serverFacade.register(regReq);

            if (regRes != null) {
                if (regRes.getCode() == 200) {
                    returnStatus = 1;
                    this.loginResponse = regRes;
                    System.out.println(String.format("Registered user: %s", username));

                }
//                returnStatus = 1;
//                this.loginResponse = regRes;
            }
        }
    }

    public void login(String[] args) {
        //TODO: Test logging in with wrong password, other fail cases
        if (args.length == 3) {
            String username = args[1];
            String password = args[2];

            LoginRequest loginRequest = new LoginRequest(username, password);
            LoginResponse loginResponse = serverFacade.login(loginRequest);

            if (loginResponse != null) {
                if (loginResponse.getCode() == 200) {
                    returnStatus = 1;
                    this.loginResponse = loginResponse;
                }
//                returnStatus = 1;
//                this.loginResponse = loginResponse;
            }
        }
    }

    public void quit() {
        System.out.println(farewellMessage);
        returnStatus = -1;
    }

    public void help() {
        System.out.println(helpMessage);
    }

    public Response getLoginResponse() {
        return loginResponse;
    }
}
