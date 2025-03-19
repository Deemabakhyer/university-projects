package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PaymentScene {
    private Stage stage;
    public Scene previousScene;
    public Scene thankYouScene;
    private double totalAmount;

    public PaymentScene(Stage stage) {
        this.stage = stage;
    }

    public Scene create() {
        VBox layout = createVBox(20);
        Label title = createLabel("Payment", "24px", true);

        Button cashCardButton = createPaymentButton("Cash & Card", "https://cdn.kibrispdr.org/data/736/money-clipart-png-13.png");
        Button creditCardButton = createPaymentButton("Credit Card", "https://www.al-madina.com/uploads/imported_images/85/51/855191.jpeg");
        Button applePayButton = createPaymentButton("Apple Pay", "https://1000logos.net/wp-content/uploads/2023/03/Apple-Pay-logo.png");

        creditCardButton.setOnAction(e -> showCreditCardScreen());

        // For cashCardButton and applePayButton, go directly to thank you scene and clear the cart
        cashCardButton.setOnAction(e -> {
            App.getCartSceneInstance().deleteCartItems();
            if (thankYouScene != null) {
                stage.setScene(thankYouScene);
            }
        });

        applePayButton.setOnAction(e -> {
            App.getCartSceneInstance().deleteCartItems();
            if (thankYouScene != null) {
                stage.setScene(thankYouScene);
            }
        });

        // For cashCardButton and applePayButton, go directly to thank you scene and clear the cart
        cashCardButton.setOnAction(e -> {
            App.getCartSceneInstance().deleteCartItems();
            if (thankYouScene != null) {
                stage.setScene(thankYouScene);
            }
        });

        applePayButton.setOnAction(e -> {
            App.getCartSceneInstance().deleteCartItems();
            if (thankYouScene != null) {
                stage.setScene(thankYouScene);
            }
        });

        VBox paymentMethods = new VBox(20, cashCardButton, creditCardButton, applePayButton);

        layout.getChildren().addAll(title, paymentMethods, createBottomImages());

        return new Scene(layout, 267, 400);
    }

    private VBox createVBox(int spacing) {
        VBox box = new VBox(spacing);
        box.setStyle("-fx-background-color: #f5d9d8; -fx-padding: 20;");
        return box;
    }

    private Label createLabel(String text, String fontSize, boolean bold) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: " + (bold ? "bold" : "normal") + ";");
        return label;
    }

    private Button createPaymentButton(String text, String imageUrl) {
        Button button = new Button(text);
        ImageView imageView = createImageView(imageUrl, 50);
        button.setGraphic(imageView);
        return button;
    }

    private ImageView createImageView(String url, int height) {
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private HBox createLabeledField(String labelText, TextField field, Label errorLabel) {
        VBox fieldBox = new VBox(5, field, errorLabel);
        return new HBox(10, new Label(labelText), fieldBox);
    }

    private Label createErrorLabel() {
        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        return errorLabel;
    }

    private ComboBox<String> createComboBox(int start, int end) {
        ComboBox<String> comboBox = new ComboBox<>();
        for (int i = start; i <= end; i++) {
            comboBox.getItems().add(String.valueOf(i));
        }
        return comboBox;
    }

    private GridPane createBottomImages() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        ImageView groceryImage = createImageView("https://i.pinimg.com/736x/8c/95/b6/8c95b65b290fe5163db53662260f7525.jpg", 100);
        ImageView cartImage = createImageView("https://i.pinimg.com/736x/ff/63/4f/ff634fea86a41aea0d8988767a5a92a1.jpg", 100);
        gridPane.add(groceryImage, 0, 0);
        gridPane.add(cartImage, 1, 0);
        return gridPane;
    }

    private void showCreditCardScreen() {
        VBox layout = createVBox(20);
        Label title = createLabel("Pay with Credit Card", "24px", true);
        ImageView cardImage = createImageView("https://www.bankygate.com/UserFiles/News/2019/01/13/222.jpg?190113132300", 100);
        HBox titleBox = new HBox(10, title, cardImage);

        TextField nameField = new TextField();
        Label nameError = createErrorLabel();

        TextField cardNumberField = new TextField();
        Label cardNumberError = createErrorLabel();

        TextField cvvField = new TextField();
        Label cvvError = createErrorLabel();

        ComboBox<String> monthField = createComboBox(1, 12);
        ComboBox<String> yearField = createComboBox(25, 35);
        VBox expiryBox = new VBox(10, new Label("Expiry Date"), new HBox(10, monthField, yearField));

        Button payButton = new Button("Proceed");
        payButton.setOnAction(e -> validatePayment(nameField, cardNumberField, cvvField, nameError, cardNumberError, cvvError));

        layout.getChildren().addAll(
            titleBox,
            createLabeledField("Cardholder Name", nameField, nameError),
            createLabeledField("Card Number", cardNumberField, cardNumberError),
            expiryBox,
            createLabeledField("CVV", cvvField, cvvError),
            payButton,
            createBottomImages()
        );

        stage.setScene(new Scene(layout, 267, 400));
    }

    private void validatePayment(TextField name, TextField cardNumber, TextField cvv,
                                  Label nameError, Label cardNumberError, Label cvvError) {
        boolean valid = true;
        nameError.setText("");
        cardNumberError.setText("");
        cvvError.setText("");

        if (name.getText().isEmpty()) {
            nameError.setText("Please enter the cardholder name.");
            valid = false;
        } else if (!name.getText().matches("[a-zA-Z ]+")) {
            nameError.setText("Cardholder name must contain letters only.");
            valid = false;
        }

        if (cardNumber.getText().isEmpty()) {
            cardNumberError.setText("Please enter the card number.");
            valid = false;
        } else if (!cardNumber.getText().matches("\\d{16}")) {
            cardNumberError.setText("Card Number must be 16 digits (numbers only).");
            valid = false;
        }

        if (cvv.getText().isEmpty()) {
            cvvError.setText("Please enter the CVV.");
            valid = false;
        } else if (!cvv.getText().matches("\\d{3}")) {
            cvvError.setText("CVV must be exactly 3 digits (numbers only).");
            valid = false;
        }

        if (valid) {
            System.out.println("Payment successful!");
            App.getCartSceneInstance().deleteCartItems();
            if (thankYouScene != null) {
                stage.setScene(thankYouScene);
            } else {
                System.out.println("Thank You scene is not set!");
            }
        }
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setThankYouScene(Scene scene) {
        this.thankYouScene = scene;
    }

    public void setTotalAmount(double amount) {
        this.totalAmount = amount;
    }
}
