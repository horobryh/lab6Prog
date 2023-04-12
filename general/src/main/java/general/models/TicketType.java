package general.models;

import java.io.Serializable;

/**
 * TicketType model class
 */
public enum TicketType implements Serializable {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;
}