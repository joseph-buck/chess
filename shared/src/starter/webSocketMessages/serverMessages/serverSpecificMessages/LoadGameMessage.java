package webSocketMessages.serverMessages.serverSpecificMessages;

import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
import webSocketMessages.userCommands.userGameCommands.JoinPlayerCommand;

import static webSocketMessages.serverMessages.ServerMessage.ServerMessageType.LOAD_GAME;

public class LoadGameMessage extends ServerMessage {
    private models.Game game;

    public LoadGameMessage(models.Game game) {
        super(LOAD_GAME);
        this.game = game;
    }

    public models.Game getGame() {
        return this.game;
    }
}
