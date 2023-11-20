package ui;

import requests.CreateGameRequest;
import requests.JoinGameRequest;
import responses.*;
import serverfacade.ServerFacade;

import java.util.*;


public class PostloginUI {
    private ServerFacade serverFacade;
    private int returnStatus = 1;
    private Response loginResponse;
    private List<HashMap<String, Object>> games;

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

    public PostloginUI() {
        serverFacade = new ServerFacade();
    }

    public int run() {
        while (returnStatus == 1) {
            System.out.printf(promptMessage);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] args = line.split(" ");

            switch (args[0]) {
                case "create" -> create(args);
                case "list" -> list();
                case "join" -> join(args);
                case "observe" -> observe(args);
                case "logout" -> logout();
                case "quit" -> quit();
                case "help" -> help();
            }
        }
        return returnStatus;
    }

    private void create(String[] args) {
        if (args.length == 2) {
            String gameName = args[1];
            String authToken = (String) loginResponse.getAuthToken();
            CreateGameResponse createGameResponse = serverFacade.createGame(
                    new CreateGameRequest(gameName, authToken));
            System.out.println(String.format("Created game '%s' with ID %d",
                    args[1], createGameResponse.getGameID()));
        }
    }

    private void list() {
        ListGamesResponse listGamesResponse = serverFacade.listGames(
                (String) loginResponse.getAuthToken());
        List<HashMap<String, Object>> games = listGamesResponse.getGames();
        this.games = games;
        StringBuilder stringBuilder = new StringBuilder("\nLIST OF GAMES:\n");
        int iter = 1;
        for (Map game : games) {
            stringBuilder.append(String.format("%d. Game Name: %s.\n",
                    iter, game.get("gameName")));
            stringBuilder.append(String.format("   GameID: %d\n", ((Double) game.get("gameID")).intValue()));
            stringBuilder.append("   Players: \n");
            stringBuilder.append("      White User: ");
            if (game.get("whiteUsername") != null) {
                stringBuilder.append(game.get("whiteUsername") + "\n");
            } else {
                stringBuilder.append("NONE\n");
            }
            stringBuilder.append("      Black User: ");
            if (game.get("blackUsername") != null) {
                stringBuilder.append(game.get("blackUsername") + "\n");
            } else {
                stringBuilder.append("NONE\n");
            }
            iter += 1;
        }
        System.out.println(stringBuilder.toString());
    }

    private void join(String[] args) {
        if (args.length == 3) {
            int gameID = Integer.valueOf(args[1]);
            String teamColor = args[2].toUpperCase();
            JoinGameResponse joinGameResponse = serverFacade.joinGame(
                    new JoinGameRequest(teamColor, gameID,
                            (String) loginResponse.getAuthToken()));
            if (joinGameResponse != null) {
                System.out.println(String.format(
                        "Joined game %d on the %s team.", gameID, teamColor));
                displayInitialBoard();
            }
        }
    }

    private void observe(String[] args) {
        if (args.length == 2) {
            int gameID = Integer.valueOf(args[1]);
            JoinGameResponse joinGameResponse = serverFacade.joinGame(
                    new JoinGameRequest("", gameID,
                            (String) loginResponse.getAuthToken()));
            if (joinGameResponse != null) {
                System.out.println(String.format(
                        "Joined game %d as an observer.", gameID));
            }
        }
        displayInitialBoard();
    }

    private void logout() {
        LogoutResponse logoutResponse = serverFacade.logout((String) loginResponse.getAuthToken());
        if (logoutResponse != null) {
            if (logoutResponse.getCode() == 200) {
                returnStatus = 0;
            }
        }
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
        ChessBoardDisplay chessBoardDisplay = new ChessBoardDisplay();
        chessBoardDisplay.drawBoard();
    }

    public void setLoginResponse(Response loginResponse) {
        System.out.printf(welcomeMessage);
        System.out.println((String) loginResponse.getUsername());
        this.loginResponse = loginResponse;
    }

    public void setGames(List<HashMap<String, Object>> games) {
        this.games = games;
    }

    public List<HashMap<String, Object>> getGames() {
        return this.games;
    }
}
