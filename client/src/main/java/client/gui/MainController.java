package client.gui;

import client.Main;
import client.commands.CommandManager;
import client.commands.Executable;
import client.serverManager.ServerManager;
import general.models.Ticket;
import general.models.TicketType;
import general.network.requests.*;
import general.network.responses.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    private final URL drawingURL;
    private HashMap<Integer, String> userToColorMap = new HashMap<>();
    @FXML
    private TableColumn<Ticket, String> eventCreationUserLogin;

    @FXML
    private MenuItem turkishLanguageMenuItem;

    @FXML
    private MenuItem uniquePriceMenuItem;
    @FXML
    private TextField filteringValueTextField;
    @FXML
    private Label mainUserLoginLabel;

    @FXML
    private MenuItem printFieldAscendingDiscountMenuItem;

    @FXML
    private Button refreshTableButton;

    @FXML
    private MenuItem russianLanguageMenuItem;

    @FXML
    private Label filteringByLabel;

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
    @FXML
    private ChoiceBox<String> filteringColumnChoiceBox;
    @FXML
    private MenuItem englishLanguageMenuItem;
    @FXML
    private MenuItem denmarkLanguageMenuItem;
    @FXML
    private MenuItem TurkishLanguageMenuItem;
    @FXML
    private Button visualizationButton;
    @FXML
    private MenuItem changeUserColorsMenuItem;
    private final DrawingController drawingController;
    private final ServerManager serverManager;
    private CommandManager commandManager;
    private EditTicketController editTicketController;
    private Stage stage;
    private Parent editParent;
    private Parent drawingParent;
    private URL editURL;
    private ObservableList<Ticket> lastCollection = null;
    private HashSet<Ticket> oldCollection = new HashSet<>();
    private final LocaleManager localeManager;

    public void initialize() {
        changeLanguage();
        mainTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        mainUserLoginLabel.setText(serverManager.getUser().getLogin());
        setCellFactories();
        startLoadingThread();
        setMenuEvents();
        ObservableList<String> observableListTicket = FXCollections.observableArrayList();
        observableListTicket.addAll(new Ticket().getValuesForFiltering().keySet().stream().sorted().toList());

        filteringColumnChoiceBox.setItems(observableListTicket);
        filteringColumnChoiceBox.setValue("ticketID");
        try {
            loadEditController();
            loadDrawingController();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.error") + e);
            alert.show();
        }
    }

    private void loadDrawingController() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(drawingURL);
        fxmlLoader.setController(drawingController);
        drawingParent = fxmlLoader.load();
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(new Scene(drawingParent));
        StackPane borderPane = (StackPane) fxmlLoader.getNamespace().get("backgroundStackPane");
        borderPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);

        drawingController.getStage().setTitle(localeManager.getName("drawing.windowTitle"));
        drawingController.getStage().setScene(jMetro.getScene());
        drawingController.getStage().initModality(Modality.APPLICATION_MODAL);
        drawingController.getStage().initOwner(stage);
    }

    public MainController(ServerManager serverManager, Stage stage, URL editURL, URL drawingURL, CommandManager commandManager, LocaleManager localeManager) {
        this.localeManager = localeManager;
        this.serverManager = serverManager;
        this.stage = stage;
        this.editTicketController = new EditTicketController(new Stage(), localeManager);
        this.drawingController = new DrawingController(new Stage(), localeManager, userToColorMap);
        this.editURL = editURL;
        this.drawingURL = drawingURL;
        this.commandManager = commandManager;
    }

    public void loadCollection() {
        ShowRequest showRequest = new ShowRequest();
        ShowResponse showResponse = (ShowResponse) serverManager.sendRequestGetResponse(showRequest, true);

        List<Ticket> coll = showResponse.getResultList();
        for (Ticket ticket : coll) {
            if (!userToColorMap.containsKey(ticket.getCreationUserID())) {

                Double result = Math.random() * 16777216;
                userToColorMap.put(ticket.getCreationUserID(), String.format("%06x", result.intValue()));
            }
        }
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
        mainTableView.setRowFactory(ticketTableView -> new TableRow<Ticket>() {
            @Override
            protected void updateItem(Ticket ticket, boolean empty) {
                super.updateItem(ticket, empty);
                if (ticket == null) {
                    setStyle("");
                    return;
                }
                setStyle("-fx-background-color: #" + userToColorMap.getOrDefault(ticket.getCreationUserID(), "FFFFFF"));
            }
        });
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
        refreshTableButton.setOnAction(actionEvent -> filterTable());

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

        visualizationButton.setOnAction(actionEvent -> drawElements());
        changeUserColorsMenuItem.setOnAction(actionEvent -> countNewColors(mainTableView.getItems()));
    }

    private void countNewColors(List<Ticket> collection) {
        userToColorMap.clear();
        for (Ticket ticket : collection) {
            if (!userToColorMap.containsKey(ticket.getCreationUserID())) {

                double result = Math.random() * 16777216;
                userToColorMap.put(ticket.getCreationUserID(), String.format("%06x", (int) result));
            }
        }
        mainTableView.refresh();
    }

    private void changeLanguage() {
        languageMenu.setText(localeManager.getName("lang.languageMenu"));
        russianLanguageMenuItem.setText(localeManager.getName("lang.russianLanguageMenuItem"));
        denmarkLanguageMenuItem.setText(localeManager.getName("lang.denmarkLanguageMenuItem"));
        englishLanguageMenuItem.setText(localeManager.getName("lang.englishLanguageMenuItem"));
        turkishLanguageMenuItem.setText(localeManager.getName("lang.turkishLanguageMenuItem"));

        commandsMenu.setText(localeManager.getName("main.commandMenu"));
        addTicketMenuItem.setText(localeManager.getName("main.menu.addNewTicket"));
        clearCollectionMenuItem.setText(localeManager.getName("main.menu.clearCollection"));
        countLessThanDiscountMenuItem.setText(localeManager.getName("main.menu.countTicketsThat"));
        printFieldAscendingDiscountMenuItem.setText(localeManager.getName("main.menu.printDiscount"));
        uniquePriceMenuItem.setText(localeManager.getName("main.menu.uniquePrice"));
        infoMenuItem.setText(localeManager.getName("main.menu.infoAboutCollection"));

        helpMenu.setText(localeManager.getName("main.helpMenu"));
        infoAboutCommandsMenuItem.setText(localeManager.getName("main.help.help"));

        filteringByLabel.setText(localeManager.getName("main.filterByColumnLabel"));
        refreshTableButton.setText(localeManager.getName("main.refreshTableButton"));
        currentUserLabel.setText(localeManager.getName("main.currentUserLabel"));

        visualizationButton.setText(localeManager.getName("drawing.windowTitle"));
        changeUserColorsMenuItem.setText(localeManager.getName("main.menu.changeUserColors"));
    }

    private void filterTable() {
        String column = filteringColumnChoiceBox.getValue();
        String value = filteringValueTextField.getText();

        ShowRequest showRequest = new ShowRequest();
        ShowResponse showResponse = (ShowResponse) serverManager.sendRequestGetResponse(showRequest, true);

        List<Ticket> collection = showResponse.getResultList();
        if (value.isEmpty()) {
            Platform.runLater(() -> {
                mainTableView.setItems(FXCollections.observableArrayList(collection));
                mainTableView.refresh();
            });
            return;
        }
        List<HashMap<String, String>> collectionForFiltering = collection.stream().map(Ticket::getValuesForFiltering).toList();
        collectionForFiltering = collectionForFiltering.stream().filter(x -> x.get(column).equals(value)).toList();
        List<String> collectionOfID = collectionForFiltering.stream().map(x -> x.get("ticketID")).toList();
        List<Ticket> coll = collection.stream().filter(x -> collectionOfID.contains(String.valueOf(x.getId()))).toList();
        Platform.runLater(() -> {
            mainTableView.setItems(FXCollections.observableArrayList(coll));
            mainTableView.refresh();
        });
    }

    private void infoAboutCommands() {
        StringBuilder result = new StringBuilder();
        for (Executable command : commandManager.getCommands().values()) {
            result.append(command.getName()).append(" ").append(command.getArgs()).append("\t").append(command.getDescription()).append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, result.toString());
        alert.setHeaderText(localeManager.getName("main.alerts.success"));
        alert.setResizable(true);
        alert.show();
    }

    private void info() {
        InfoRequest infoRequest = new InfoRequest();
        InfoResponse infoResponse = (InfoResponse) serverManager.sendRequestGetResponse(infoRequest, true);
        if (infoResponse.getResult()) {
            List<Ticket> collection = infoResponse.getResultList();
            Date initializationDate = infoResponse.getInitializationDate();
            String result = localeManager.getName("main.collectionType") + " Vector<Ticket>";
            result += "\n" + localeManager.getName("main.initializationDate") + initializationDate;
            result += "\n" + localeManager.getName("main.itemsQuantity") + collection.size();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result);
            alert.setHeaderText(localeManager.getName("main.alerts.success"));
            alert.show();
        }
    }

    private void uniquePrice() {
        PrintUniquePriceRequest printUniquePriceRequest = new PrintUniquePriceRequest();
        PrintUniquePriceResponse printUniquePriceResponse = (PrintUniquePriceResponse) serverManager.sendRequestGetResponse(printUniquePriceRequest, true);
        if (printUniquePriceResponse.getResult()) {
            StringBuilder result = new StringBuilder();
            for (Integer price : printUniquePriceResponse.getResultList()) {
                result.append(price.toString()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result.toString());
            alert.setHeaderText(localeManager.getName("main.alerts.success"));
            alert.show();
        } else {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.error") + printUniquePriceResponse.getMessage()).show();
        }
    }

    private void printFieldAscendingDiscount() {
        PrintFieldAscendingDiscountRequest printFieldAscendingDiscountRequest = new PrintFieldAscendingDiscountRequest();
        PrintFieldAscendingDiscountResponse printFieldAscendingDiscountResponse = (PrintFieldAscendingDiscountResponse) serverManager.sendRequestGetResponse(printFieldAscendingDiscountRequest, true);
        if (printFieldAscendingDiscountResponse.getResult()) {
            StringBuilder result = new StringBuilder();
            for (Long discount : printFieldAscendingDiscountResponse.getResultList()) {
                result.append(discount.toString()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, result.toString());
            alert.setHeaderText(localeManager.getName("main.alerts.success"));
            alert.show();
        } else {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.error") + printFieldAscendingDiscountResponse.getMessage());
        }
    }

    private void countLessThanDiscount() {
        TextInputDialog textInputDialog = new TextInputDialog("0");
        textInputDialog.setHeaderText(localeManager.getName("main.alerts.discountQuantity"));
        textInputDialog.showAndWait();
        String discount = textInputDialog.getResult();
        Long parsedDiscount;
        try {
            parsedDiscount = Long.valueOf(discount);
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.notNumber")).show();
            return;
        }
        if (!(0 <= parsedDiscount && parsedDiscount <= 100)) {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.discountError")).show();
            return;
        }
        CountLessThanDiscountRequest countLessThanDiscountRequest = new CountLessThanDiscountRequest(parsedDiscount);
        CountLessThanDiscountResponse countLessThanDiscountResponse = (CountLessThanDiscountResponse) serverManager.sendRequestGetResponse(countLessThanDiscountRequest, true);
        if (countLessThanDiscountResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, localeManager.getName("main.alerts.success") + countLessThanDiscountResponse.getCount()).show();
        } else {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.error") + countLessThanDiscountResponse.getMessage());
        }
    }

    private void clearCollection() {
        ClearRequest clearRequest = new ClearRequest();
        ClearResponse clearResponse = (ClearResponse) serverManager.sendRequestGetResponse(clearRequest, true);
        if (clearResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, localeManager.getName("main.alerts.success")).show();
        } else {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.error") + clearResponse.getMessage()).show();
        }
    }

    public void addNewTicket() {
        editTicketController.setDisable(false);
        stage.setTitle(localeManager.getName("main.ticketEditing"));
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
            new Alert(Alert.AlertType.INFORMATION, localeManager.getName("main.alerts.success")).showAndWait();
        } else {
            new Alert(Alert.AlertType.ERROR, localeManager.getName("main.alerts.fieldsError")).showAndWait();
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

        editTicketController.getStage().setTitle(localeManager.getName("main.ticketEditing"));
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
        editTicketController.setDisable(!Objects.equals(ticket.getCreationUserID(), serverManager.getUser().getId()));
        stage.setTitle(localeManager.getName("main.ticketEditing"));
        Scene lastScene = stage.getScene();
        ticket = editTicketController.edit(ticket);
        if (ticket == null) {
            return;
        }
        ticket.setCreationUser(serverManager.getUser());
        ticket.getEvent().setCreationUser(serverManager.getUser());
        UpdateRequest updateRequest = new UpdateRequest(ticket.getId(), ticket);
        UpdateResponse updateResponse = (UpdateResponse) serverManager.sendRequestGetResponse(updateRequest, true);
        if (updateResponse.getResult()) {
            new Alert(Alert.AlertType.INFORMATION, localeManager.getName("main.alerts.success")).show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, localeManager.getName("main.alers.error") + updateResponse.getMessage()).show();
        }
        stage.setScene(lastScene);
        stage.setTitle(localeManager.getName("main.mainWindow"));
    }

    public void drawElements() {
        drawingController.draw(mainTableView.getItems());
        Ticket ticket = drawingController.getMainTicket();
        if (ticket == null) {
            return;
        }
        editTicketController.setDisable(true);
        editTicketController.edit(ticket);
    }
}
