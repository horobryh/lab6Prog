package general.models.comparators;

import general.models.Ticket;

import java.util.Comparator;

/**
 * Ticket element comparison class by name
 */
public class TicketNameComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket o1, Ticket o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
