package client.commands.baseCommandsClient;

import client.commands.Executable;
import client.serverManager.ServerManager;
import general.models.User;
import general.network.requests.AuthRequest;
import general.network.responses.AuthResponse;

import java.util.ArrayList;
import java.util.Scanner;

public class AuthCommand implements Executable {
    private final ServerManager serverManager;

    public AuthCommand(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void execute(String[] args, Scanner... scanner) {
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

    public ArrayList getUser(String login, String password) {
        AuthRequest authRequest = new AuthRequest(new User(login, password));
        AuthResponse authResponse = (AuthResponse) serverManager.sendRequestGetResponse(authRequest, true);
        String message;
        if (authResponse.getResult()) {
            if (authResponse.getMessage().equals("")) {
                message = "Авторизация прошла успешно.";
            } else {
                message = authResponse.getMessage();
            }
        } else {
            if (authResponse.getMessage().equals("")) {
                message = "Произошла ошибка авторизации. Завершение работы.";
            } else {
                message = authResponse.getMessage();
            }
        }
        ArrayList<Object> res = new ArrayList<>();
        res.add(authResponse.getUser());
        res.add(message);
        res.add(authResponse.getResult());
        return res;
    }
}
