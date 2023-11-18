package responses;


/**
 * ClearApplicationResponse --- Class for storing data about a clear
 * application response.
 */
public class ClearApplicationResponse {
    private String message;
    private int code;

    public ClearApplicationResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

}
