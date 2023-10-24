package model;

/**
 * User --- Class for storing
 */
public class User {
    /**
     * username - The public identifier of the user.
     */
    private String username;
    /**
     * password - The private key the user uses to authenticate into the system.
     */
    private String password;
    /**
     * email - Contact address for the user.
     */
    private String email;

    /**
     * Constructor - Initializes user data.
     * @param username Username for the new User.
     * @param password Password for the new User.
     * @param email Contact address for the new User.
     */
    public User(String username, String password, String email) {
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
