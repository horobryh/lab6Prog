package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class ShuffleResponse extends Response implements Serializable {
    public ShuffleResponse(Boolean result) {
        super(result);
    }
    public ShuffleResponse(Boolean result, String message) {
        super(result, message);
    }
}
