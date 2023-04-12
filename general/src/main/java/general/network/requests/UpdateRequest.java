package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.UpdateResponse;
import general.models.Ticket;

import java.io.Serializable;

public class UpdateRequest extends Request implements Serializable {
    private final Ticket ticket;
    private final Integer id;
    public UpdateRequest(Integer id, Ticket ticket) {
        this.ticket = ticket;
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public Integer getId() {
        return id;
    }
}
