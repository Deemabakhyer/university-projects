package com.mycompany.sallatk;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

public class CategoryScene {
    protected Stage stage;
    protected Scene previousScene; // For back button
    protected Scene cartScene; // For checkout button
    protected String categoryName;
    private final ProductService productService;
    private List<Product> products;

    public CategoryScene(Stage stage, String categoryName) {
        this.stage = stage;
        this.categoryName = categoryName;
        this.productService = new ProductService();
        this.products = productService.getProductsByCategory(categoryName);
        
        // Use the existing CartScene instance from App
        CartScene cartSceneHandler = App.getCartSceneInstance();
        setCartScene(cartSceneHandler.getScene());
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setCartScene(Scene scene) {
        this.cartScene = scene;
    }

    public Scene create() {
        VBox mainLayout = new VBox(15);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setBackground(
                new Background(new BackgroundFill(Color.web("#f5d9d8"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setPadding(new Insets(10));

        // Header with category name and back button
        HBox header = createHeader();

        // Scrollable product list
        ScrollPane scrollPane = new ScrollPane(createProductList());
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Checkout button at bottom
        Button checkoutButton = createCheckoutButton();

        mainLayout.getChildren().addAll(header, scrollPane, checkoutButton);

        return new Scene(mainLayout, 265, 440);
    }

    private HBox createHeader() {
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);

        Button backButton = new Button("←");
        backButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-font-size: 20px;" +
                        "-fx-padding: 5;");
        backButton.setOnAction(e -> {
            ProductsScene productsScene = new ProductsScene(stage);
            Scene productsSceneContent = productsScene.create();
            stage.setScene(productsSceneContent);
        });

        Label titleLabel = new Label(categoryName);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        titleLabel.setAlignment(Pos.CENTER);

        header.getChildren().addAll(backButton, titleLabel);
        return header;
    }

    private Button createCheckoutButton() {
        Button checkoutButton = new Button("Checkout");
        checkoutButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 5;");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);
        checkoutButton.setOnAction(e -> {
            if (cartScene != null) {
                stage.setScene(cartScene);
            }
        });
        return checkoutButton;
    }

    private VBox createProductList() {
        // Use Theme's main layout for consistent styling
        VBox mainLayout = Theme.createMainLayout();
        mainLayout.setPadding(new Insets(15));
        
        // Declare addToCartButton at class level
        Button addToCartButton;

        if (products != null) {
            for (Product product : products) {
                // Create product card with consistent styling
                HBox productCard = Theme.createProductCard();

                // Create quantity controls section
                VBox controlsBox = new VBox(10);
                controlsBox.setAlignment(Pos.CENTER_RIGHT);
                controlsBox.setPadding(new Insets(0, 0, 0, 10));

                // Initialize quantity controls
                HBox quantityControls = new HBox(5);
                quantityControls.setAlignment(Pos.CENTER);

                TextField quantityField = new TextField("0");
                quantityField.setEditable(false);
                quantityField.setPrefWidth(40);
                quantityField.setAlignment(Pos.CENTER);
                quantityField.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 3;");

                Button decreaseButton = Theme.createQuantityButton("-");
                Button increaseButton = Theme.createQuantityButton("+");

                decreaseButton.setOnAction(e -> {
                    try {
                        int value = Integer.parseInt(quantityField.getText());
                        if (value > 0) {
                            quantityField.setText(String.valueOf(value - 1));
                        }
                    } catch (NumberFormatException ex) {
                        quantityField.setText("0");
                    }
                });

                increaseButton.setOnAction(e -> {
                    try {
                        int value = Integer.parseInt(quantityField.getText());
                        if (value < product.getStockQuantity()) {
                            quantityField.setText(String.valueOf(value + 1));
                        }
                    } catch (NumberFormatException ex) {
                        quantityField.setText("0");
                    }
                });

                quantityControls.getChildren().addAll(decreaseButton, quantityField, increaseButton);

                // 1. Left section - Product image (if available)
                try {
                    VBox imageContainer = new VBox();
                    imageContainer.setAlignment(Pos.CENTER);
                    ImageView productImage = new ImageView(new Image(product.getProductDescription()));
                    productImage.setFitWidth(30);
                    productImage.setFitHeight(30);
                    productImage.setPreserveRatio(true);
                    imageContainer.setPrefWidth(40);
                    imageContainer.getChildren().add(productImage);
                    productCard.getChildren().add(imageContainer);
                } catch (Exception e) {
                    // Skip adding image container if load fails
                    System.err.println("Failed to load image for " + product.getProductName());
                }

                // 2. Middle section - Title and price
                VBox productInfo = new VBox(5);
                productInfo.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(productInfo, Priority.ALWAYS);
                productInfo.setPadding(new Insets(5, 10, 5, 10));
                productInfo.setMinWidth(120);
                
                Label nameLabel = new Label(product.getProductName());
                nameLabel.setFont(Font.font("EB Garamond", FontWeight.BOLD, 16));
                nameLabel.setStyle("-fx-text-fill: #000000;");
                nameLabel.setWrapText(true);
                
                Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));
                priceLabel.setFont(Font.font("Arial", 14));
                priceLabel.setStyle("-fx-text-fill: #4CAF50;");
                productInfo.getChildren().addAll(nameLabel, priceLabel);
                productCard.getChildren().add(productInfo);

                // Add quantity controls and Add to Cart button
                addToCartButton = Theme.createAddToCartButton();
                addToCartButton.setOnAction(e -> {
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        if (quantity > 0 && quantity <= product.getStockQuantity()) {
                            if (cartScene != null) {
                                CartScene cartSceneHandler = (CartScene) cartScene.getUserData();
                                cartSceneHandler.addItem(product, quantity);
                                cartSceneHandler.setLastCategory(this.categoryName);
                                quantityField.setText("0");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        quantityField.setText("0");
                    }
                });

                // Add to product card
                controlsBox.getChildren().addAll(quantityControls, addToCartButton);
                productCard.getChildren().add(controlsBox);

                mainLayout.getChildren().add(productCard);
            }
        }

        return mainLayout;
    }

    // Method to refresh products (useful when cart updates)
    public void refreshProducts() {
        this.products = productService.getProductsByCategory(categoryName);
    }

    // Utility method for creating product item layout
    protected HBox createProductItem(String name, double price, int quantity) {
        HBox item = new HBox(10);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 5;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        VBox productInfo = new VBox(5);
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        Label priceLabel = new Label(String.format("$%.2f", price));
        productInfo.getChildren().addAll(nameLabel, priceLabel);
        HBox.setHgrow(productInfo, Priority.ALWAYS);

        // Quantity controls
        HBox quantityControls = new HBox(5);
        quantityControls.setAlignment(Pos.CENTER_RIGHT);

        Button decreaseButton = new Button("-");
        Label quantityLabel = new Label(String.valueOf(quantity));
        Button increaseButton = new Button("+");

        quantityControls.getChildren().addAll(
                decreaseButton, quantityLabel, increaseButton);

        item.getChildren().addAll(productInfo, quantityControls);
        return item;
    }
}
