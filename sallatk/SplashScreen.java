package com.mycompany.sallatk;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreen {
    private Stage stage;
    private Scene nextScene;

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        // Create welcome label
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        welcomeLabel.setStyle("-fx-text-fill: #55443D;");
        welcomeLabel.setOpacity(0); // Start fully transparent

        // Create layout
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(welcomeLabel);
        root.setStyle("-fx-background-color: #F5D8DA;"); // Match app theme

        // Create scene with Samsung 8 dimensions
        Scene scene = new Scene(root, 360, 740);
        stage.setScene(scene);

        // Create fade in animation
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.5), welcomeLabel);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Create fade out animation
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.5), welcomeLabel);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Add pause between animations
        PauseTransition pause = new PauseTransition(Duration.seconds(1));

        // Combine animations
        SequentialTransition sequence = new SequentialTransition(fadeIn, pause, fadeOut);

        // After animation completes, show the account question scene
        sequence.setOnFinished(e -> {
            if (nextScene != null) {
                stage.setScene(nextScene);
            }
        });

        sequence.play();
    }

    public void setNextScene(Scene scene) {
        this.nextScene = scene;
    }
}