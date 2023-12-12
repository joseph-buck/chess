package webSocketMessages.serverMessages.serverSpecificMessages;


import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.userGameCommands.JoinPlayerCommand;
import static webSocketMessages.serverMessages.ServerMessage.ServerMessageType.*;

public class PlayerJoinedNotification extends ServerMessage {
    private String message;

    public PlayerJoinedNotification(JoinPlayerCommand joinPlayerCommand, String username) {
        //TODO: Pass in other stuff so that I can get the username
        super(NOTIFICATION);
        String gameID = String.valueOf((Integer) joinPlayerCommand.getJoinGameRequest().getGameID());
        String color = joinPlayerCommand.getJoinGameRequest().getPlayerColor();
        message = String.format("User %s joined game %s as %s.", username, gameID, color);
    }

    public String getMessage() {
        return message;
    }
}
