package client.commands.baseCommandsClient;

import client.commands.Executable;
import client.serverManager.ServerManager;
import general.models.User;
import general.network.requests.AuthRequest;
import general.network.responses.AuthResponse;

import java.util.Scanner;

import static java.lang.System.exit;

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

    public User getUser(String login, String password) {
        AuthRequest authRequest = new AuthRequest(new User(login, password));
        AuthResponse authResponse = (AuthResponse) serverManager.sendRequestGetResponse(authRequest, true);
        if (authResponse.getResult()) {
            if (authResponse.getMessage().equals("")) {
                System.out.println("Авторизация прошла успешно.");
            } else {
                System.out.println(authResponse.getMessage());
            }
        } else {
            if (authResponse.getMessage().equals("")) {
                System.out.println("Произошла ошибка авторизации. Завершение работы.");
            } else {
                System.out.println(authResponse.getMessage());
            }
            exit(0);
        }
        return authResponse.getUser();
    }
}
