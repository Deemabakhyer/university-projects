package com.mycompany.sallatk;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProductService {
    
    public List<Product> getProductsByCategory(String category) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Product> products = null;
        try {
            String queryStr = "FROM Product WHERE category = :category";
            Query query = session.createQuery(queryStr);
            query.setParameter("category", category);
            products = query.list();
        } finally {
            session.close();
        }
        return products;
    }

    public void saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(product);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void updateProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(product);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Product getProductById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (Product) session.get(Product.class, id);
        } finally {
            session.close();
        }
    }

    public void updateStock(int productId, int newQuantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Product product = (Product) session.get(Product.class, productId);
            if (product != null) {
                product.setStockQuantity(newQuantity);
                session.update(product);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}