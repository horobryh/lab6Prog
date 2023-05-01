package general.network.requests;

import general.network.Request;
import general.models.User;

import java.io.Serializable;

public class AuthRequest extends Request implements Serializable {
    private User user;

    public AuthRequest(User user) {
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }
}
