package webSocketMessages.serverMessages.serverSpecificMessages;

import webSocketMessages.serverMessages.ServerMessage;

public class ErrorMessage extends ServerMessage {
    String errorMessage;

    public ErrorMessage(ServerMessageType type, int code) {
        super(type);
        if (code == 400) {
            errorMessage = "Error: Bad request.";
        } else if (code == 401) {
            errorMessage = "Error: Unauthorized.";
        } else if (code == 403) {
            errorMessage = "Error: Already taken.";
        } else {
            errorMessage = "Error: Misc.";
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
