package service.request;


/**
 * RegisterRequest -- Class for storing register request data.
 */
public class RegisterRequest {
    /**
     * username - Identifier for the User that is registering.
     */
    private String username;
    /**
     * password - The string key to be used by the User when logging in.
     */
    private String password;
    /**
     * email - The contact method of the new User.
     */
    private String email;

    /**
     * Constructor - Initializes fields to user defined values.
     * @param username User defined username.
     * @param password User defined password.
     * @param email User defined email.
     */
    public RegisterRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
