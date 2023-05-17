package general.models;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class User implements Serializable {
    private Integer id = null;
    private String login;
    private String password;
    private Boolean isNull;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.isNull = login == null || password == null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isNull() {
        return isNull;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
