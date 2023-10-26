package services.responses;


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

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
