package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class AddIfMinResponse extends Response implements Serializable {
    private final Boolean resultChecking;

    public AddIfMinResponse(Boolean result, Boolean resultChecking) {
        super(result);
        this.resultChecking = resultChecking;
    }

    public AddIfMinResponse(Boolean result, Boolean resultChecking, String message) {
        super(result, message);
        this.resultChecking = resultChecking;
    }

    public Boolean getResultChecking() {
        return this.resultChecking;
    }
}
