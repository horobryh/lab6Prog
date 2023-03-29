package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class UpdateResponse extends Response implements Serializable {
    public UpdateResponse(Boolean result) {
        super(result);
    }
}
