package com.mycompany.sallatk;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javafx.geometry.Insets;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
// import Textfield
import javafx.scene.control.TextField;

public class CartScene {

    private Stage stage;
    public Scene previousScene;
    public Scene paymentScene;
    private VBox cartItems;
    private Label totalLabel;
    private List<Order> items = new ArrayList<>();
    private double totalPrice = 0.0;
    private String lastCategory; // Store the last visited category
    // Getter for items collection

    public List<Order> getItems() {
        return items;
    }

    public CartScene(Stage stage) {
        this.stage = stage;
        loadCartFromDatabase(); // Load cart on construction
    }

    private Scene scene;

    public Scene create() {
        // Main layout with header at top and checkout at bottom
        BorderPane mainLayout = new BorderPane();
        mainLayout.setBackground(new Background(new BackgroundFill(Color.web("#F5D8DA"), CornerRadii.EMPTY, Insets.EMPTY)));
        mainLayout.setPadding(new Insets(15));

        // Header with back button and title
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        Button backButton = Theme.createBackButton();
        //---------------------------------------------------
        backButton.setOnAction(e -> {
            if (previousScene != null) {
                stage.setScene(previousScene);
            } else if (lastCategory != null) {
                // Create new category scene if previous scene not set
                CategoryScene categoryScene = new CategoryScene(stage, lastCategory);
                Scene scene = categoryScene.create();
                categoryScene.setPreviousScene(stage.getScene());
                stage.setScene(scene);
            }
        });
        Label titleLabel = Theme.createTitleLabel("Shopping Cart");
        header.getChildren().addAll(backButton, titleLabel);
        mainLayout.setTop(header);

        // Scrollable cart items list
        cartItems = new VBox(10);
        cartItems.setPadding(new Insets(10, 0, 10, 0));

        ScrollPane scrollPane = new ScrollPane(cartItems);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        // Total price and checkout section with theme styling
        HBox checkoutSection = createCheckoutSection();

        // Add components to main layout
        mainLayout.setCenter(scrollPane);
        mainLayout.setBottom(checkoutSection);

        scene = new Scene(mainLayout, 265, 440);
        scene.setUserData(this); // Store CartScene reference for access

        return scene;
    }

    public Scene getScene() {
        return scene;
    }

    // Add a method to set the last visited category
    public void setLastCategory(String category) {
        this.lastCategory = category;
    }
//DEEMA-------------------------------------------------------------------------

    private void updateOrder(Order order) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(order);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error updating order: " + e.getMessage());
        } finally {
            session.close();
        }
    }
//------------------------------------------------------------------------------

    private HBox createCheckoutSection() {
        // Create checkout section with theme styling
        HBox section = Theme.createProductCard();
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(15));

        VBox checkoutContent = new VBox(10);
        checkoutContent.setAlignment(Pos.CENTER);
        checkoutContent.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(checkoutContent, Priority.ALWAYS);

        // Create total label with consistent styling
        totalLabel = new Label("Total: $0.00");
        totalLabel.setStyle("-fx-font-family: 'EB Garamond'; -fx-font-weight: bold; -fx-font-size: 18px;");

        // Create checkout button with theme styling
        Button checkoutButton = Theme.createAddToCartButton();
        checkoutButton.setText("Proceed to Checkout");
        checkoutButton.setMaxWidth(Double.MAX_VALUE);

        // Handle checkout action
        checkoutButton.setOnAction(e -> {

            stage.setScene(paymentScene);

        });

        // Create divider for visual separation
        Region divider = new Region();
        divider.setStyle("-fx-background-color: #E0E0E0;");
        divider.setPrefHeight(1);
        divider.setMaxWidth(Double.MAX_VALUE);

        checkoutContent.getChildren().addAll(divider, totalLabel, checkoutButton);
        section.getChildren().add(checkoutContent);
        return section;
    }

//DEEMA ------------------------------------------------------------------------
    public void addItem(Product product, int quantity) {
        Customer loggedInCustomer = App.getLoggedInCustomer();
        if (loggedInCustomer == null) {
            System.err.println("No customer logged in");
            return;
        }

        if (quantity <= 0) {
            return; // Don't add items with zero or negative quantity
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Check if item already exists in cart for this customer
            Order existingOrder = session.createQuery(
                    "FROM Order WHERE customer = :customer AND product = :product AND status = 'PENDING'", Order.class)
                    .setParameter("customer", loggedInCustomer)
                    .setParameter("product", product)
                    .uniqueResult();

            if (existingOrder != null) {
                // Update existing order
                // Allows updating the quantity instead of creating a new entry every time.
                existingOrder.setNumberOfItems(existingOrder.getNumberOfItems() + quantity);
                existingOrder.setTotal(existingOrder.getTotal() + (product.getPrice() * quantity));
                session.update(existingOrder);
            } else {
                // Create new order
                Order order = new Order(loggedInCustomer, quantity, product.getPrice() * quantity);
                order.setProduct(product);
                session.save(order);
                items.add(order);
            }

            tx.commit();
            updateTotal();
            loadCartFromDatabase(); // Refresh cart from database
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error adding item to cart: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private void loadCartFromDatabase() {
        Customer loggedInCustomer = App.getLoggedInCustomer();
        if (loggedInCustomer == null) {
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Load items and initialize products
            items = session.createQuery(
                    "FROM Order o LEFT JOIN FETCH o.product WHERE o.customer = :customer AND o.status = 'PENDING'", Order.class)
                    .setParameter("customer", loggedInCustomer)
                    .list();
            System.out.println("Cart loaded - Found " + items.size() + " items for customer: " + loggedInCustomer.getPhoneNumber());
//--------------------------------------------------------------------------------------

            // Refresh cart display
            cartItems.getChildren().clear();
            for (Order order : items) {
                Product product = order.getProduct();
                if (product != null) {
                    HBox itemCard = Theme.createProductCard();

                    // Left: Product info
                    VBox productInfo = new VBox(5);
                    Label nameLabel = new Label(product.getProductName());
                    nameLabel.setFont(Font.font("EB Garamond", FontWeight.BOLD, 16));
                    nameLabel.setStyle("-fx-text-fill: #000000;");
                    Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));
                    priceLabel.setStyle("-fx-text-fill: #4CAF50;");
                    productInfo.getChildren().addAll(nameLabel, priceLabel);
                    HBox.setHgrow(productInfo, Priority.ALWAYS);

                    // Right: Quantity controls
                    VBox controls = new VBox(5);
                    controls.setAlignment(Pos.CENTER_RIGHT);

                    // Quantity controls
                    HBox quantityControls = new HBox(5);
                    quantityControls.setAlignment(Pos.CENTER);

                    TextField quantityField = new TextField(String.valueOf(order.getNumberOfItems()));
                    quantityField.setEditable(false);
                    quantityField.setPrefWidth(40);
                    quantityField.setAlignment(Pos.CENTER);
                    quantityField.setStyle("-fx-background-color: white; -fx-border-color: #ddd;");

                    Button decreaseBtn = Theme.createQuantityButton("-");
                    Button increaseBtn = Theme.createQuantityButton("+");
//---------------------------------------------------------------------------------------
                    decreaseBtn.setOnAction(e -> {
                        int currentQty = Integer.parseInt(quantityField.getText());
                        if (currentQty > 1) {
                            order.setNumberOfItems(currentQty - 1);
                            order.setTotal(product.getPrice() * (currentQty - 1));
                            updateOrder(order);
                            quantityField.setText(String.valueOf(currentQty - 1));
                            updateTotal();
                        }
                    });
//-------------------------------------------------------------
                    increaseBtn.setOnAction(e -> {
                        int currentQty = Integer.parseInt(quantityField.getText());
                        if (currentQty < product.getStockQuantity()) {
                            order.setNumberOfItems(currentQty + 1);
                            order.setTotal(product.getPrice() * (currentQty + 1));
                            updateOrder(order);
                            quantityField.setText(String.valueOf(currentQty + 1));
                            updateTotal();
                        }
                    });

                    quantityControls.getChildren().addAll(decreaseBtn, quantityField, increaseBtn);

                    // Total for this item
                    Label itemTotal = new Label(String.format("Total: $%.2f", order.getTotal()));
                    itemTotal.setStyle("-fx-text-fill: #4CAF50; -fx-font-size: 14px;");

                    controls.getChildren().addAll(quantityControls, itemTotal);
                    itemCard.getChildren().addAll(productInfo, controls);
                    cartItems.getChildren().add(itemCard);
                }
            }

            updateTotal();
        } catch (Exception e) {
            System.err.println("Error loading cart: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    private void updateTotal() {
        totalPrice = items.stream().mapToDouble(item -> item.getTotal()).sum();
        totalLabel.setText(String.format("Total: $%.2f", totalPrice));
    }

    public void setPreviousScene(Scene scene) {
        this.previousScene = scene;
    }

    public void setPaymentScene(Scene scene) {
        System.out.println("[CartScene] Setting payment scene");
        this.paymentScene = scene;

        // Ensure PaymentScene controller is set as userData
        if (scene != null && !(scene.getUserData() instanceof PaymentScene)) {
            System.out.println("[CartScene] Creating new PaymentScene controller");
            PaymentScene controller = new PaymentScene(stage);
            scene.setUserData(controller);
            // Set basic scene properties
            controller.setPreviousScene(this.create());
        }
        System.out.println("[CartScene] Payment scene configured with controller: "
                + (scene != null && scene.getUserData() instanceof PaymentScene ? "yes" : "no"));
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void clearCart() {
        items.clear();
        updateTotal();
        loadCartFromDatabase();
    }
//DEEMA--------------------------------------------------------------------------------------

    public void deleteCartItems() {
        System.out.println("[CartScene] Deleting cart items...");
        Customer loggedInCustomer = App.getLoggedInCustomer();
        if (loggedInCustomer == null) {
            System.err.println("No customer logged in");
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // Retrieve all pending orders for the logged-in customer
            List<Order> pendingOrders = session.createQuery(
                    "FROM Order WHERE customer = :customer AND status = 'PENDING'", Order.class)
                    .setParameter("customer", loggedInCustomer)
                    .list();

            // Delete each pending order
            for (Order order : pendingOrders) {
                session.delete(order);
            }

            tx.commit();
            items.clear(); // Clear the local items list
            updateTotal(); // Update the total price
            loadCartFromDatabase(); // Refresh the cart from the database
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            System.err.println("Error deleting cart items: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
