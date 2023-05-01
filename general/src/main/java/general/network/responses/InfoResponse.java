package general.network.responses;

import general.network.Response;
import general.models.Ticket;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class InfoResponse extends Response implements Serializable {
    private final List<Ticket> resultList;
    private final Date initializationDate;
    public InfoResponse(Boolean result, List<Ticket> resultList, Date initializationDate) {
        super(result);
        this.resultList = resultList;
        this.initializationDate = initializationDate;
    }

    public InfoResponse(Boolean result, List<Ticket> resultList, Date initializationDate, String message) {
        super(result, message);
        this.resultList = resultList;
        this.initializationDate = initializationDate;
    }

    public List<Ticket> getResultList() {
        return this.resultList;
    }

    public Date getInitializationDate() {
        return this.initializationDate;
    }
}
