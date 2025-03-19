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
import javafx.stage.Stage;

public class ProductsScene {
    private Stage stage;
    private Scene[] categoryScenes; // Array to hold scenes for each category

    // Category icons - using transparent PNG URLs
    private final String[] CATEGORY_ICONS = {
        "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f966.png", // broccoli
        "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f34e.png", // apple
        "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f379.png", // drink
        "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f35f.png", // fries
        "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f36b.png"  // chocolate
    };

    public ProductsScene(Stage stage) {
        this.stage = stage;
        this.categoryScenes = new Scene[5]; // One for each category
    }

    public Scene create() {
        // Use Theme's main layout for consistent styling
        VBox mainLayout = Theme.createMainLayout();

        // Title
        Label titleLabel = Theme.createTitleLabel("Choose Category");

        // Category buttons in vertical layout
        VBox categoryList = new VBox(15);
        categoryList.setAlignment(Pos.CENTER);
        categoryList.setPadding(new Insets(20, 0, 0, 0));

        String[] categories = {
            "Vegetables", "Fruits", "Drinks", "Chips", "Chocolates"
        };

        for (int i = 0; i < categories.length; i++) {
            Button categoryButton = createCategoryButton(categories[i], CATEGORY_ICONS[i]);
            final String category = categories[i];
            categoryButton.setOnAction(e -> {
                CategoryScene categoryScene = new CategoryScene(stage, category);
                Scene scene = categoryScene.create();
                categoryScene.setPreviousScene(stage.getScene());
                stage.setScene(scene);
            });
            categoryList.getChildren().add(categoryButton);
        }

        // Use layout from Theme
        mainLayout.getChildren().addAll(titleLabel, categoryList);

        // Add bottom images that stick to bottom
        HBox bottomContainer = Theme.createBottomImages();
        mainLayout.getChildren().add(bottomContainer);

        Scene scene = new Scene(mainLayout, 360, 740);

        // Add keyboard navigation
        scene.setOnKeyPressed(e -> {
            int index = -1;
            switch (e.getCode()) {
                case DIGIT1: index = 0; break;
                case DIGIT2: index = 1; break;
                case DIGIT3: index = 2; break;
                case DIGIT4: index = 3; break;
                case DIGIT5: index = 4; break;
                default: break;
            }
            if (index >= 0 && index < categories.length) {
                CategoryScene categoryScene = new CategoryScene(stage, categories[index]);
                Scene newScene = categoryScene.create();
                categoryScene.setPreviousScene(scene);
                stage.setScene(newScene);
            }
        });

        return scene;
    }

    private Button createCategoryButton(String text, String iconUrl) {
        HBox buttonContent = new HBox(10);
        buttonContent.setAlignment(Pos.CENTER_LEFT);

        // Icon
        try {
            ImageView icon = new ImageView(new Image(iconUrl));
            icon.setFitWidth(30);
            icon.setFitHeight(30);
            buttonContent.getChildren().add(icon);
        } catch (Exception e) {
            System.err.println("Failed to load icon for " + text + ": " + e.getMessage());
        }

        // Create a label for category name using Theme
        Label categoryLabel = Theme.createProductNameLabel(text);
        buttonContent.getChildren().add(categoryLabel);

        Button button = Theme.createButton("");  // Empty text since we're using custom content
        button.setGraphic(buttonContent);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setPadding(new Insets(0, 0, 0, 20)); // Add left padding for icon

        // Add hover effect - only change background color
        button.setOnMouseEntered(e -> {
            button.setStyle(button.getStyle() + "-fx-background-color: #E5A9B0;");
        });
        button.setOnMouseExited(e -> {
            button.setStyle(button.getStyle().replace("-fx-background-color: #E5A9B0;", "-fx-background-color: transparent;"));
        });

        return button;
    }
}
