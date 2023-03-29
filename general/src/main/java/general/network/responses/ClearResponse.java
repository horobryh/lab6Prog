package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class ClearResponse extends Response implements Serializable {
    public ClearResponse(Boolean result) {
        super(result);
    }
}
