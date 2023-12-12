package webSocketMessages.userCommands.userGameCommands;

import requests.JoinGameRequest;
import webSocketMessages.userCommands.UserGameCommand;

import java.util.HashMap;
import java.util.Map;

public class JoinPlayerCommand extends UserGameCommand {
    private JoinGameRequest joinGameRequest;

    public JoinPlayerCommand(JoinGameRequest joinGameRequest) {
        super(joinGameRequest.getAuthToken());
        this.commandType = CommandType.JOIN_PLAYER;
        this.joinGameRequest = joinGameRequest;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public JoinGameRequest getJoinGameRequest() {
        return this.joinGameRequest;
    }

    /*public Map getMap() {
        Map joinPlayerCommand = new HashMap();
        joinPlayerCommand.put(this.authToken)
        return joinGameRequest.getMap();
    }*/
}
