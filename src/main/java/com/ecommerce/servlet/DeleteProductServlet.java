package com.ecommerce.servlet;

import com.ecommerce.dao.ProductDAO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {

    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int productId = Integer.parseInt(request.getParameter("productId"));
        productDAO.deleteProduct(productId);
        response.sendRedirect("admin");
    }
}
	