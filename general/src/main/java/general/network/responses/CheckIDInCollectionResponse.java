package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class CheckIDInCollectionResponse extends Response implements Serializable {
    public CheckIDInCollectionResponse(Boolean result) {
        super(result);
    }
}
