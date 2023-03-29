package general.network;

import java.io.*;
import java.util.List;

public abstract class Request implements Serializable {
    protected static final Class<? extends Response> responseClassLink = Response.class;

    public Class<? extends Response> getResponseClass() {
        return responseClassLink;
    }
}
