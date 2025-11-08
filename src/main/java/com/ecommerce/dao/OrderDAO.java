package com.ecommerce.dao;

import com.ecommerce.model.Order;
import com.ecommerce.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class OrderDAO {

    // Place an order
    public void placeOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Get all orders of a user
    public List<Order> getOrdersByUser(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Order WHERE user.userId=:uid", Order.class)
                .setParameter("uid", userId)
                .list();
        }
    }
}
