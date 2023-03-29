package general.network.requests;

import general.network.Request;
import general.network.Response;
import general.network.responses.AddResponse;
import general.models.Ticket;

import java.io.Serializable;

public class AddRequest extends Request implements Serializable {
    private final Ticket ticket;
    private static final Class<? extends Response> responseClassLink = AddResponse.class;

    public AddRequest(Ticket ticket) {
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return this.ticket;
    }
}
