package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.ShowResponse;

import java.io.Serializable;

public class ShowRequest extends Request implements Serializable {
    private final static Class<? extends Response> responseClassLink = ShowResponse.class;
}
