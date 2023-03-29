package server.commands;

import general.network.Request;
import general.network.Response;

public interface Executable {
    Response execute(Request request);
    String getName();
    String getArgs();
    String getDescription();
}
