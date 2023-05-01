package general.network;

import java.io.Serializable;
public abstract class Response implements Serializable {
    protected Boolean result;
    protected String message = "";

    public Response(Boolean result) {
        this.result = result;
    }

    public Response(Boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Boolean getResult() {
        return this.result;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getMessage() {
        return message;
    }
}
