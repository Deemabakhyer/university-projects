package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.net.URL;

public class Theme {
    private static String backgroundColor = "#F5D8DA";
    public static final String TEXT_COLOR = "#55443D";
    public static final String BLACK_COLOR = "#000000";
    
    private static Font ebGaramondBold;
    
    static {
        try {
            URL fontResource = Theme.class.getResource("/fonts/ebgaramond_bold.ttf");
            if (fontResource != null) {
                ebGaramondBold = Font.loadFont(fontResource.toExternalForm(), 24);
            } else {
                System.err.println("Failed to load EB Garamond Bold font");
            }
        } catch (Exception e) {
            System.err.println("Error loading font: " + e.getMessage());
        }
    }

    public static void setBackgroundColor(String colorHex) {
        backgroundColor = colorHex;
    }

    public static String getBackgroundColor() {
        return backgroundColor;
    }

    public static void resetBackgroundColor() {
        backgroundColor = "#F4D7D9";
    }

    public static VBox createMainLayout() {
        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setBackground(
                new Background(new BackgroundFill(Color.web(backgroundColor), CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setPadding(new Insets(20));
        
        // Create a spacer that pushes content up
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        mainLayout.getChildren().add(spacer);
        
        return mainLayout;
    }

    public static Label createTitleLabel(String text) {
        Label titleLabel = new Label(text);
        titleLabel.setFont(ebGaramondBold);
        titleLabel.setTextFill(Color.web(TEXT_COLOR));
        return titleLabel;
    }

    public static Label createProductNameLabel(String text) {
        Label nameLabel = new Label(text);
        nameLabel.setFont(ebGaramondBold.font(16));
        nameLabel.setTextFill(Color.web(BLACK_COLOR));
        return nameLabel;
    }

    public static Button createButton(String text) {
        Button button = new Button(text);
        button.setFont(ebGaramondBold.font(20)); // Smaller size for buttons
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 20;" +
            "-fx-text-fill: " + TEXT_COLOR + ";" +
            "-fx-min-width: 280px;" +
            "-fx-min-height: 60px;"
        );
        return button;
    }

    public static Button createQuantityButton(String text) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 5;" +
            "-fx-text-fill: " + TEXT_COLOR + ";" +
            "-fx-min-width: 30px;" +
            "-fx-min-height: 30px;"
        );
        return button;
    }

    public static Button createAddToCartButton() {
        Button button = new Button("Add to Cart");
        button.setStyle(
            "-fx-background-color: #4CAF50;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 8 16;" +
            "-fx-background-radius: 5;"
        );
        return button;
    }

    public static HBox createProductCard() {
        HBox card = new HBox(10);
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-padding: 10;" +
            "-fx-background-radius: 5;" +
            "-fx-border-color: #E0E0E0;" +
            "-fx-border-radius: 5;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );
        card.setAlignment(Pos.CENTER_LEFT);
        return card;
    }

    public static Button createBackButton() {
        Button button = new Button("←");
        button.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-font-size: 20px;" +
            "-fx-padding: 5;" +
            "-fx-text-fill: " + TEXT_COLOR + ";"
        );
        return button;
    }

    public static HBox createBottomImages() {
        ImageView bag = new ImageView(new Image(Theme.class.getResource("/images/bag.jpg").toExternalForm()));
        bag.setFitWidth(124);
        bag.setFitHeight(150);

        ImageView cart = new ImageView(new Image(Theme.class.getResource("/images/cart.jpg").toExternalForm()));
        cart.setFitWidth(134);
        cart.setFitHeight(150);

        HBox bottomContainer = new HBox();
        bottomContainer.setPadding(new Insets(0));
        bottomContainer.setSpacing(20);
        bottomContainer.setAlignment(Pos.BOTTOM_CENTER);

        // Create containers that will stick to bottom
        VBox bagContainer = new VBox(bag);
        bagContainer.setAlignment(Pos.BOTTOM_LEFT);
        VBox cartContainer = new VBox(cart);
        cartContainer.setAlignment(Pos.BOTTOM_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bottomContainer.getChildren().addAll(bagContainer, spacer, cartContainer);
        VBox.setVgrow(bottomContainer, Priority.NEVER); // Don't let it grow
        
        return bottomContainer;
    }
}