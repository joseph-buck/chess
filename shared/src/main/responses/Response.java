package responses;

public abstract class Response {
    public abstract Object getUsername();
    public abstract Object getAuthToken();
    public abstract Object getMessage();
    public abstract int getCode();
}
