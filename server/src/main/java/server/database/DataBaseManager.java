package server.database;

import general.models.Event;
import general.models.Ticket;
import general.models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.postgresql.ds.PGSimpleDataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DataBaseManager {
    private String login;
    private String password;
    private String url;
    private String pepper = "@_HaFfH";
    private PGSimpleDataSource pgSimpleDataSource;
    private Connection connection;

    public DataBaseManager(String url, String login, String password) throws SQLException {
        this.url = url;
        this.login = login;
        this.password = password;
        pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(url);
        pgSimpleDataSource.setUser(login);
        pgSimpleDataSource.setPassword(password);
        connection = DriverManager.getConnection(url, login, password);
    }

    public Connection getConnection() throws SQLException {
        return pgSimpleDataSource.getConnection();
    }

    public User createUser(User user) throws SQLException, NoSuchAlgorithmException {
//        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO \"PUsers\" (login, password, salt) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getLogin());
        String salt = RandomStringUtils.randomGraph(7);
        String password = getHashedPassword(pepper, user.getPassword(), salt);
        statement.setString(2, password);
        statement.setString(3, salt);
        int result = statement.executeUpdate();

        if (result == 0) {
            throw new SQLException("При создании объекта User произошла ошибка");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt("id"));
            } else {
                throw new SQLException("При получении ID произошла ошибка");
            }
        }
//        connection.close();
        return user;
    }

    public Boolean checkUserInDB(User user) throws SQLException {
//        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"PUsers\" WHERE login = ?");
        statement.setString(1, user.getLogin());
        ResultSet result = statement.executeQuery();
//        connection.close();
        return result.next();
    }

    public Boolean checkPassword(User user) throws SQLException, NoSuchAlgorithmException {
//        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM \"PUsers\" WHERE login = ?");
        statement.setString(1, user.getLogin());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String password = resultSet.getString("password");
        String salt = resultSet.getString("salt");
        String userHashedPassword = getHashedPassword(pepper, user.getPassword(), salt);
//
//        connection.close();
        return userHashedPassword.equals(password);
    }

    public String getHashedPassword(String pepper, String password, String salt) throws NoSuchAlgorithmException {
        String userPassword = pepper + password + salt;
        MessageDigest md = MessageDigest.getInstance("MD2");
        md.update(userPassword.getBytes());
        return new String(md.digest());
    }

    public User setUserID(User user) throws SQLException {
//        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT id FROM \"PUsers\" WHERE login = ?");
        statement.setString(1, user.getLogin());
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        user.setId(resultSet.getInt("id"));
//        connection.close();
        return user;
    }

    public Event addEvent(Event event) throws SQLException {
//        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO \"PEvents\" (name, date, \"minAge\", description, \"eventType\", creationuserid) VALUES (?,? ,?, ?, '" + event.getEventType().name() + "', ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, event.getName());
        statement.setDate(2, Date.valueOf(event.getDate().toLocalDate()));
        statement.setObject(3, event.getMinAge());
        statement.setString(4, event.getDescription());
        statement.setInt(5, event.getCreationUser().getId());

        int result = statement.executeUpdate();

        if (result == 0) {
            throw new SQLException("При создании объекта Event произошла ошибка");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                event.setId(generatedKeys.getLong("id"));
            } else {
                throw new SQLException("При получении ID произошла ошибка");
            }
        }
//        connection.close();
        return event;
    }

    public Ticket addTicket(Ticket ticket, Event event) throws SQLException {
//        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO \"PTickets\" (name, \"coordinateX\", \"coordinateY\", \"creationDate\", price, discount, comment, type, \"eventID\", \"creationUserID\") VALUES (?, ?, ?, ?, ?, ?, ?, '" + ticket.getType().name() + "', ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, ticket.getName());
        statement.setInt(2, ticket.getCoordinates().getX());
        statement.setFloat(3, ticket.getCoordinates().getY());
        statement.setDate(4, new Date(ticket.getCreationDate().getTime()));
        statement.setInt(5, ticket.getPrice());
        statement.setFloat(6, ticket.getDiscount());
        statement.setString(7, ticket.getComment());
        statement.setLong(8, event.getId());
        statement.setInt(9, ticket.getCreationUser().getId());

        int result = statement.executeUpdate();

        if (result == 0) {
            throw new SQLException("При создании объекта Ticket произошла ошибка");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                ticket.setId(generatedKeys.getInt("id"));
            } else {
                throw new SQLException("При получении ID произошла ошибка");
            }
        }

//        connection.close();
        return ticket;
    }

    public ResultSet getAllElements() throws SQLException {
//        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT t.id tid, t.name tname, t.\"coordinateX\" tx, t.\"coordinateY\" ty, t.\"creationDate\" tcd, t.price tp, t.discount td, t.comment tc, t.type tt, t.\"eventID\" teid, t.\"creationUserID\" tcuid, ev.id eid, ev.name ename, ev.date ed, ev.\"minAge\" ema, ev.description edesc, ev.\"eventType\" et, ev.creationuserid ecuid, tu.login tcul, eu.login ecul FROM \"PTickets\" t JOIN \"PEvents\" ev ON t.\"eventID\"=ev.id JOIN \"PUsers\" tu ON t.\"creationUserID\"=tu.id JOIN \"PUsers\" eu ON ev.creationuserid = eu.id");
        return resultSet;
    }

    public int[] updateTicket(Ticket ticket) throws SQLException {
//        Connection connection = getConnection();
        Event event = ticket.getEvent();
        PreparedStatement preparedStatementEvent = connection.prepareStatement("update \"PEvents\" SET name = ?, date = ?, \"minAge\" = ?, description = ?, \"eventType\" = '" + event.getEventType().name() + "' WHERE id = ?");
        preparedStatementEvent.setString(1, event.getName());
        preparedStatementEvent.setDate(2, Date.valueOf(event.getDate().toLocalDate()));
        preparedStatementEvent.setLong(3, event.getMinAge());
        preparedStatementEvent.setString(4, event.getDescription());
        preparedStatementEvent.setLong(5, event.getId());
        int resultEvent = preparedStatementEvent.executeUpdate();

        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE \"PTickets\" SET name = ?, \"coordinateX\" = ?, \"coordinateY\" = ?, \"creationDate\" = ?, price = ?, discount = ?, comment = ?, type = '" + ticket.getType().name() + "', \"eventID\" = ? WHERE id = ?");
        preparedStatement.setString(1, event.getName());
        preparedStatement.setDouble(2, ticket.getCoordinates().getX());
        preparedStatement.setDouble(3, ticket.getCoordinates().getY());
        preparedStatement.setDate(4, new Date(ticket.getCreationDate().getTime()));
        preparedStatement.setInt(5, ticket.getPrice());
        preparedStatement.setLong(6, ticket.getDiscount());
        preparedStatement.setString(7, ticket.getComment());
        preparedStatement.setLong(8, event.getId());
        preparedStatement.setInt(9, ticket.getId());
        int resultTicket = preparedStatement.executeUpdate();

//        connection.close();
        return new int[]{resultEvent, resultTicket};

    }

    public ResultSet findElementByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM \"PTickets\" WHERE id = ?");
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery();
    }

    public int clear() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"PTickets\", \"PEvents\"");

        return preparedStatement.executeUpdate();
    }

    public int removeByID(Integer id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM \"PTickets\" WHERE id = ?");
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate();
    }
}
