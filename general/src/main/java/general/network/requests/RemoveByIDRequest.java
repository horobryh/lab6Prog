package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.RemoveByIDResponse;

import java.io.Serializable;

public class RemoveByIDRequest extends Request implements Serializable {
    private final Integer id;

    public RemoveByIDRequest(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
