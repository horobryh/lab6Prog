package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class AddResponse extends Response implements Serializable {
    public AddResponse(Boolean result) {
        super(result);
    }
    public AddResponse(Boolean result, String message) {
        super(result, message);
    }
}
