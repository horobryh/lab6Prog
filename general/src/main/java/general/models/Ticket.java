package general.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import general.validators.Verifiable;
import general.validators.baseValidators.NotBlank;
import general.validators.baseValidators.NotNull;
import general.validators.baseValidators.SizeNumber;
import general.validators.baseValidators.SizeString;
import general.validators.exceptions.NullException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.Condition;

/**
 * Ticket model class
 */
@EqualsAndHashCode
public class Ticket extends BaseModelWithValidators implements Serializable, AutomaticID<Integer>, Comparable<Ticket> {
    private static Integer lastID = 0;
    private User creationUser;
    private static Set<Integer> usedID = new HashSet<>();
    @CsvBindByName(required = true)
    private int id;
    @CsvBindByName
    private String name;
    @CsvRecurse
    private Coordinates coordinates;
    @CsvDate
    @CsvBindByName(required = true)
    private Date creationDate;
    @CsvBindByName(required = true)
    private Integer price;
    @CsvBindByName(required = true)
    private Long discount;
    @CsvBindByName(required = true)
    private String comment;
    @CsvBindByName(required = true)
    private TicketType type;
    @CsvRecurse
    private Event event;

    public static void removeInUsedID(Integer id) {
        usedID.remove(id);
    }

    @Override
    public void setValidators() {
        validators.put("id", List.of(new NotNull(), new SizeNumber<>(0, Integer.MAX_VALUE)));
        validators.put("name", List.of(new NotNull(), new NotBlank()));
        validators.put("coordinates", List.of(new NotNull()));
        validators.put("creationDate", List.of(new NotNull()));
        validators.put("price", List.of(new SizeNumber<>(0, Integer.MAX_VALUE)));
        validators.put("discount", List.of(new NotNull(), new SizeNumber<>(0, 100)));
        validators.put("comment", List.of(new NotNull(), new SizeString(0, 719)));
        validators.put("type", List.of(new NotNull()));
        validators.put("event", List.of(new NotNull()));
    }

    public Ticket() {
        super();
        this.id = getNextID();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Event getEvent() {
        return event;
    }

    public Ticket(String name, Coordinates coordinates, Integer price, Long discount, String comment, TicketType type, Event event) {
        super();
        this.id = getNextID();
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.event = event;
        this.creationDate = new Date();
    }

    public Ticket(Integer id, String name, Coordinates coordinates, Date creationDate, Integer price, Long discount, String comment, TicketType type, Event event) {
        super();
        if (!checkIDInUsed(id)) {
            lastID = id;
        }
        this.id = getNextID();
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.event = event;
        this.creationDate = creationDate;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "models.Ticket{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", discount=" + discount +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", event=" + event +
                '}';
    }

    @Override
    public Integer getNextID() {
        lastID += 1;
        while (usedID.contains(lastID)) {
            if (lastID == Integer.MAX_VALUE) {
                lastID = 1;
            }
            lastID += 1;
        }
        usedID.add(lastID);
        return lastID;
    }

    public static boolean checkIDInUsed(Integer id) {
        return usedID.contains(id);
    }

    @Override
    public int compareTo(Ticket o) {
        if (o == null) {
            try {
                throw new NullException("Передан пустой аргумент для сравнения");
            } catch (NullException e) {
                throw new RuntimeException(e);
            }
        }
        return Integer.compare(this.getId(), o.getId());
    }

    public String beautifulString() {
        String r = "";
        r += "id: " + getId();
        r += ", name: " + getName();
        r += ", coordinates: [" + getCoordinates().beautifulString() + "]";
        r += ", creationDate: " + getCreationDate();
        r += ", price: " + getPrice();
        r += ", discount: " + getDiscount();
        r += ", comment: " + getComment();
        r += ", type: " + getType();
        r += ", event: [" + getEvent().beautifulString() + "]";
        r += ", creationUserLogin: " + getCreationUser().getLogin();
        return r;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }

    public User getCreationUser() {
        return this.creationUser;
    }

    public Integer getCreationUserID() {
        return creationUser.getId();
    }

    public Integer getX() {
        return coordinates.getX();
    }

    public Float getY() {
        return coordinates.getY();
    }

    public HashMap<String, String> getValuesForFiltering() {
        Ticket ticket = this;
        HashMap<String, String> result = new HashMap<>();
        result.put("ticketID", String.valueOf(ticket.getId()));
        result.put("ticketName", ticket.getName());
        try {
            result.put("ticketX", String.valueOf(ticket.getX()));
        } catch (NullPointerException e) {
            result.put("ticketX", "");
        }
        try {
            result.put("ticketY", String.valueOf(ticket.getY()));
        } catch (NullPointerException e) {
            result.put("ticketY", "");
        }
        result.put("ticketDate", String.valueOf(ticket.getCreationDate()));
        result.put("ticketPrice", String.valueOf(ticket.getPrice()));
        result.put("ticketDiscount", String.valueOf(ticket.getDiscount()));
        result.put("ticketComment", String.valueOf(ticket.getComment()));
        try {
            result.put("ticketType", ticket.getType().name());
        } catch (NullPointerException e) {
            result.put("ticketType", "");
        }
        try {
            result.put("ticketCreationUserLogin", ticket.getCreationUser().getLogin());
        } catch (NullPointerException e) {
            result.put("ticketCreationUserLogin", "");
        }
        try {
            result.put("eventID", String.valueOf(ticket.getEvent().getId()));
            result.put("eventName", ticket.getEvent().getName());
            result.put("eventDate", String.valueOf(ticket.getEvent().getDate()));
            result.put("eventMinAge", String.valueOf(ticket.getEvent().getMinAge()));
            result.put("eventDescription", ticket.getEvent().getDescription());
            result.put("eventType", ticket.getEvent().getEventType().name());
            result.put("eventCreationUserLogin", ticket.getEvent().getCreationUser().getLogin());
        } catch (NullPointerException e) {
            result.put("eventID", "");
            result.put("eventName", "");
            result.put("eventDate", "");
            result.put("eventMinAge", "");
            result.put("eventDescription", "");
            result.put("eventType", "");
            result.put("eventCreationUserLogin", "");
        }


        return result;
    }

    public static class DeleteTicket extends Ticket {
        private Ticket ticket;

        public  DeleteTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public Ticket getTicket() {
            return this.ticket;
        }
    }
}
