package client.gui;

import client.builders.FirstStartBuilder;
import client.commands.CommandManager;
import general.validators.exceptions.AuthorizationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;
import java.net.URL;

import static java.lang.System.exit;

public class AuthController {
    private final FirstStartBuilder firstStartBuilder;
    private final LocaleManager localeManager;
    @FXML
    private MenuItem englishLanguageMenuItem;

    @FXML
    private VBox authBox;

    @FXML
    private Button authButton;

    @FXML
    private Label authorizationHeaderLabel;

    @FXML
    private BorderPane backgroundBorderPane;

    @FXML
    private MenuItem denmarkLanguageMenuItem;

    @FXML
    private Menu languageMenu;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Button portBindingButton;

    @FXML
    private HBox portBox;

    @FXML
    private Spinner<Integer> portSpinner;

    @FXML
    private MenuItem russianLanguageMenuItem;

    @FXML
    private MenuItem turkishLanguageMenuItem;
    private URL mainURL;
    private URL editURL;
    private Stage stage;

    public void authClicked() {
        try {
            String message = firstStartBuilder.authorization(loginTextField.getText(), passwordPasswordField.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.APPLY);
            alert.setHeaderText("Авторизация");
            alert.show();
            authBox.setDisable(true);
            portBox.setDisable(false);
        } catch (AuthorizationException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert1.setHeaderText("Ошибка авторизации");
            alert1.show();
            return;
        }
        try {
            setMainScene();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Произошла непредвиденная ошибка: " + e, ButtonType.CLOSE);
            alert.showAndWait();
            exit(0);
        }
    }

    public void portClicked() {
        try {
            firstStartBuilder.setPort(portSpinner.getValue());
        } catch (NullPointerException | IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            Alert alert1 = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert1.setHeaderText("Произошла ошибка соединения с сервером");
            alert1.show();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Соединение успешно!", ButtonType.APPLY);
        alert.setHeaderText("Соединение с сервером");
        alert.show();
        portBox.setDisable(true);
        authBox.setDisable(false);
    }

    public void initialize() {
        portSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 65535, 1));
        setMenuEvents();
    }

    public AuthController(FirstStartBuilder firstStartBuilder, URL mainURL, URL editURL, Stage stage, LocaleManager localeManager) {
        this.firstStartBuilder = firstStartBuilder;
        this.mainURL = mainURL;
        this.stage = stage;
        this.editURL = editURL;
        this.localeManager = localeManager;
    }

    public void setMainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(mainURL);
        fxmlLoader.setController(new MainController(firstStartBuilder.getServerManager(), stage, editURL, new CommandManager()));

        Parent root = fxmlLoader.load();
        JMetro jMetro = new JMetro(Style.DARK);
        jMetro.setScene(new Scene(root));
        BorderPane borderPane = (BorderPane) fxmlLoader.getNamespace().get("backgroundBorderPane");
        borderPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        stage.setTitle("Основное окно");
        stage.setScene(jMetro.getScene());
        stage.show();
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

    public void changeLanguage() {
        System.out.println("CHANGE!");
        authorizationHeaderLabel.setText(localeManager.getName("auth.authorizationHeaderLabel"));
        portBindingButton.setText(localeManager.getName("auth.portBindingButton"));
        loginTextField.setPromptText(localeManager.getName("auth.loginTextField"));
        passwordPasswordField.setPromptText(localeManager.getName("auth.passwordPasswordField"));
        authButton.setText(localeManager.getName("auth.authButton"));

        languageMenu.setText(localeManager.getName("auth.languageMenu"));
        russianLanguageMenuItem.setText(localeManager.getName("auth.russianLanguageMenuItem"));
        englishLanguageMenuItem.setText(localeManager.getName("auth.englishLanguageMenuItem"));
        denmarkLanguageMenuItem.setText(localeManager.getName("auth.denmarkLanguageMenuItem"));
        turkishLanguageMenuItem.setText(localeManager.getName("auth.turkishLanguageMenuItem"));
    }
}
