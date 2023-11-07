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

@WebServlet (name = "editcartitemamount", urlPatterns = "/editcartitemamount")
public class EditCartItemAmountServlet extends HttpServlet {
    // CONSTRUCTORS:
    public EditCartItemAmountServlet() {
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

        // Get amount parameter
        String amountStr = request.getParameter("amount");

        // Amount null case
        if (amountStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Cast amount string to an integer
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } 
        catch (Exception e) {
            e.printStackTrace();
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

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

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get cart
        Cart cart = (Cart)path.get("cart");

        // Edit cart item amount
        for (CartItem cartItem : cart.getItems()) {
            // Target cart item detect
            if (cartItem.getItem().getId().equals(id)) {
                // Amount not valid case
                if ((cartItem.getAmount() + amount) > cartItem.getItem().getAmount()) {
                    ServletUtil.showMessage(
                        request, response,
                        "Số lượng sản phẩm trong giỏ hàng đã đạt đến tối đa!"
                    );
                    return;
                }

                // Edit amount
                cartItem.setAmount(
                    cartItem.getAmount() + amount
                );

                // Amount less than 1 case
                if (cartItem.getAmount() < 1) {
                    if (result == GetCartResult.USER_CART_FOUND) {
                        dbHandler.remove(cartItem);
                    }
                    else {
                        cart.getItems().remove(cartItem);
                    }
                }
                // Otherwise
                else {
                    if (result == GetCartResult.USER_CART_FOUND) {
                        dbHandler.save(cartItem);
                    }
                }
                
                break;
            }
        }

        // Refresh user if neccessary
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
