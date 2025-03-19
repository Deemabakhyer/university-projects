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

public class RegisterScene {

    private Stage stage;
    private Scene nextScene;

    public RegisterScene(Stage stage) {
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
        TextField emailInput = createDashedField("Email");
        TextField phoneInput = createDashedField("Phone number");
        TextField passwordInput = createDashedField("Password");

        // Create form labels
        Label emailLabel = createFormLabel("EMAIL");
        Label phoneLabel = createFormLabel("PHONE NUMBER");
        Label passwordLabel = createFormLabel("PASSWORD");

        // Sign up button
        Button signUpButton = Theme.createButton("SIGN UP");
        signUpButton.setOnAction(e -> {
            System.out.println("[RegisterScene] Attempting to validate inputs and transition...");
            if (validateInputs(emailInput.getText(), phoneInput.getText(), passwordInput.getText())) {
                if (nextScene != null) {
                    System.out.println("[RegisterScene] Database operation successful, scheduling scene transition");
                    javafx.application.Platform.runLater(() -> {
                        try {
                            System.out.println("[RegisterScene] Executing scene transition on JavaFX thread");
                            stage.setScene(nextScene);
                            System.out.println("[RegisterScene] Scene transition successful");
                        } catch (Exception ex) {
                            System.err.println("[RegisterScene] Error during scene transition: " + ex.getMessage());
                            ex.printStackTrace();
                            showAlert("Error", "Failed to move to next screen: " + ex.getMessage());
                        }
                    });
                } else {
                    System.err.println("[RegisterScene] Error: nextScene is null - scene transition failed");
                    showAlert("Error", "Application configuration error - please contact support");
                }
            } else {
                System.out.println("[RegisterScene] Input validation failed");
            }
        });

        // Layout
        mainLayout.getChildren().addAll(
                percentageIcon,
                title,
                createInputSection(emailLabel, emailInput, 40),
                createInputSection(phoneLabel, phoneInput, 20),
                createInputSection(passwordLabel, passwordInput, 20),
                signUpButton
        );

        // Add bottom images that stick to bottom
        HBox bottomContainer = Theme.createBottomImages();
        mainLayout.getChildren().add(bottomContainer);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + Theme.getBackgroundColor() + ";");
        root.setCenter(mainLayout);
        BorderPane.setAlignment(percentageIcon, Pos.TOP_LEFT);
        BorderPane.setMargin(percentageIcon, new Insets(20, 0, 0, 20));

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

    private boolean validateInputs(String email, String phone, String password) {
        System.out.println("[RegisterScene] Validating registration inputs");

        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            System.out.println("[RegisterScene] Invalid email format");
            showAlert("Invalid Email", "Please enter a valid email address");
            return false;
        }
        if (phone == null || phone.trim().isEmpty() || !phone.matches("\\d+")) {
            System.out.println("[RegisterScene] Invalid phone number format");
            showAlert("Invalid Phone", "Please enter a valid phone number");
            return false;
        }
        if (password == null || password.length() < 6) {
            System.out.println("[RegisterScene] Password too short");
            showAlert("Invalid Password", "Password must be at least 6 characters long");
            return false;
        }

        //DEEMA-----------------------------------------------------------------
        try {
            System.out.println("[RegisterScene] Creating new customer record");
            var session = HibernateUtil.getSessionFactory().openSession();
            var tx = session.beginTransaction();

            try {
                //stores customer attirbutes from text fields into the database
                Customer newCustomer = new Customer(password, "New Customer", email, phone, "");
                session.save(newCustomer);
                tx.commit();
                System.out.println("[RegisterScene] Successfully created customer record");
                return true;
            } catch (Exception e) {
                tx.rollback();
                System.err.println("[RegisterScene] Database error during customer creation: " + e.getMessage());
                e.printStackTrace();
                showAlert("Registration Failed", "Could not create account. Please try again.");
                return false;
            } finally {
                session.close();
            }
        } catch (Exception e) {
            System.err.println("[RegisterScene] Critical database error: " + e.getMessage());
            e.printStackTrace();
            showAlert("System Error", "Could not connect to database. Please try again later.");
            return false;
        }
    }
    //---------------------------------------------------------------------------

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
