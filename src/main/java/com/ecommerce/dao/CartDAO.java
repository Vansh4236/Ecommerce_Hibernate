package com.ecommerce.dao;

import com.ecommerce.model.CartItem;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CartDAO {

    // Add item to cart
    public void addCartItem(CartItem item) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Remove item from cart
    public void removeCartItem(int cartId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            CartItem item = session.get(CartItem.class, cartId);
            if (item != null) session.delete(item);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get all items of a user
    public List<CartItem> getCartItemsByUser(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CartItem> query = session.createQuery(
                "FROM CartItem WHERE user.userId=:uid", CartItem.class);
            query.setParameter("uid", userId);
            return query.list();
        }
    }
}
