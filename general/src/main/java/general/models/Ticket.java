package general.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;
import general.validators.baseValidators.NotBlank;
import general.validators.baseValidators.NotNull;
import general.validators.baseValidators.SizeNumber;
import general.validators.baseValidators.SizeString;
import general.validators.exceptions.NullException;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Ticket model class
 */
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
        return r;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }

    public User getCreationUser() {
        return this.creationUser;
    }
}
