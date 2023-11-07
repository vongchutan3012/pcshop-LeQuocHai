package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.GetCartResult;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.CartItem;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "removecartitem", urlPatterns = "/removecartitem")
public class RemoveCartItemServlet extends HttpServlet {
    // CONSTRUCTORS:
    public RemoveCartItemServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get id parameter
        String id = request.getParameter("id");

        // ID null case
        if (id == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get cart
        Map<String, Object> path = ServletUtil.getCart(request, response, false);

        // Get result
        GetCartResult result = (GetCartResult)path.get("getCartResult");

        // Not found case
        if (result == GetCartResult.NOT_FOUND) {
            ServletUtil.showMessage(
                request, response,
                "Không tìm thấy giỏ hàng!"
            );
            return;
        }

        // Get cart entity from path
        Cart cart = (Cart)path.get("cart");

        // Cart item removing
        for (CartItem cartItem : cart.getItems()) {
            // Remove cart item detected
            if (cartItem.getItem().getId().equals(id)) {
                // Removing cart item
                if (result == GetCartResult.USER_CART_FOUND) {
                    dbHandler.remove(cartItem);
                }
                else {
                    cart.getItems().remove(cartItem);
                }

                break;
            }
        }

        // Refresh user if needed
        if (result == GetCartResult.USER_CART_FOUND) {
            // Get user from path
            User user = (User)path.get("user");

            // Refresh user
            dbHandler.refresh(user);
        }

        // Send redirect back to cart page
        response.sendRedirect("cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
