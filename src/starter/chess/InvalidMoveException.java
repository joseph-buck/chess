package chess;

/**
 * Indicates an invalid move was made in a game
 */
public class InvalidMoveException extends Exception {
    public boolean movedIntoCheck = false;

    public InvalidMoveException() {}

    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(String message, boolean movedIntoCheck) {
        super(message);
        this.movedIntoCheck = movedIntoCheck;
    }
}
