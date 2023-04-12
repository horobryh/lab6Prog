package general.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import general.validators.baseValidators.NotBlank;
import general.validators.baseValidators.NotNull;
import general.validators.baseValidators.SizeNumber;
import general.validators.exceptions.WrongIDException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Event model class
 */
public class Event extends BaseModelWithValidators implements AutomaticID<Long>, Serializable {
    private static Long lastID = 1L;
    private static Set<Long> usedID = new HashSet<>();
    @CsvBindByName(column = "EventID", required = true)
    private Long id;
    @CsvBindByName(column = "EventName", required = true)
    private String name;
    @CsvDate
    @CsvBindByName(column = "EventDate", required = true)
    private LocalDateTime date;
    @CsvBindByName(column = "EventMinAge")
    private Long minAge;
    @CsvBindByName(column = "EventDescription", required = true)
    private String description;
    @CsvBindByName(column = "EventType", required = true)
    private EventType eventType;

    public Event(String name, LocalDateTime date, Long minAge, String description, EventType eventType) {
        super();
        this.id = getNextID();
        this.name = name;
        this.date = date;
        this.minAge = minAge;
        this.description = description;
        this.eventType = eventType;
    }

    public Event(Long id, String name, LocalDateTime date, Long minAge, String description, EventType eventType) throws WrongIDException {
        super();
        if (!checkIDInUsed(id)) {
            lastID = id;
        }
        this.id = getNextID();
        this.name = name;
        this.date = date;
        this.minAge = minAge;
        this.description = description;
        this.eventType = eventType;

    }

    public Event() {
        super();
        this.id = getNextID();
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getMinAge() {
        return minAge;
    }

    public void setMinAge(Long minAge) {
        this.minAge = minAge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public Long getNextID() {
        lastID += 1L;
        while (usedID.contains(lastID)) {
            if (lastID == Long.MAX_VALUE) {
                lastID = 1L;
            }
            lastID += 1L;
        }
        usedID.add(lastID);
        return lastID;
    }

    public boolean checkIDInUsed(Long id) {
        return usedID.contains(id);
    }

    @Override
    public void setValidators() {
        validators.put("id", List.of(new NotNull(), new SizeNumber<>(1L, Long.MAX_VALUE)));
        validators.put("name", List.of(new NotNull(), new NotBlank()));
        validators.put("date", List.of(new NotNull()));
        validators.put("minAge", List.of());
        validators.put("description", List.of(new NotNull(), new NotBlank()));
        validators.put("eventType", List.of(new NotNull()));
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", minAge=" + minAge +
                ", description='" + description + '\'' +
                ", eventType=" + eventType +
                '}';
    }

    public String beautifulString() {
        return "id: " + getId() + ", name: " + getName() + ", date: " + getDate() + ", minAge: " + getMinAge() + ", description: " + getDescription() + ", eventType: " + getEventType();
    }
}