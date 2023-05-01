package general.network.responses;

import general.models.User;
import general.network.Response;

import java.io.Serializable;

public class AuthResponse extends Response implements Serializable {
    private User user;
    public AuthResponse(Boolean result, User user) {
        super(result);
        this.user = user;
    }

    public AuthResponse(Boolean result, User user, String message) {
        super(result, message);
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
