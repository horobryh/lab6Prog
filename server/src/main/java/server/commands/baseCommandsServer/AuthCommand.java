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
            User user = authorization(request);
            return new AuthResponse(user.getId() != null, user);
        } catch (SQLException | NoSuchAlgorithmException ignored) {
        }
        return new Response(false) {};
    }

    public User authorization(Request request) throws SQLException, NoSuchAlgorithmException {
        User user = request.getUser();

        if (server.getDataBaseManager().checkUserInDB(user)) {
            if (server.getDataBaseManager().checkPassword(user)) {
                return server.getDataBaseManager().setUserID(user);
            }
            return user;
        } else {
            return server.getDataBaseManager().createUser(user);
        }
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
