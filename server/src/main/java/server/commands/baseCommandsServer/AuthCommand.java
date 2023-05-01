package server.commands.baseCommandsServer;

import general.models.User;
import general.network.Request;
import general.network.Response;
import general.network.requests.AuthRequest;
import general.network.responses.AuthResponse;
import server.commands.Executable;
import server.network.Server;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AuthCommand implements Executable {
    private Server server;

    @Override
    public Response execute(Request request) {
        try {
            User user = request.getUser();

            if (server.getDataBaseManager().checkUserInDB(user)) {
                if (server.getDataBaseManager().checkPassword(user)) {
                    user = server.getDataBaseManager().setUserID(user);
                    return new AuthResponse(user.getId() != null, user, "Авторизация прошла успешно.");
                } else {
                    user = new User(null, null);
                    return new AuthResponse(user.getId() != null, user, "Пароль неправильный.");
                }
            } else {
                user = server.getDataBaseManager().createUser(user);
                return new AuthResponse(user.getId() != null, user, "Регистрация нового пользователя прошла успешно.");
            }
        } catch (SQLException | NoSuchAlgorithmException ignored) {
        }
        return new Response(false) {};
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    public AuthCommand(Server server) {
        this.server = server;
    }
}
