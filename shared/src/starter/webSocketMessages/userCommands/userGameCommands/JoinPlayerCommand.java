package webSocketMessages.userCommands.userGameCommands;

import chess.ChessGame;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import webSocketMessages.userCommands.UserGameCommand;

public class JoinPlayerCommand extends UserGameCommand {
    //private JoinGameRequest joinGameRequest;
    private int gameID;
    private ChessGame.TeamColor color;
    private int code;

    public JoinPlayerCommand(JoinGameRequest joinGameRequest, JoinGameResponse joinGameResponse) {
        super(joinGameRequest.getAuthToken());
        this.commandType = CommandType.JOIN_PLAYER;
        gameID = joinGameRequest.getGameID();
        color = ChessGame.TeamColor.valueOf(joinGameRequest.getPlayerColor());
        code = joinGameResponse.getCode();
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public int getGameID() {
        return gameID;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return color;
    }

    public Integer getCode() {
        return (Integer) code;
    }
}
