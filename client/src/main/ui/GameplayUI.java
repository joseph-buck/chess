package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import serverfacade.ServerFacade;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;
import clientwebsocket.WsClient;
import webSocketMessages.userCommands.userGameCommands.JoinPlayerCommand;


public class GameplayUI {
    private ServerFacade serverFacade;
    private int returnStatus;
    private JoinGameRequest joinGameRequest;
    private JoinGameResponse joinGameResponse;
    private WsClient wsClient;

    private static String welcomeMessage = "Joined game %s as %s.";
    private static String leaveFarewellMessage = "Leaving game %s.";
    private static String resignFarewellMessage = "Resigning from game %s.";
    private static String promptMessage = "[IN_GAME] >>> ";
    private static String observerPromptMessage = "[OBSERVING_GAME] >>> ";
    private static String helpMessage = """            
            help - with possible commands
            redraw - the chess board
            leave - the current game
            make move - make a move
            resign - the game
            highlight - legal moves
            """;
    private static String observerHelpMessage = """
            help - with possible commands
            redraw - the chess board
            leave - the current game
            highlight - legal moves
            """;

    public GameplayUI(int loginStatus, JoinGameRequest joinGameRequest, JoinGameResponse joinGameResponse) {
        serverFacade = new ServerFacade();
        returnStatus = loginStatus;
        this.joinGameRequest = joinGameRequest;
        this.joinGameResponse = joinGameResponse;

        // Send JOIN_PLAYER or JOIN_OBSERVER
        try {
            wsClient = new WsClient();
            if (returnStatus == 2) {
                JoinPlayerCommand joinPlayerCommand = new JoinPlayerCommand(joinGameRequest, joinGameResponse);
                wsClient.send(new Gson().toJson(joinPlayerCommand, java.util.Map.class));
            } else if (returnStatus == 3) {
                //TODO: SEND JOIN_OBSERVER HERE
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public int run() {
        System.out.println(welcomeMessage);

        while (returnStatus == 2) {
            System.out.printf(promptMessage);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] args = line.split(" ");

            switch (args[0]) {
                case "help" -> help();
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "make move" -> makeMove();
                case "resign" -> resign();
                case "highlight" -> highlight();
            }
        }
        while (returnStatus == 3) {
            System.out.printf(observerPromptMessage);
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] args = line.split(" ");

            switch(args[0]) {
                case "help" -> observerHelp();
                case "redraw" -> redraw();
                case "leave" -> leave();
                case "highlight" -> highlight();
            }
        }
        return returnStatus;
    }

    private void help() {
        System.out.println(helpMessage);
    }
    
    private void observerHelp() {
        System.out.println(observerHelpMessage);
    }

    private void redraw() {
        System.out.println("redraw");

        //TODO: ChessBoardDisplay needs to use a chess.Game object and display
        // the contents of that object, not just a basic board.

        //TODO: Edit drawBoard() so that you can input from which perspective
        // you want the board displayed from.

        ChessBoardDisplay chessBoardDisplay = new ChessBoardDisplay();
        chessBoardDisplay.drawBoard();
    }

    private void leave() {
        System.out.println(leaveFarewellMessage);

        //TODO: Remove the user from the game. If they are a player, their name
        // should be removed from the respective WHITE/BLACK username of the game.

        // TODO: Send the client back to the post-login UI if successful
        if (true) { // if removal from game was successful
            returnStatus = 1;
        }
    }

    private void makeMove() {
        //TODO: Move is taken as user input after calling makeMove. Prompt the user.

        //TODO: Update the game board with the move, and automatically
        // update all other clients
        System.out.println("make move");
    }

    private void resign() {
        //TODO: Prompts for confirmation to resign

        //TODO: Should only allow a resign if the game is not over

        //TODO: Game is marked as completed. No more moves are allowed.

        System.out.println(resignFarewellMessage);
    }

    private void highlight() {
        //TODO: Prompts for input about which piece to highlight moves for.

        //TODO: Hightlight the current piece's square and all squares it can go to.

        System.out.println("highlight");
    }
}
