package service.request;


/**
 * LoginRequest --- Class for storing login request information.
 */
public class LoginRequest {
    /**
     * username - Identifier for the User that is logging in.
     */
    private String username;
    /**
     * password - The string key defined by the User upon registration.
     */
    private String password;

    /**
     * Constructor - Initializes fields with user defined values.
     * @param username User defined username.
     * @param password User defined password.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword() {
        this.password = password;
    }
}
