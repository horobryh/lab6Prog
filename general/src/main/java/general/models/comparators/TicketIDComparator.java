package general.models.comparators;

import general.models.Ticket;

import java.util.Comparator;

/**
 * Ticket element comparison class by ID
 */
public class TicketIDComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket o1, Ticket o2) {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
