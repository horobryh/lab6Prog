package client.gui;

import general.models.Ticket;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.List;

public class DrawingController {

    @FXML
    private Pane drawingPane;

    @FXML
    private StackPane backgroundStackPane;

    @FXML
    private BorderPane upperBorderPane;

    private final LocaleManager localeManager;
    private final Stage stage;
    private Ticket mainTicket;
    private final HashMap<Integer, String> colors;

    public DrawingController(Stage stage, LocaleManager localeManager, HashMap<Integer, String> colors) {
        this.stage = stage;
        this.localeManager = localeManager;
        this.colors = colors;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void initialize() {
        stage.setResizable(false);


    }

    public Ticket draw(List<Ticket> elements) {
        stage.show();
        drawingPane.getChildren().clear();
        for (Ticket ticket: elements) {
            TicketCircle circle = new TicketCircle(stage.getWidth() / 2, stage.getHeight() / 2, 5, ticket);
            circle.setOnMouseClicked(mouseEvent -> setMainTicket((TicketCircle) mouseEvent.getSource()));
            circle.setFill(Color.valueOf(colors.get(ticket.getCreationUserID())));
            drawingPane.getChildren().add(circle);
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.seconds(3));
            translateTransition.setNode(circle);

            translateTransition.setToX(circle.getTicket().getX());
            translateTransition.setToY(circle.getTicket().getY());
            translateTransition.play();

            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(3), circle);
            scaleTransition.setToX(5);
            scaleTransition.setToY(5);
            scaleTransition.play();
        }
        stage.hide();
        stage.showAndWait();
        return null;
    }

    private void setMainTicket(TicketCircle ticket) {
        this.mainTicket = ticket.getTicket();
        stage.close();
    }

    public Ticket getMainTicket() {
        Ticket ticket = this.mainTicket;
        this.mainTicket = null;
        return ticket;
    }

    private class TicketCircle extends Circle {
        private Ticket ticket = null;
        public TicketCircle(double v1, double v2, double r, Ticket ticket) {
            super(v1, v2, r);
            this.ticket = ticket;
        }

        public Ticket getTicket() {
            return this.ticket;
        }
    }
}
