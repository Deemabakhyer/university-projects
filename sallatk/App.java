package com.mycompany.sallatk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Main application class that manages the flow between scenes.
 *
 * Scene Flow:
 * 1. Splash Screen (Welcome animation)
 * 2. Account Question (Yes/No radio buttons)
 * 3. Login or Register form
 * 4. Registration Complete
 * 5. Products Categories
 * 6. Individual Product Lists
 * 7. Cart
 * 8. Payment
 * 9. Thank You
 */
public class App extends Application {
  private Stage primaryStage;
  
  //DEEMA--------------------------------------------------------------------------
  private static boolean shouldReinitDb = false;
  private static Customer loggedInCustomer; // Store logged in customer
  // Static getter/setter for logged in customer
  public static void setLoggedInCustomer(Customer customer) {
    loggedInCustomer = customer;
  }

  public static Customer getLoggedInCustomer() {
    return loggedInCustomer;
  }
//---------------------------------------------------------------------------------
  // Scene controllers
  private SplashScreen splashScreen;
  private AccountQuestionScene accountQuestionScene;
  private LoginScene loginScene;
  private RegisterScene registerScene;
  private RegistrationCompleteScene registrationCompleteScene;
  private ProductsScene productsScene;
  private static CartScene cartScene;
  
  public static CartScene getCartSceneInstance() {
      return cartScene;
  }
  private PaymentScene paymentScene;
  private ThankYouScene thankYouScene;

  @Override
  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;
    primaryStage.setTitle("Sallatk");

    //DEEMA-----------------------------------------------------------------------
    try {
      // Initialize database connection first
      System.out.println("[App] Initializing database connection...");
      var sessionFactory = HibernateUtil.getSessionFactory();

      // Test database connection
      try (var session = sessionFactory.openSession()) {
        System.out.println("[App] Testing database connection...");
        var tx = session.beginTransaction();
        var query = session.createQuery("FROM Customer");
        var customers = query.list();
        System.out.println("[App] Found " + customers.size() +
                           " customers in database");
        tx.commit();
        System.out.println("[App] Database connection test successful");

        if (shouldReinitDb) {
          // Clear existing products
          System.out.println("[App] Clearing existing products...");
          tx = session.beginTransaction();
          session.createQuery("DELETE FROM Product").executeUpdate();
          tx.commit();
          System.out.println("[App] Products cleared successfully");
        }
        // Initialize product data
        System.out.println("[App] Initializing product data...");
        DataInitializer.initializeProducts();
        System.out.println("[App] Product data initialization complete");
      } catch (Exception e) {
        System.err.println("[App] Database operation failed:");
        e.printStackTrace();
        throw e;
      }
      //DEEMA----------------------------------------------------------------------

      // Set Samsung 8 dimensions
      primaryStage.setWidth(360);
      primaryStage.setHeight(740);
      primaryStage.setResizable(false);
      primaryStage.centerOnScreen();

      // Initialize all scenes
      initializeScenes();

      // Show splash screen first
      splashScreen.show();

      primaryStage.show();

    } catch (Exception e) {
      System.err.println("[App] Critical error during application startup:");
      e.printStackTrace();
      // Show error dialog to user
      javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
          javafx.scene.control.Alert.AlertType.ERROR,
          "Failed to start application: " + e.getMessage());
      alert.showAndWait();
      System.exit(1);
    }
  }

  private void initializeScenes() {
    System.out.println("[App] Starting scene initialization...");

    try {
      // Create all scene objects
      System.out.println("[App] Creating scene controllers...");
      splashScreen = new SplashScreen(primaryStage);
      accountQuestionScene = new AccountQuestionScene(primaryStage);
      loginScene = new LoginScene(primaryStage);
      registerScene = new RegisterScene(primaryStage);
      registrationCompleteScene = new RegistrationCompleteScene(primaryStage);
      productsScene = new ProductsScene(primaryStage);
      // Create CartScene with stored instance
      cartScene = new CartScene(primaryStage);
      Scene cartSceneObj = cartScene.create();
      cartSceneObj.setUserData(cartScene); // Store CartScene instance
      
      thankYouScene = new ThankYouScene(primaryStage);
      System.out.println("[App] Successfully created all scene controllers");

      // Create all scenes with proper initialization
      System.out.println("[App] Creating scene objects...");
      
      Scene questionScene = accountQuestionScene.create();
      Scene loginSceneObj = loginScene.create();
      Scene registerSceneObj = registerScene.create();
      Scene completeSceneObj = registrationCompleteScene.create();
      Scene productsSceneObj = productsScene.create();
      
      // Create PaymentScene once and set it on CartScene
      paymentScene = new PaymentScene(primaryStage);
      Scene paymentSceneObj = paymentScene.create();
      paymentSceneObj.setUserData(paymentScene);
      System.out.println("[App] Created payment scene with controller");
      
      // Create other scenes
      Scene thankYouSceneObj = thankYouScene.create();
      System.out.println("[App] Successfully created all scene objects");
      
      // Set payment scene on cart scene
      cartScene.setPaymentScene(paymentSceneObj);

      // Set up scene transitions and store controller references
      System.out.println("[App] Setting up scene transitions...");
      splashScreen.setNextScene(questionScene);
      System.out.println("[App] Connected: Splash -> Account Question");

      accountQuestionScene.setLoginScene(loginSceneObj);
      accountQuestionScene.setRegisterScene(registerSceneObj);
      System.out.println("[App] Connected: Account Question -> Login/Register");

      loginScene.setNextScene(completeSceneObj);
      registerScene.setNextScene(completeSceneObj);
      System.out.println(
          "[App] Connected: Login/Register -> Registration Complete");

      registrationCompleteScene.setNextScene(productsSceneObj);
      System.out.println("[App] Connected: Registration Complete -> Products");

      // Set up cart and payment transitions with detailed logging
      System.out.println("[App] Setting up cart and payment connections");
      
      // Connect cart to its scenes
      cartScene.setPreviousScene(productsSceneObj);
      cartScene.setPaymentScene(paymentSceneObj);
      System.out.println("[App] Cart scene connections:");
      System.out.println("  - Previous scene (Products): " + (cartScene.previousScene != null));
      System.out.println("  - Payment scene: " + (cartScene.paymentScene != null));
      
      // Connect payment scene
      paymentScene.setPreviousScene(cartSceneObj);
      paymentScene.setThankYouScene(thankYouSceneObj);
      System.out.println("[App] Payment scene connections:");
      System.out.println("  - Previous scene (Cart): " + (paymentScene.previousScene != null));
      System.out.println("  - Thank You scene: " + (paymentScene.thankYouScene != null));
      
      // Complete the flow
      thankYouScene.setProductsScene(productsSceneObj);
      System.out.println("[App] Thank You scene connected to Products");

      System.out.println(
          "[App] Successfully completed all scene initializations");
    } catch (Exception e) {
      System.err.println("[App] Critical error during scene initialization: " +
                         e.getMessage());
      e.printStackTrace();
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    for (String arg : args) {
      // إعادة إنشاء قاعدة البيانات
      if (arg.equals("--reinit-db")) {
        shouldReinitDb = true;
        System.out.println("[App] Database reinitialization requested");
        break;
      }
    }
    launch(args);
  }
}