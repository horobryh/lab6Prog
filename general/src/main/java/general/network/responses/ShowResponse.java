package general.network.responses;

import general.network.Response;
import general.models.Ticket;

import java.io.Serializable;
import java.util.List;

public class ShowResponse extends Response implements Serializable {
    private final List<Ticket> resultList;
    public ShowResponse(Boolean result, List<Ticket> resultList) {
        super(result);
        this.resultList = resultList;
    }

    public List<Ticket> getResultList() {
        return resultList;
    }
}
