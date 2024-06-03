/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmcy;

/**
 *
 * @author Layan
 */
 

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * represents a pharmacy product that implements the careProuductInterface
 */
class PharmacyProduct implements CareProductInterface {

    private List<Product> careProducts;
    private List<Product> MedicinProduct;

    private List<Product> cart;
/**
     * Constructs a PharmacyProduct object, initializing the lists for care products and medication products.
     */
    public PharmacyProduct() {
        careProducts = new ArrayList<>();
        cart = new ArrayList<>();
        MedicinProduct = new ArrayList<>();
        initializeData();
    }
/**
     * Adds a care product to the list of available care products.
     *
     * @param product the care product to be added.
     */
    public void addProduct(Product product) {
        careProducts.add(product);
    }
/**
     * Adds a product to the cart.
     *
     * @param product the product to be added to the cart.
     */
    public void addCART(Product product) {
        cart.add(product);
    }
    /**
     * Retrieves the list of products in the cart.
     *
     * @return the list of products in the cart.
     */
    public List<Product> getCart()
    {
        return cart;
    }
/**
     * Initiates the shopping process based on the specified type.
     *
     * @param type the type of shopping to start.
     */
    public void startShopping(int type) {

        Scanner scanner = new Scanner(System.in);
        if (type == 1) {
            while (true) {
                System.out.println("Enter category number to select a category:");
                displayCategories();
                System.out.println("A- Apply and add selected careProducts to cart");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("A")) {
                    displayCart();
                    break;
                }

                int categoryIndex = Integer.parseInt(input) - 1;
                if (categoryIndex >= 0 && categoryIndex < careProducts.size()) {
                    Product selectedProduct = careProducts.get(categoryIndex);
                    addCART(selectedProduct);
                    System.out.println("Product added to cart.");
                } else {
                    System.out.println("Invalid category number.");
                }

            }
        } else if (type == 2) {
            while (true) {
                System.out.println("Enter category number to select a category:");
                displaymedicineCategories();
                System.out.println("A- Apply and add selected careProducts to cart");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("A")) {
                    displayCart();
                    break;
                }

                int categoryIndex = Integer.parseInt(input) - 1;
                if (categoryIndex >= 0 && categoryIndex < MedicinProduct.size()) {
                    Product selectedProduct = MedicinProduct.get(categoryIndex);
                    addCART(selectedProduct);
                    System.out.println("Product added to cart.");
                } else {
                    System.out.println("Invalid category number.");
                }
            }
        }
    }
/**
     * Displays the available categories for care products.
     */
    public void displayCategories(){
        System.out.println("What type of Care product are you looking for?");
        System.out.println("1. Hair Care");
        System.out.println("2. Skin Care");
        System.out.println("3. Body Care");
        System.out.println("4. BACK");

        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt() - 1;
        if (num < 3) {
            for (int i = num * 4; i < num * 4 + 4; i++) {
                Product product = careProducts.get(i);
                System.out.println((i + 1) + "- " + product.getCategory() + " - " + product.getName());
            }
        }
    }
/**
     * Displays the available categories for medication products.
     */
    private void displaymedicineCategories() {

        System.out.println("What type of medication are you looking for?");
        System.out.println("1. Pain Reliever");
        System.out.println("2. Cough, cold & allergy medication");
        System.out.println("3. Stomach & bowel medication");
        System.out.println("4. Medication for children and infants");
        System.out.println("5. BACK");

        Scanner scanner = new Scanner(System.in);

        int num = scanner.nextInt() - 1;
        if (num<4) {

            for (int i = num * 5; i < num * 5 + 5; i++) {
                Product product = MedicinProduct.get(i);
                System.out.println((i + 1) + "- " + product.getCategory() + " - " + product.getName());
            }
        }
                
    }
/**
     * display the products in the cart.
     * @return the prices in the cart as double
     */
    public double displayCart() {
        double total = 0;
        System.out.println("Cart:");
        for (Product product : cart) {
            System.out.println(product);
            total += product.getPrice();
        }
        System.out.println("The total is: $" + total);
        return total;
    }
/**
     * Initializes the data for the pharmacy products.
     */
    private void initializeData() {
        addProduct(new HairCare("Herbal Essences Shampoo", 150, "Hair Care", "Silky"));
        addProduct(new HairCare("Sunsilk Shampoo", 200, "Hair Care", "curly"));
        addProduct(new HairCare("Dove shampoo", 150, "Hair Care", "long"));
        addProduct(new HairCare("Cantu Shampoo", 200, "Hair Care", "short"));

        addProduct(new SkinCare("Cerave Moisturiser", 100, "Skin Care", true));
        addProduct(new SkinCare("Cetaphil Moisturiser", 70, "Skin Care", true));
        addProduct(new SkinCare("Cerave Foaming cleanser", 50, "Skin Care", false));
        addProduct(new SkinCare("Cetaphil Gentle skin cleanser", 40, "Skin Care", false));

        addProduct(new BodyCare("QV cream", 100, "Body Care", "hands"));
        addProduct(new BodyCare("Bioderma Cream", 150, "Body Care", "face"));
        addProduct(new BodyCare("Eucerin Cream", 80, "Body Care", "eyes"));
        addProduct(new BodyCare("Smooth Cream", 50, "Body Care", "feet"));

        MedicinProduct.add(new PainRelief("Adol", 20, 5.05));
        MedicinProduct.add(new PainRelief("Fevadol Plus", 20, 9.65));
        MedicinProduct.add(new PainRelief("Sapofen", 20, 10));
        MedicinProduct.add(new PainRelief("Benzaflex", 30, 11.50));
        MedicinProduct.add(new PainRelief("Advil", 24, 7.15));

        MedicinProduct.add(new CoughCold("Flutab Sinus", 20, 9.25));
        MedicinProduct.add(new CoughCold("Mucosolvan LA", 10, 15.15));
        MedicinProduct.add(new CoughCold("Septolete Lemon Lozenges", 18, 33.00));
        MedicinProduct.add(new CoughCold("Contra", 30, 22.20));
        MedicinProduct.add(new CoughCold("Advil Cold And Sinus", 20, 11.75));

        MedicinProduct.add(new Stomach("Imodium", 6, 9.45));
        MedicinProduct.add(new Stomach("Bio-Intest", 30, 55.00));
        MedicinProduct.add(new Stomach("Spasmiona Softgels", 30, 49.00));
        MedicinProduct.add(new Stomach("Mebagen", 30, 34.65));
        MedicinProduct.add(new Stomach("Premosan Prevent Vomiting", 20, 3.60));

        MedicinProduct.add(new KidsMedicine("Adol", 100, 5.80));
        MedicinProduct.add(new KidsMedicine("Nurofen", 150, 15.30));
        MedicinProduct.add(new KidsMedicine("Lorinase", 100, 22.90));
        MedicinProduct.add(new KidsMedicine("Fludrex", 120, 12.00));
        MedicinProduct.add(new KidsMedicine("Otrivin Child", 10, 8.40));

    }

}
