package com.ecommerce.servlet;

import com.ecommerce.dao.OrderDAO;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class OrdersServlet extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("currentUser");

        List<Order> orders = orderDAO.getOrdersByUser(user.getUserId());
        request.setAttribute("orders", orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("orders.jsp");
        dispatcher.forward(request, response);
    }
}
