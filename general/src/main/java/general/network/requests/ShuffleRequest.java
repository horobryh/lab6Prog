package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.ShuffleResponse;

import java.io.Serializable;

public class ShuffleRequest extends Request implements Serializable {
    private static final Class<? extends Response> responseClassLink = ShuffleResponse.class;
}
