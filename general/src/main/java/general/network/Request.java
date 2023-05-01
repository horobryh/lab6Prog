package general.network;

import general.models.User;

import java.io.*;

public abstract class Request implements Serializable {
    User user;
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return this.user;
    }
}
