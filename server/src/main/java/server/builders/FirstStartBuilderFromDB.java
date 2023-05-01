package server.builders;

import general.models.*;
import server.collectionManager.CollectionManager;
import general.validators.exceptions.NullException;
import server.database.DataBaseManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Creating an initial collection object from a file
 */
public class FirstStartBuilderFromDB {
    private static FirstStartBuilderFromDB instance = null;
    private final DataBaseManager dataBaseManager;

    /**
     * @return Collection of objects created earlier
     */
    public CollectionManager buildObject() throws SQLException, NullException {
        ResultSet resultSet = dataBaseManager.getAllElements();
        List<Ticket> collection = new java.util.ArrayList<>(List.of());
        while (resultSet.next()) {
            Ticket newTicket = new Ticket();
            newTicket.setId(resultSet.getInt("tid"));
            newTicket.setName(resultSet.getString("tname"));
            newTicket.setCoordinates(new Coordinates(resultSet.getInt("tx"), resultSet.getFloat("ty")));
            newTicket.setCreationDate(resultSet.getDate("tcd"));
            newTicket.setPrice(resultSet.getInt("tp"));
            newTicket.setDiscount(resultSet.getLong("td"));
            newTicket.setComment(resultSet.getString("tc"));
            newTicket.setType(TicketType.valueOf(resultSet.getString("tt")));
            User user = new User(null, null);
            user.setId(resultSet.getInt("tcuid"));
            newTicket.setCreationUser(user);
            Event event = new Event();
            event.setId(resultSet.getLong("eid"));
            event.setName(resultSet.getString("ename"));
            event.setDate(LocalDateTime.of(resultSet.getDate("ed").toLocalDate(), LocalTime.parse("00:00:00")));
            event.setEventType(EventType.valueOf(resultSet.getString("et")));
            event.setDescription(resultSet.getString("edesc"));
            event.setMinAge(resultSet.getLong("ema"));
            User user2 = new User(null, null);
            user2.setId(resultSet.getInt("ecuid"));
            event.setCreationUser(user2);
            newTicket.setEvent(event);

            collection.add(newTicket);
        }
        return new CollectionManager(collection);
    }

    private FirstStartBuilderFromDB(DataBaseManager dataBaseManager) {
        this.dataBaseManager = dataBaseManager;
    }

    public static FirstStartBuilderFromDB getInstance(DataBaseManager dataBaseManager) {
        if (instance == null) {
            instance = new FirstStartBuilderFromDB(dataBaseManager);
        }
        return instance;
    }
}
