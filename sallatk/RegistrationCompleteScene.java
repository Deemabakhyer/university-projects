package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.net.URL;

public class RegistrationCompleteScene {
    private Stage stage;
    private Scene nextScene; // Products scene

    public RegistrationCompleteScene(Stage stage) {
        this.stage = stage;
    }

    public Scene create() {
        // Load percentage icon
        ImageView percentageIcon = new ImageView(new Image(getClass().getResource("/images/percentage.jpg").toExternalForm()));
        percentageIcon.setFitWidth(190);
        percentageIcon.setFitHeight(120);

        // Create labels with styling
        Label completeLabel = new Label("Registration is complete");
        completeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #55443D;");
        
        Label shoppingLabel = new Label("Let's start shopping...");
        shoppingLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #55443D;");

        // Create next button
        Button nextButton = createOutlinedButton("NEXT");
        nextButton.setOnAction(e -> {
            System.out.println("[RegistrationCompleteScene] Next button clicked");
            if (nextScene != null) {
                try {
                    stage.setScene(nextScene);
                } catch (Exception ex) {
                    System.err.println("[RegistrationCompleteScene] Error during scene transition: " + ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                System.err.println("[RegistrationCompleteScene] Error: nextScene is null - cannot transition");
            }
        });

        // Create layout
        VBox mainLayout = new VBox(20,
            percentageIcon,
            completeLabel,
            shoppingLabel,
            nextButton
        );
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#F4D7D9"), CornerRadii.EMPTY, Insets.EMPTY)));

        // Add bottom images
        HBox bottomContainer = createBottomImages();
        
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F4D7D9;");
        root.setCenter(mainLayout);
        root.setBottom(bottomContainer);
        BorderPane.setAlignment(percentageIcon, Pos.TOP_LEFT);
        BorderPane.setMargin(percentageIcon, new Insets(20, 0, 0, 20));

        return new Scene(root, 360, 740); // Samsung 8 dimensions
    }

    private Button createOutlinedButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
            "-fx-background-color: transparent;" +
            "-fx-border-color: white;" +
            "-fx-border-width: 2px;" +
            "-fx-border-radius: 20;" +
            "-fx-background-insets: 0;" +
            "-fx-font-size: 18px;"
        );
        URL fontResource = getClass().getResource("/fonts/ukijka3d.ttf");
        if (fontResource == null) {
            System.err.println("Critical Error: Font resource not found at /fonts/ukijka3d.ttf");
            System.exit(1);
        }
        Font customFont = Font.loadFont(fontResource.toExternalForm(), 28);
        btn.setFont(customFont);
        btn.setMinSize(120, 48);
        return btn;
    }

    private HBox createBottomImages() {
        ImageView bag = new ImageView(new Image(getClass().getResource("/images/bag.jpg").toExternalForm()));
        bag.setFitWidth(124);
        bag.setFitHeight(150);

        ImageView cart = new ImageView(new Image(getClass().getResource("/images/cart.jpg").toExternalForm()));
        cart.setFitWidth(134);
        cart.setFitHeight(150);

        HBox bottomContainer = new HBox();
        bottomContainer.setPadding(new Insets(0));
        bottomContainer.setSpacing(20);
        bottomContainer.setAlignment(Pos.BOTTOM_CENTER);

        StackPane bagStack = new StackPane(bag);
        StackPane cartStack = new StackPane(cart);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bottomContainer.getChildren().addAll(bagStack, spacer, cartStack);
        return bottomContainer;
    }

    public void setNextScene(Scene scene) {
        this.nextScene = scene;
    }
}
