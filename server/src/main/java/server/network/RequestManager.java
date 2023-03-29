package server.network;

import general.network.Request;
import general.network.requests.*;

import java.util.HashMap;

public class RequestManager {
    private HashMap<Class<? extends Request>, String> requestToCommandKey;
    private static RequestManager instance = null;

    public RequestManager getInstance() {
        if (instance == null){
            instance = new RequestManager();
        }
        return instance;
    }

    public RequestManager() {
        registerRequests();
    }

    private void registerRequests() {
        requestToCommandKey.put(AddRequest.class, "add");
        requestToCommandKey.put(AddIfMinRequest.class, "add_if_min");
        requestToCommandKey.put(ClearRequest.class, "clear");
        requestToCommandKey.put(CountLessThanDiscountRequest.class, "count_less_than_discount");
        requestToCommandKey.put(InfoRequest.class, "info");
        requestToCommandKey.put(PrintFieldAscendingDiscountRequest.class, "print_field_ascending_discount");
        requestToCommandKey.put(RemoveByIDRequest.class, "remove_by_id");
        requestToCommandKey.put(ShowRequest.class, "show");
        requestToCommandKey.put(ShuffleRequest.class, "shuffle");
        requestToCommandKey.put(SortRequest.class, "sort");
        requestToCommandKey.put(UpdateRequest.class, "update");
    }

    public String getCommandKeyByRequestClass(Class<? extends Request> requestClass) {
        return requestToCommandKey.get(requestClass);
    }
}