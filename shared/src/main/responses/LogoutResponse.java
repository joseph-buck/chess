package responses;


/**
 * LogoutResponse --- Class for storing the data of a logout response.
 */
public class LogoutResponse {
    private String message;
    private int code;

    public LogoutResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
       return code;
    }

}
