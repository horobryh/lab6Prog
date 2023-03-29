package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.InfoResponse;

import java.io.Serializable;

public class InfoRequest extends Request implements Serializable {
    private final static Class<? extends Response> responseClassLink = InfoResponse.class;
}
