package client.gui;

import client.commands.CommandManager;
import client.commands.Executable;
import client.commands.baseCommandsClient.InfoCommand;
import client.serverManager.ServerManager;
import general.models.Ticket;
import general.network.requests.*;
import general.network.responses.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MainController {
    @FXML
    private TableColumn<Ticket, String> eventCreationUserLogin;

    @FXML
    private MenuItem turkishLanguageMenuItem;

    @FXML
    private MenuItem uniquePriceMenuItem;
    @FXML
    private TextField sortingValueTextField;
    @FXML
    private Label mainUserLoginLabel;

    @FXML
    private MenuItem printFieldAscendingDiscountMenuItem;

    @FXML
    private Button refreshTableButton;

    @FXML
    private MenuItem russianLanguageMenuItem;

    @FXML
    private Label sortingByLabel;

    @FXML
    private Menu helpMenu;

    @FXML
    private MenuItem infoAboutCommandsMenuItem;

    @FXML
    private MenuItem infoMenuItem;

    @FXML
    private Menu languageMenu;
    @FXML
    private MenuItem DanishLanguageMenuItem;

    @FXML
    private MenuItem EnglishLanguageMenuItem;

    @FXML
    private MenuItem addTicketMenuItem;

    @FXML
    private BorderPane backgroundBorderPane;

    @FXML
    private MenuItem clearCollectionMenuItem;

    @FXML
    private Menu commandsMenu;

    @FXML
    private MenuItem countLessThanDiscountMenuItem;

    @FXML
    private Label currentUserLabel;

    @FXML
    private TableColumn<Ticket, LocalDateTime> eventDate;

    @FXML
    private TableColumn<Ticket, String> eventDescription;

    @FXML
    private TableColumn<Ticket, Long> eventID;

    @FXML
    private TableColumn<Ticket, Long> eventMinAge;

    @FXML
    private TableColumn<Ticket, String> eventName;

    @FXML
    private TableColumn<Ticket, String> eventType;

    @FXML
    private TableView<Ticket> mainTableView;

    @FXML
    private TableColumn<Ticket, String> ticketComment;

    @FXML
    private TableColumn<Ticket, String> ticketCreationUserLogin;

    @FXML
    private TableColumn<Ticket, Date> ticketDate;

    @FXML
    private TableColumn<Ticket, Integer> ticketDiscount;

    @FXML
    private TableColumn<Ticket, Integer> ticketID;

    @FXML
    private TableColumn<Ticket, String> ticketName;

    @FXML
    private TableColumn<Ticket, Integer> ticketPrice;

    @FXML
    private TableColumn<Ticket, String> ticketType;

    @FXML
    private TableColumn<Ticket, Integer> ticketX;

    @FXML
    private TableColumn<Ticket, Float> ticketY;

    private final ServerManager serverManager;
    private CommandManager commandManager;
    private EditTicketController editTicketController;
    private Stage stage;
    private Parent editParent;
    private URL editURL;
    private ObservableList<Ticket> lastCollection = null;
    private HashSet<Ticket> oldCollection = new HashSet<>();

    public void initialize() {
        mainTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        mainUserLoginLabel.setText(serverManager.getUser().getLogin());
        setCellFactories();
        startLoadingThread();
        setMenuEvents();
        try {
            loadEditController();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Произошла ошибка " + e);
            alert.show();
        }
    }

    public MainController(ServerManager serverManager, Stage stage, URL editURL, CommandManager commandManager) {
        this.serverManager = serverManager;
        this.stage = stage;
        this.editTicketController = new EditTicketController(new Stage());
        this.editURL = editURL;
        this.commandManager = commandManager;
    }

    public void loadCollection() {
        ShowRequest showRequest = new ShowRequest();
        ShowResponse showResponse = (ShowResponse) serverManager.sendRequestGetResponse(showRequest, true);

        List<Ticket> coll = showResponse.getResultList();
        HashSet<Ticket> resultAddingSet = new HashSet<>(oldCollection);
        resultAddingSet.addAll(coll);
        resultAddingSet.removeAll(oldCollection);

        if (resultAddingSet.toArray().length > 0 || coll.toArray().length == 0) {
            oldCollection = new HashSet<>(coll);
            Platform.runLater(() -> {
                mainTableView.setItems(FXCollections.observableArrayList(coll));
                mainTableView.refresh();
            });
        }
    }

    public void startLoadingThread() {
        new Thread(() -> {
            while (true) {
                loadCollection();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }

    private void setCellFactories() {
        ticketID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ticketName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ticketX.setCellValueFactory(new PropertyValueFactory<>("x"));
        ticketY.setCellValueFactory(new PropertyValueFactory<>("y"));
        ticketDate.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        ticketPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ticketDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        ticketComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        ticketType.setCellValueFactory(new PropertyValueFactory<>("type"));
        ticketCreationUserLogin.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getCreationUser().getLogin()));

        eventID.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getId()));
        eventName.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getName()));
        eventDate.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getDate()));
        eventMinAge.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getMinAge()));
        eventDescription.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getDescription()));
        eventType.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getEventType()).asString());
        eventCreationUserLogin.setCellValueFactory(ticket -> new SimpleObjectProperty<>(ticket.getValue().getEvent().getCreationUser().getLogin()));
    }

    private void setMenuEvents() {
        addTicketMenuItem.setOnAction(actionEvent -> addNewTicket());
        clearCollectionMenuItem.setOnAction(actionEvent -> clearCollection());
        countLessThanDiscountMenuItem.setOnAction(actionEvent -> countLessThanDiscount());
        printFieldAscendingDiscountMenuItem.setOnAction(actionEvent -> printFieldAscendingDiscount());
        uniquePriceMenuItem.setOnAction(actionEvent -> uniquePrice());
        infoMenuItem.setOnAction(actionEvent -> info());
        infoAboutCommandsMenuItem.setOnAction(actionEvent -> infoAboutCommands());
    }

    private void infoAboutCommands() {
        StringBuilder result = new StringBuilder();
        for (Executable command: commandManager.getCommands().values()) {
            result.append(command.getName()).append(" ").append(command.getArgs()).append("\t").append(command.getDescription()).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, result.toString());
        alert.setHeaderText("Успешно!");
        alert.setResizable(true);
        alert.show();
    }

    private void info() {
        InfoRequest infoRequest = new InfoRequest();
        InfoResponse infoResponse = (InfoResponse) serverManager.sendRequestGetResponse(infoRequest, true);
        if (infoResponse.getResult()) {
            List<Ticket> collection = infoResponse.getResultList();
            Date initializationDate = infoResponse.getInitializationDate();
            String result = "Коллекция типа Vector<Ticket>";
            result += "\n" + "Дата инициализации: " + initializationDate;
            result += "\n" + "Количество элементов: " + collection.size();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
            alert.setHeaderText("Успешно!");
            alert.show();
        }
    }
    private void uniquePrice() {
        PrintUniquePriceRequest printUniquePriceRequest = new PrintUniquePriceRequest();
        PrintUniquePriceResponse printUniquePriceResponse = (PrintUniquePriceResponse) serverManager.sendRequestGetResponse(printUniquePriceRequest, true);
        if (printUniquePriceResponse.getResult()) {
            StringBuilder result = new StringBuilder();
            for (Integer price: printUniquePriceResponse.getResultList()) {
                result.append(price.toString()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result.toString());
            alert.setHeaderText("Успешно!");
            alert.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Произошла ошибка " + printUniquePriceResponse.getMessage()).show();
        }
    }

    private void printFieldAscendingDiscount() {
        PrintFieldAscendingDiscountRequest printFieldAscendingDiscountRequest = new PrintFieldAscendingDiscountRequest();
        PrintFieldAscendingDiscountResponse printFieldAscendingDiscountResponse = (PrintFieldAscendingDiscountResponse) serverManager.sendRequestGetResponse(printFieldAscendingDiscountRequest, true);
        if (printFieldAscendingDiscountResponse.getResult()) {
            StringBuilder result = new StringBuilder();
            for (Long discount: printFieldAscendingDiscountResponse.getResultList()) {
                result.append(discount.toString()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result.toString());
            alert.setHeaderText("Успешно!");
            alert.show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Произошла ошибка " + printFieldAscendingDiscountResponse.getMessage());
        }
    }

    private void countLessThanDiscount() {
        TextInputDialog textInputDialog = new TextInputDialog("0");
        textInputDialog.setHeaderText("Введите значение discount");
        textInputDialog.showAndWait();
        String discount = textInputDialog.getResult();
        Long parsedDiscount;
        try {
            parsedDiscount = Long.valueOf(discount);
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, "Значение не является числом.").show();
            return;
        }
        if (!(0 <= parsedDiscount && parsedDiscount <= 100)) {
            new Alert(Alert.AlertType.ERROR, "Значение не помещается в пределы скидки.").show();
            return;
        }
        CountLessThanDiscountRequest countLessThanDiscountRequest = new CountLessThanDiscountRequest(parsedDiscount);
        CountLessThanDiscountResponse countLessThanDiscountResponse = (CountLessThanDiscountResponse) serverManager.sendRequestGetResponse(countLessThanDiscountRequest, true);
        if (countLessThanDiscountResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, "Успешно. Результат: " + countLessThanDiscountResponse.getCount()).show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Произошла ошибка " + countLessThanDiscountResponse.getMessage());
        }
    }

    private void clearCollection() {
        ClearRequest clearRequest = new ClearRequest();
        ClearResponse clearResponse = (ClearResponse) serverManager.sendRequestGetResponse(clearRequest, true);
        if (clearResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, "Успешно").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Произошла ошибка " + clearResponse.getMessage()).show();
        }
    }

    public void addNewTicket() {
        stage.setTitle("Изменение билета");
        Scene lastScene = stage.getScene();
        Ticket ticket = editTicketController.add();
        if (ticket == null) {
            return;
        }
        ticket.setCreationUser(serverManager.getUser());
        ticket.getEvent().setCreationUser(serverManager.getUser());
        AddRequest addRequest = new AddRequest(ticket);
        AddResponse addResponse = (AddResponse) serverManager.sendRequestGetResponse(addRequest, true);
        if (addResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, "Успешно!").showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, addResponse.getMessage()).showAndWait();
        }
        stage.setScene(lastScene);
        stage.setTitle("Основное окно");
    }

    private void loadEditController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(editURL);
        fxmlLoader.setController(editTicketController);
        editParent = fxmlLoader.load();
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(new Scene(editParent));
        BorderPane borderPane = (BorderPane) fxmlLoader.getNamespace().get("backgroundBorderPane");
        borderPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        editTicketController.getStage().setTitle("Изменение билета");
        editTicketController.getStage().setScene(jMetro.getScene());
        editTicketController.getStage().initModality(Modality.APPLICATION_MODAL);
        editTicketController.getStage().initOwner(stage);

    }

    public void openTicket(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Node node = (Node) event.getTarget();
            TableRow<Ticket> ticketTableRow;

            try {
                ticketTableRow = (TableRow<Ticket>) node.getParent();
            } catch (ClassCastException e) {
                try {
                    ticketTableRow = (TableRow<Ticket>) node.getParent().getParent();
                } catch (ClassCastException exception) {
                    return;
                }
            }
            Ticket ticket = ticketTableRow.getItem();
            editTicket(ticket);
        }
    }

    public void editTicket(Ticket ticket) {
        stage.setTitle("Изменение билета");
        Scene lastScene = stage.getScene();
        ticket = editTicketController.edit(ticket);
        ticket.setCreationUser(serverManager.getUser());
        ticket.getEvent().setCreationUser(serverManager.getUser());
        UpdateRequest updateRequest = new UpdateRequest(ticket.getId(), ticket);
        UpdateResponse updateResponse = (UpdateResponse) serverManager.sendRequestGetResponse(updateRequest, true);
        if (updateResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, "Успешно.").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Произошла ошибка " + updateResponse.getMessage()).show();
        }
        stage.setScene(lastScene);
        stage.setTitle("Основное окно");
    }
}
