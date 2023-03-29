package general.network;

import java.io.Serializable;
public abstract class Response implements Serializable {
    protected Boolean result;

    public Response(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return this.result;
    }
}
