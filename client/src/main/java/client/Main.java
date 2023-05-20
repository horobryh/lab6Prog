package client;

import client.gui.AuthController;
import client.builders.FirstStartBuilder;
import client.gui.LocaleManager;
import clojure.lang.IFn;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.JMetroStyleClass;
import jfxtras.styles.jmetro.Style;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

public class Main extends Application {
    private static final FirstStartBuilder firstStartBuilder = new FirstStartBuilder();
    private static URL xmlUrlAuth = Main.class.getResource("/authWindow.fxml");
    private static URL xmlUrlMain = Main.class.getResource("/mainWindow.fxml");
    private static URL xmlUrlEdit = Main.class.getResource("/editTicketWindow.fxml");
    private static ResourceBundle bundleRU = ResourceBundle.getBundle("languages");
    private static ResourceBundle bundleEN = ResourceBundle.getBundle("languages_en");
    private static ResourceBundle bundleDA = ResourceBundle.getBundle("languages_da");
    private static ResourceBundle bundleTR = ResourceBundle.getBundle("languages_tr");

    public static void main(String[] args) {
        launch(args);
        exit(0);

//        new Thread(() -> launch(args)).start();
//        ServerManager serverManager = null;
//        Scanner scanner = new Scanner(System.in);
//        String obj = null;
//        Integer port;
//        do {
//            try {
//                port = authController.portSpinner.getValue();
//                if (port != null) break;
//            } catch (NumberFormatException e) {
//                System.out.println(e + "Введенный аргумент не число.");
//            } catch (NullPointerException e) {
//                System.out.println(new Date());
//            }
//
//        } while (true);
//        try {
//            serverManager = ServerManager.getInstance(InetAddress.getLocalHost(), port);
//            Socket socket = new Socket(InetAddress.getLocalHost(), port);
//            socket.close();
//            System.out.println("Сервер подключен.");
//        } catch (IOException | IllegalArgumentException e) {
//            System.out.println("Произошла ошибка подключения ServerManager " + e);
//            System.out.println("Завершение программы...");
//            System.exit(0);
//        }
//        CommandManager commandManager = CommandManager.getInstance();
//        CommandRegister commandRegister = CommandRegister.getInstance();
//
//        commandRegister.registerCommands(commandManager, serverManager);
//
//        User user = authorization(serverManager);
//
//        serverManager.addUser(user);
//
//        InputStream inputStream = System.in;
//
//        ScannerManager scannerManager = new ScannerManager(inputStream, commandManager, true);
//        try {
//            scannerManager.startScan();
//        } catch (NullPointerException e) {
//            System.out.println("Произошла ошибка, сервер недоступен " + e);
//            commandManager.getCommands().get("exit").execute(new String[1]);
//        }
    }

//    private static User authorization(ServerManager serverManager) {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Авторизация. Если вы еще не зарегистрированы в системе, придумайте ваши логин и пароль.");
//        System.out.print("Введите логин:\t");
//        String login = scanner.nextLine();
//        System.out.print("Введите пароль:\t");
//        String password = scanner.nextLine();
//
//        return new AuthCommand(serverManager).getUser(login, password);
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> Platform.runLater(() -> showErrorDialog(t, e)));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(xmlUrlAuth);
        LocaleManager localeManager = LocaleManager.getInstance(bundleRU, bundleEN, bundleDA, bundleTR);
        AuthController authController = new AuthController(firstStartBuilder, xmlUrlMain, xmlUrlEdit, stage, localeManager);
        loader.setController(authController);

        Parent root = loader.load();

        JMetro jMetro = new JMetro(Style.DARK);
        Scene scene = new Scene(root);
        jMetro.setScene(scene);
        BorderPane borderPane = (BorderPane) loader.getNamespace().get("backgroundBorderPane");
        borderPane.getStyleClass().add(JMetroStyleClass.BACKGROUND);
        stage.setScene(scene);
        stage.setTitle("Авторизация");
        stage.show();
    }

    private void showErrorDialog(Thread t, Throwable e) {
        e.printStackTrace();
    }
}