package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.SortResponse;

import java.io.Serializable;

public class SortRequest extends Request implements Serializable {
    private final static Class<? extends Response> responseClassLink = SortResponse.class;
}
