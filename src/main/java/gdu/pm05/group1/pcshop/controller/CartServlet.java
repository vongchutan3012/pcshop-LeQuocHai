package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Set;

import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.CartItem;
import gdu.pm05.group1.pcshop.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet (name = "cart", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    // CONSTRUCTORS:
    public CartServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get session
        HttpSession session = request.getSession(true);

        // Get User from session
        User user = (User)session.getAttribute("user");

        // Get cart
        Cart cart = null;
        if (user == null) {
            cart = (Cart)session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
        }
        else {
            cart = user.getCart();
        }

        // Get cart's cart items
        Set<CartItem> cartItems = cart.getItems();

        // Set cartItems attribute for request
        request.setAttribute("cartItems", cartItems);

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/cart.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    
}
