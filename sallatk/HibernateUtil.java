/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sallatk;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static boolean initialized = false;

    static {
        try {
            System.out.println("[HibernateUtil] Initializing Hibernate...");

            // loads configuration and mappings
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            System.out.println("[HibernateUtil] Loaded hibernate.cfg.xml");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            System.out.println("[HibernateUtil] Built service registry");

            // Add entity mappings
            configuration.addAnnotatedClass(Customer.class);
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(Order.class);
            System.out.println("[HibernateUtil] Added entity mappings");

            // builds a session factory from the service registry
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("[HibernateUtil] Successfully built session factory");
            initialized = true;

        } catch (HibernateException ex) {
            System.err.println("[HibernateUtil] Initial SessionFactory creation failed: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        } catch (Exception ex) {
            System.err.println("[HibernateUtil] Unexpected error during initialization: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (!initialized) {
            System.err.println("[HibernateUtil] Warning: Attempting to get session factory before initialization");
        }
        return sessionFactory;
    }
}
