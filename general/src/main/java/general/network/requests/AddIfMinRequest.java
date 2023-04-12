package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.AddIfMinResponse;
import general.models.Ticket;

import java.io.Serializable;

public class AddIfMinRequest extends Request implements Serializable {
    private Ticket ticket;

    public AddIfMinRequest(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return this.ticket;
    }
}
