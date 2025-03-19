package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AccountQuestionScene {
    private Stage stage;
    private Scene loginScene;
    private Scene registerScene;

    public AccountQuestionScene(Stage stage) {
        this.stage = stage;
    }

    public Scene create() {
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#F5D8DA"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setPadding(new Insets(20));

        Label questionLabel = Theme.createTitleLabel("Do you have an account?");
        questionLabel.setAlignment(Pos.CENTER);
        
        RadioButton yesButton = new RadioButton("YES");
        yesButton.setStyle("-fx-font-size: 18px; -fx-text-fill: #55443D;");
        
        RadioButton noButton = new RadioButton("NO");
        noButton.setStyle("-fx-font-size: 18px; -fx-text-fill: #55443D;");

        ToggleGroup group = new ToggleGroup();
        yesButton.setToggleGroup(group);
        noButton.setToggleGroup(group);

        Button nextButton = Theme.createButton("NEXT");
        nextButton.setAlignment(Pos.CENTER);
        nextButton.setOnAction(e -> {
            if (yesButton.isSelected() && loginScene != null) {
                stage.setScene(loginScene);
            } else if (noButton.isSelected() && registerScene != null) {
                stage.setScene(registerScene);
            }
        });

        mainLayout.getChildren().addAll(
            questionLabel,
            yesButton,
            noButton,
            nextButton
        );

        return new Scene(mainLayout, 360, 740); // Samsung 8 dimensions
    }

    public void setLoginScene(Scene scene) {
        this.loginScene = scene;
    }

    public void setRegisterScene(Scene scene) {
        this.registerScene = scene;
    }
}
