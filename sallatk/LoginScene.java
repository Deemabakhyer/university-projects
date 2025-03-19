package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginScene {

    private Stage stage;
    private Scene nextScene;

    public LoginScene(Stage stage) {
        this.stage = stage;
    }

    public Scene create() {
        VBox mainLayout = Theme.createMainLayout();

        // Image setup
        ImageView percentageIcon = new ImageView(new Image(getClass().getResource("/images/percentage.jpg").toExternalForm()));
        percentageIcon.setFitWidth(190);
        percentageIcon.setFitHeight(120);

        // Title text
        Label title = Theme.createTitleLabel("Please complete the following\ninformation");
        title.setAlignment(Pos.CENTER);

        // Form fields
        TextField phoneInput = createDashedField("Phone number");
        TextField passwordInput = createDashedField("Password");

        // Create form labels
        Label phoneLabel = createFormLabel("PHONE NUMBER");
        Label passwordLabel = createFormLabel("PASSWORD");

        // Next button
        Button nextButton = Theme.createButton("NEXT");
        nextButton.setOnAction(e -> {
            System.out.println("[LoginScene] Attempting to validate inputs and transition...");
            if (validateInputs(phoneInput.getText(), passwordInput.getText())) {
                stage.setScene(nextScene);
            } else {
                System.out.println("[LoginScene] Input validation failed");
            }
        });

        // Layout
        mainLayout.getChildren().addAll(
                percentageIcon,
                title,
                createInputSection(phoneLabel, phoneInput, 40),
                createInputSection(passwordLabel, passwordInput, 20),
                nextButton
        );

        // Add bottom images that stick to bottom
        HBox bottomContainer = Theme.createBottomImages();
        mainLayout.getChildren().add(bottomContainer);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + Theme.getBackgroundColor() + ";");
        root.setCenter(mainLayout);

        return new Scene(root, 360, 740);
    }

    private TextField createDashedField(String prompt) {
        TextField field = prompt.equals("Password") ? new PasswordField() : new TextField();
        field.setPromptText(prompt);
        field.setStyle(
                "-fx-background-color: transparent;"
                + "-fx-border-color: white;"
                + "-fx-border-width: 2px;"
                + "-fx-border-style: segments(5, 5);"
                + "-fx-border-radius: 5;"
                + "-fx-text-fill: " + Theme.TEXT_COLOR + ";"
                + "-fx-background-insets: 0;"
        );
        field.setPrefSize(280, 48);
        return field;
    }

    private Label createFormLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        label.setEffect(new DropShadow(4, Color.BLACK));
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private VBox createInputSection(Label label, TextField input, double topMargin) {
        VBox container = new VBox(8);
        container.setAlignment(Pos.CENTER);
        VBox.setMargin(label, new Insets(topMargin, 0, 8, 0));
        container.getChildren().addAll(label, input);
        return container;
    }

    private boolean validateInputs(String phone, String password) {
        System.out.println("[LoginScene] Validating inputs - Phone: " + phone + ", Password: [MASKED]");

        if (phone == null || phone.trim().isEmpty() || !phone.matches("\\d+")) {
            System.out.println("[LoginScene] Validation failed: Invalid phone number format");
            showAlert("Invalid Phone", "Please enter a valid phone number");
            return false;
        }
        if (password == null || password.length() < 6) {
            System.out.println("[LoginScene] Validation failed: Password too short");
            showAlert("Invalid Password", "Password must be at least 6 characters long");
            return false;
        }
//DEEMA---------------------------------------------------------------------------------
        try {
            //authenticate customer
            System.out.println("[LoginScene] Basic validation passed, attempting database authentication");
            Customer authenticatedCustomer = Customer.authenticate(phone, password);//takes parameters from validateInputs(phoneInput.getText(), passwordInput.getText())

            if (authenticatedCustomer != null) {
                System.out.println("[LoginScene] Database authentication successful");

                // Store the authenticated customer as logged in customer in App
                App.setLoggedInCustomer(authenticatedCustomer);
                System.out.println("[LoginScene] Set logged in customer: " + authenticatedCustomer.getPhoneNumber());

                return true;
            } else {
                System.out.println("[LoginScene] Database authentication failed");
                showAlert("Login Failed", "Invalid phone number or password");
                return false;
            }
        } catch (Exception e) {
            System.err.println("[LoginScene] Database authentication error: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "Could not verify credentials. Please try again.");
            return false;
        }
    }
//------------------------------------------------------------------------------------------------------

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setNextScene(Scene scene) {
        this.nextScene = scene;
    }
}
