package client.gui;

import general.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class EditTicketController {
    private Stage stage;
    @FXML
    private BorderPane backgroundBorderPane;

    @FXML
    private MenuItem denmarkLanguageMenuItem;

    @FXML
    private MenuItem englishLanguageMenuItem;

    @FXML
    private DatePicker eventDateDatePicker;

    @FXML
    private Label eventDateLabel;

    @FXML
    private TextField eventDescription;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private Label eventMinAgeLabel;

    @FXML
    private Spinner<Integer> eventMinAgeSpinner;

    @FXML
    private Label eventNameLabel;

    @FXML
    private TextField eventNameTextFIeld;

    @FXML
    private ChoiceBox<String> eventTypeChoiceBox;

    @FXML
    private Label eventTypeEvent;

    @FXML
    private Menu languageMenu;

    @FXML
    private MenuItem russianLanguageMenuItem;

    @FXML
    private Button saveTicketButton;

    @FXML
    private Button saveTicketIfMinButton;

    @FXML
    private Label ticketCommentLabel;

    @FXML
    private TextField ticketCommentTextField;

    @FXML
    private Label ticketDiscountLabel;

    @FXML
    private Spinner<Integer> ticketDiscountSpinner;

    @FXML
    private Label ticketNameLabel;

    @FXML
    private TextField ticketNameTextField;

    @FXML
    private Label ticketPriceLabel;

    @FXML
    private Spinner<Integer> ticketPriceSpinner;

    @FXML
    private ChoiceBox<String> ticketTypeChoiceBox;

    @FXML
    private Label ticketTypeLabel;

    @FXML
    private Label ticketXLabel;

    @FXML
    private Spinner<Integer> ticketXSpinner;

    @FXML
    private Label ticketYLabel;

    @FXML
    private Spinner<Double> ticketYSpinner;

    @FXML
    private MenuItem turkishLanguageMenuItem;
    @FXML
    private Label headerLabel;

    private final LocaleManager localeManager;
    private boolean stopped = false;


    public EditTicketController(Stage stage, LocaleManager localeManager) {
        this.stage = stage;
        this.localeManager = localeManager;
    }

    public void initialize() {
        changeLanguage();

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
        clearForm();
        setMenuEvents();
    }

    private void setMenuEvents() {
        russianLanguageMenuItem.setOnAction(actionEvent -> {
            localeManager.changeCurrentLanguage("ru");
            changeLanguage();
        });
        englishLanguageMenuItem.setOnAction(actionEvent -> {
            localeManager.changeCurrentLanguage("en");
            changeLanguage();
        });
        denmarkLanguageMenuItem.setOnAction(actionEvent -> {
            localeManager.changeCurrentLanguage("da");
            changeLanguage();
        });
        turkishLanguageMenuItem.setOnAction(actionEvent -> {
            localeManager.changeCurrentLanguage("tr");
            changeLanguage();
        });
    }

    private void changeLanguage() {
        headerLabel.setText(localeManager.getName("edit.header"));
        ticketNameLabel.setText(localeManager.getName("edit.ticketName"));
        ticketXLabel.setText(localeManager.getName("edit.ticketX"));
        ticketYLabel.setText(localeManager.getName("edit.ticketY"));
        ticketPriceLabel.setText(localeManager.getName("edit.ticketPrice"));
        ticketDiscountLabel.setText(localeManager.getName("edit.ticketDiscount"));
        ticketCommentLabel.setText(localeManager.getName("edit.ticketComment"));
        ticketTypeLabel.setText(localeManager.getName("edit.ticketType"));
        eventNameLabel.setText(localeManager.getName("edit.eventName"));
        eventDateLabel.setText(localeManager.getName("edit.eventDate"));
        eventMinAgeLabel.setText(localeManager.getName("edit.eventMinAge"));
        eventDescriptionLabel.setText(localeManager.getName("edit.eventDescription"));
        eventTypeEvent.setText(localeManager.getName("edit.eventType"));
        saveTicketIfMinButton.setText(localeManager.getName("edit.saveTicketIfMinButton"));
        saveTicketButton.setText(localeManager.getName("edit.saveTicketButton"));
        languageMenu.setText(localeManager.getName("lang.languageMenu"));
        russianLanguageMenuItem.setText(localeManager.getName("lang.russianLanguageMenuItem"));
        denmarkLanguageMenuItem.setText(localeManager.getName("lang.denmarkLanguageMenuItem"));
        englishLanguageMenuItem.setText(localeManager.getName("lang.englishLanguageMenuItem"));
        turkishLanguageMenuItem.setText(localeManager.getName("lang.turkishLanguageMenuItem"));
    }

    public void showAndWait() {
        stage.setOnCloseRequest(windowEvent -> EditTicketController.this.stopped = true);
        stage.showAndWait();
    }

    public boolean checkStopped() {
        if (this.stopped) {
            this.stopped = false;
            return true;
        }
        return false;
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
            new Alert(Alert.AlertType.ERROR, localeManager.getName("edit.alerts.blankFieldError")).show();
            ticket = null;
        }
        return ticket;
    }

    public Ticket add() {
        showAndWait();
        if (checkStopped()) {
            return null;
        }
        Ticket ticket = fromFormsToEntity();
        clearForm();
        return ticket;
    }

    public void setDisable(boolean access) {
        ticketNameTextField.setDisable(access);
        ticketYSpinner.setDisable(access);
        ticketXSpinner.setDisable(access);
        ticketDiscountSpinner.setDisable(access);
        ticketPriceSpinner.setDisable(access);
        ticketCommentTextField.setDisable(access);
        ticketTypeChoiceBox.setDisable(access);

        eventNameTextFIeld.setDisable(access);
        eventMinAgeSpinner.setDisable(access);
        eventDateDatePicker.setDisable(access);
        eventDescription.setDisable(access);
        eventTypeChoiceBox.setDisable(access);

        saveTicketButton.setDisable(access);
        saveTicketIfMinButton.setDisable(access);
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
        if (checkStopped()) {
            return null;
        }
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
