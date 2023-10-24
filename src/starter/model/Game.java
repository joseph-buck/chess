package model;

import chess.ChessGame;


/**
 * Game --- Class for storing the information for individual chess games.
 */
public class Game {
    /**
     * gameID - Integer that serves as a unique identifier of the Game object.
     */
    private int gameID;
    /**
     * whiteUsername - The username of the white User in the game.
     */
    private String whiteUsername;
    /**
     * blackUsername - The username of the black User in the game.
     */
    private String blackUsername;
    /**
     * gameName - String that acts as a human-readable identifier of the Game.
     */
    private String gameName;
    /**
     * The chess.ChessGame object that stores the actual board configuration.
     */
    private ChessGame game;

    /**
     * Constructor - Initializes Game data.
     * @param gameID The gameID for the new Game.
     * @param whiteUsername Username of the white team player for the new Game.
     * @param blackUsername Username of the black team player for the new Game.
     * @param gameName The gameName for the new Game.
     * @param game ChessBoard to be used as the starting point for the Game.
     */
    public Game(int gameID, String whiteUsername, String blackUsername,
                String gameName, ChessGame game) {
        this.gameID = gameID;
        this.whiteUsername = whiteUsername;
        this.blackUsername = blackUsername;
        this.gameName = gameName;
        this.game = game;
    }

    public int getGameID() {
        return this.gameID;
    }

    public String getWhiteUsername() {
        return this.whiteUsername;
    }

    public String getBlackUsername() {
        return this.blackUsername;
    }

    public String getGameName() {
        return this.gameName;
    }

    public ChessGame getChessGame() {
        return this.game;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setWhiteUsername(String whiteUsername) {
        this.whiteUsername = whiteUsername;
    }

    public void setBlackUsername(String blackUsername) {
        this.blackUsername = blackUsername;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }
}
