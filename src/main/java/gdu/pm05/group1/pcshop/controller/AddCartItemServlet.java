package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.CartItem;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet (name = "addcartitem", urlPatterns = "/addcartitem")
public class AddCartItemServlet extends HttpServlet {
    // CONSTRUCTORS:
    public AddCartItemServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // ID parameter
        String id = request.getParameter("id");

        // ID Null case
        if (id == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get amount
        String amountStr = request.getParameter("amount");

        // Amount null case
        if (amountStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Cast amount from string to an integer
        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        }
        catch (Exception e) {
            e.printStackTrace();
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get Item entity from give id
        Item item = dbHandler.get(Item.class, new HQLParameter("id", id));

        // Item not exist case
        if (item == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Amount required not valid
        if (amount > item.getAmount()) {
            ServletUtil.showMessage(
                request, response,
                "Số lượng thêm không hợp lệ, vui lòng thử lại!"
            );
            return;
        }

        // Get session
        HttpSession session = request.getSession(false);

        // Session null case
        if (session == null) {
            session = request.getSession(true);
        }

        // Get user attribute from session
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

        // Get CartItem
        CartItem cartItem = cart.getItem(id);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setCart(cart);
        }
        else {
            if ((cartItem.getAmount() + amount) > item.getAmount()) {
                ServletUtil.showMessage(
                    request, response,
                    "Số lượng thêm không hợp lệ, vui lòng thử lại!"
                );
                return;
            }
        }

        // Set cart item amount
        cartItem.addAmount(amount);

        // Save cart item if this cart is belong to an User
        if (user != null) {
            dbHandler.save(cartItem);
            dbHandler.refresh(user);
        }
        // Add cart item into cart if not belong to any User
        else {
            cart.getItems().add(cartItem);
        }

        // Send redirect back to cart page
        response.sendRedirect("cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
