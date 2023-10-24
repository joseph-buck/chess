package services.responses;


/**
 * ClearApplicationResponse --- Class for storing data about a clear
 * application response.
 */
public class ClearApplicationResponse {
    int code;
    private String message;

    public ClearApplicationResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
