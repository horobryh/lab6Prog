package general.network.responses;

import general.network.Response;

import java.io.Serializable;

public class RemoveByIDResponse extends Response implements Serializable {
    private Boolean resultChecking;

    public RemoveByIDResponse(Boolean result, Boolean resultChecking) {
        super(result);
        this.resultChecking = resultChecking;
    }

    public Boolean getResultChecking() {
        return resultChecking;
    }
}
