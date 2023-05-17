package client.gui;

import general.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class EditTicketController {
    private Stage stage;
    @FXML
    private DatePicker eventDateDatePicker;

    @FXML
    private TextField eventDescription;

    @FXML
    private Spinner<Integer> eventMinAgeSpinner;

    @FXML
    private TextField eventNameTextFIeld;

    @FXML
    private ChoiceBox<String> eventTypeChoiceBox;

    @FXML
    private Button saveTicketButton;

    @FXML
    private TextField ticketCommentTextField;

    @FXML
    private Spinner<Integer> ticketDiscountSpinner;

    @FXML
    private TextField ticketNameTextField;

    @FXML
    private Spinner<Integer> ticketPriceSpinner;

    @FXML
    private ChoiceBox<String> ticketTypeChoiceBox;

    @FXML
    private Spinner<Integer> ticketXSpinner;

    @FXML
    private Spinner<Double> ticketYSpinner;


    public EditTicketController(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        ticketXSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE));
        ticketYSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(Float.MIN_VALUE, Float.MAX_VALUE));
        ticketPriceSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE));
        ticketDiscountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
        ObservableList<String> observableListTicket = FXCollections.observableArrayList();
        for (TicketType name: TicketType.values()) {
            observableListTicket.add(name.name());
        }
        ticketTypeChoiceBox.setItems(observableListTicket);
        eventMinAgeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(Integer.MIN_VALUE, Integer.MAX_VALUE));
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (EventType name: EventType.values()) {
            observableList.add(name.name());
        }
        eventTypeChoiceBox.setItems(observableList);
        ticketXSpinner.getValueFactory().setValue(0);
        ticketYSpinner.getValueFactory().setValue(0D);
        ticketPriceSpinner.getValueFactory().setValue(0);
        ticketDiscountSpinner.getValueFactory().setValue(0);
        eventMinAgeSpinner.getValueFactory().setValue(0);
        ticketTypeChoiceBox.setValue(TicketType.USUAL.name());
        eventTypeChoiceBox.setValue(EventType.FOOTBALL.name());
    }

    public void showAndWait() {
        stage.showAndWait();
    }

    public void saveTicket() {
        stage.close();
    }

    private Ticket fromFormsToEntity() {
        Ticket ticket;
        try {
            ticket = new Ticket();
            ticket.setName(ticketNameTextField.getText());
            ticket.setCoordinates(new Coordinates(ticketXSpinner.getValue(), BigDecimal.valueOf(ticketYSpinner.getValue()).floatValue()));
            ticket.setPrice(ticketPriceSpinner.getValue());
            ticket.setDiscount(Long.valueOf(ticketDiscountSpinner.getValue()));
            ticket.setComment(ticketCommentTextField.getText());
            ticket.setType(TicketType.valueOf(ticketTypeChoiceBox.getValue()));
            ticket.setCreationDate(new Date());

            Event event = new Event();
            event.setName(eventNameTextFIeld.getText());
            event.setDate(eventDateDatePicker.getValue().atStartOfDay());
            event.setMinAge(BigDecimal.valueOf(eventMinAgeSpinner.getValue()).longValue());
            event.setDescription(eventDescription.getText());
            event.setEventType(EventType.valueOf(eventTypeChoiceBox.getValue()));
            ticket.setEvent(event);
        } catch (NullPointerException e) {
            new Alert(Alert.AlertType.ERROR, "Похоже, Вы заполнили не все поля формы. Попробуйте снова.").show();
            ticket = null;
        }
        return ticket;
    }

    public Ticket add() {
        showAndWait();
        Ticket ticket = fromFormsToEntity();
//        clearForm();
        return ticket;
    }

    public Stage getStage() {
        return this.stage;
    }

    public Ticket edit(Ticket ticket) {
        ticketNameTextField.setText(ticket.getName());
        ticketPriceSpinner.getValueFactory().setValue(ticket.getPrice());
        ticketDiscountSpinner.getValueFactory().setValue(Math.toIntExact(ticket.getDiscount()));
        ticketXSpinner.getValueFactory().setValue(ticket.getX());
        ticketYSpinner.getValueFactory().setValue(Double.valueOf(ticket.getY()));
        ticketCommentTextField.setText(ticket.getComment());
        ticketTypeChoiceBox.setValue(ticket.getType().name());

        eventNameTextFIeld.setText(ticket.getEvent().getName());
        eventDescription.setText(ticket.getEvent().getDescription());
        eventDateDatePicker.setValue(LocalDate.from(ticket.getEvent().getDate()));
        eventMinAgeSpinner.getValueFactory().setValue(Math.toIntExact(ticket.getEvent().getMinAge()));
        eventTypeChoiceBox.setValue(ticket.getEvent().getEventType().name());
        showAndWait();
        Ticket newTicket = fromFormsToEntity();
        newTicket.setId(ticket.getId());
        clearForm();
        return newTicket;
    }

    private void clearForm() {
        ticketNameTextField.clear();
        ticketPriceSpinner.getValueFactory().setValue(0);
        ticketDiscountSpinner.getValueFactory().setValue(0);
        ticketXSpinner.getValueFactory().setValue(0);
        ticketYSpinner.getValueFactory().setValue(0D);
        ticketCommentTextField.clear();

        eventNameTextFIeld.clear();
        eventDescription.clear();
        eventDateDatePicker.setValue(LocalDate.now());
        eventMinAgeSpinner.getValueFactory().setValue(0);
    }
}
