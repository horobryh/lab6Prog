package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.ClearResponse;

import java.io.Serializable;

public class ClearRequest extends Request implements Serializable {
    public static final Class<? extends Response> responseClassLink = ClearResponse.class;
}
