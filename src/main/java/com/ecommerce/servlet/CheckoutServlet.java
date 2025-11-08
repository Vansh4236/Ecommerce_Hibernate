package com.ecommerce.servlet;

import com.ecommerce.dao.CartDAO;
import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class CheckoutServlet extends HttpServlet {

    private CartDAO cartDAO = new CartDAO();
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("currentUser");
        List<CartItem> cartItems = cartDAO.getCartItemsByUser(user.getUserId());

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        double totalAmount = 0;
        for (CartItem item : cartItems) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();
        }

        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setItems(cartItems);
        order.setTotalAmount(totalAmount);

        // Save order
        orderDAO.placeOrder(order);

        // Clear cart
        for (CartItem item : cartItems) {
            cartDAO.removeCartItem(item.getCartId());
        }

        response.sendRedirect("orderSuccess.jsp");
    }
}
