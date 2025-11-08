package com.ecommerce.servlet;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Check if user is admin
        com.ecommerce.model.User user = (com.ecommerce.model.User) session.getAttribute("currentUser");
        if (!"admin".equals(user.getRole())) {
            response.sendRedirect("products.jsp");
            return;
        }

        List<Product> products = productDAO.getAllProducts();
        request.setAttribute("products", products);

        RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
        dispatcher.forward(request, response);
    }
}
