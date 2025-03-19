package com.mycompany.sallatk;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.HashMap;
import java.util.Map;

public class DataInitializer {
    private static final int UNLIMITED_STOCK = 9999;

    private static final Map<String, String[]> CATEGORY_PRODUCTS = new HashMap<>();
    static {
        // Each product: name, image URL, price
        CATEGORY_PRODUCTS.put("Vegetables", new String[] {
            "Carrot|https://images.unsplash.com/photo-1447175008436-054170c2e979?w=400|2.50",
            "Broccoli|https://images.unsplash.com/photo-1459411621453-7b03977f4bfc?w=400|3.50",
            "Cucumber|https://images.unsplash.com/photo-1604977042946-1eecc30f269e?w=400|1.75",
            "Potato|https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=400|2.00",
            "Tomato|https://images.unsplash.com/photo-1518977676601-b53f82aba655?w=400|1.50",
            "Lettuce|https://images.unsplash.com/photo-1622206151226-18ca2c9ab4a1?w=400|2.25"
        });

        CATEGORY_PRODUCTS.put("Fruits", new String[] {
            "Apple|https://images.unsplash.com/photo-1579613832125-5d34a13ffe2a?w=400|1.50",
            "Orange|https://images.unsplash.com/photo-1582979512210-99b6a53386f9?w=400|2.00",
            "Banana|https://images.unsplash.com/photo-1571771894821-ce9b6c11b08e?w=400|4.00",
            "Watermelon|https://images.unsplash.com/photo-1563114773-84221bd62daa?w=400|15.00",
            "Grapes|https://images.unsplash.com/photo-1537640538966-79f369143f8f?w=400|5.00",
            "Strawberry|https://images.unsplash.com/photo-1518635017480-d9a4666cdc24?w=400|3.50"
        });

        CATEGORY_PRODUCTS.put("Drinks", new String[] {
            "Cola|https://images.unsplash.com/photo-1544245571-53939b33f17f?w=400|2.50",
            "Coffee|https://images.unsplash.com/photo-1541167760496-1628856ab772?w=400|3.00",
            "Tea|https://images.unsplash.com/photo-1563822249366-3efb23b8e0c9?w=400|2.00",
            "Juice|https://images.unsplash.com/photo-1600271886742-f049cd451bba?w=400|2.75",
            "Water|https://images.unsplash.com/photo-1560493676-04071c5f467b?w=400|1.00"
        });

        CATEGORY_PRODUCTS.put("Chips", new String[] {
            "Chips|https://images.unsplash.com/photo-1566478989037-eec170784d0b?w=400|1.50",
            "Tortilla|https://images.unsplash.com/photo-1621447504864-d8686e12698c?w=400|1.75",
            "Pretzels|https://images.unsplash.com/photo-1558961363-fa8fdf82db35?w=400|2.00",
            "Popcorn|https://images.unsplash.com/photo-1505686994434-e3cc5abf1330?w=400|2.50"
        });

        CATEGORY_PRODUCTS.put("Chocolates", new String[] {
            "Dark|https://images.unsplash.com/photo-1549007994-cb92caebd54b?w=400|2.50",
            "Cookies|https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=400|1.50",
            "Candy|https://images.unsplash.com/photo-1581798459219-318e76aecc7b?w=400|1.00",
            "Lollipop|https://images.unsplash.com/photo-1575224300306-1b8da36134ec?w=400|0.75",
            "Ice Cream|https://images.unsplash.com/photo-1560008581-09826d1de69e?w=400|3.00"
        });
    }

    public static void initializeProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            // Clear orders first, then clear existing products
            // why? The program is reinitializing the product database.
            //It ensures that old product data is removed before adding new products.
            //Prevents duplicate products if the initialization runs multiple times.
            session.createQuery("DELETE FROM Order").executeUpdate();
            session.createQuery("DELETE FROM Product").executeUpdate();

            // Add products for each category
            for (Map.Entry<String, String[]> entry : CATEGORY_PRODUCTS.entrySet()) {//loops each category
                String category = entry.getKey(); //fetches the category name
                String[] products = entry.getValue(); //retrieves the list of products belonging to that category.

                //This loops through all products within the current category.
                for (String productInfo : products) {//loops products in one category
                    String[] parts = productInfo.split("\\|");
                    String name = parts[0];
                    String imageUrl = parts[1];
                    double price = Double.parseDouble(parts[2]);

                    Product product = new Product(
                        name,
                        "Fresh " + name, // Simple description
                        price,
                        UNLIMITED_STOCK,
                        category
                    );
                    session.save(product);
                }
            }

            tx.commit();
            System.out.println("Successfully initialized products database");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error initializing products: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
