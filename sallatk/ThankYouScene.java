package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

public class ThankYouScene {

    private Stage stage;
    private Scene productsScene; // To return to shopping
    private double orderTotal;

    public ThankYouScene(Stage stage) {
        this.stage = stage;
    }

    public Scene create() {
        VBox mainLayout = new VBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(20);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web(Theme.getBackgroundColor()), CornerRadii.EMPTY, Insets.EMPTY)));

        Label thankYouLabel = new Label("Thank you for shopping...");
        // Use same styling as RegistrationCompleteScene
        thankYouLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #55443D;");

        // Read random name from names.txt
        String name = "";
        try {
            List<String> names = Files.readAllLines(Paths.get("names.txt"));
            if (!names.isEmpty()) {
                name = names.get((int) (Math.random() * names.size()));
            }
        } catch (IOException e) {
            System.err.println("Error reading names.txt: " + e.getMessage());
        }

        Label nameLabel = new Label("Special thanks to " + name + " for making this possible!");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #55443D;");

        Button goHomeButton = Theme.createButton("Go Home");
        goHomeButton.setOnAction(e -> {
            if (productsScene != null) {
                stage.setScene(productsScene);
            }
        });

        mainLayout.getChildren().addAll(thankYouLabel, nameLabel, goHomeButton);
        return new Scene(mainLayout, 360, 740);
    }

    private VBox createConfirmationBox() {
        VBox box = new VBox(10);
        box.setStyle(
                "-fx-background-color: white;"
                + "-fx-padding: 15;"
                + "-fx-background-radius: 5;"
                + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        box.setAlignment(Pos.CENTER);

        // Order number (random for demo)
        String orderNumber = String.format("%06d", (int) (Math.random() * 999999));
        Label orderLabel = new Label("Order #" + orderNumber);
        orderLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Order date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Label dateLabel = new Label("Date: " + now.format(formatter));
        dateLabel.setFont(Font.font("Arial", 14));

        // Order total
        Label totalLabel = new Label(String.format("Total Amount: $%.2f", orderTotal));
        totalLabel.setFont(Font.font("Arial", 14));

        // Estimated delivery
        Label deliveryLabel = new Label("Estimated Delivery:\n"
                + now.plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        deliveryLabel.setFont(Font.font("Arial", 14));
        deliveryLabel.setTextAlignment(TextAlignment.CENTER);
        deliveryLabel.setWrapText(true);

        box.getChildren().addAll(
                orderLabel,
                dateLabel,
                totalLabel,
                deliveryLabel);

        return box;
    }

    private Button createContinueButton() {
        Button button = new Button("Back Home");
        button.setStyle(
                "-fx-background-color: #4CAF50;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 16px;"
                + "-fx-padding: 10 20;"
                + "-fx-background-radius: 5;");
        button.setMaxWidth(Double.MAX_VALUE);

        button.setOnAction(e -> {
            if (productsScene != null) {
                stage.setScene(productsScene);
            }
        });

        return button;
    }

    public void setProductsScene(Scene scene) {
        this.productsScene = scene;
    }

    public void setOrderTotal(double total) {
        this.orderTotal = total;
    }
}
