package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.List;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.CartItem;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.OrderItem;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.OrderStatus;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "orderrequest", urlPatterns = "/orderrequest")
public class OrderRequestServlet extends HttpServlet {
    // CONSTRUCTORS:
    public OrderRequestServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result
        UserValidationResult userValidateResult = (UserValidationResult)path.get("userValidateResult");

        // Check result
        if (userValidateResult == UserValidationResult.NOT_LOGGED_IN) {
            ServletUtil.showLoginRequiredMessage(request, response);
            return;
        }

        // Get item cart ids (as parameter names)
        Enumeration<String> ids = request.getParameterNames();

        // Ids empty
        if (!ids.hasMoreElements()) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get user from path
        User user = (User)path.get("user");

        // Get user's cart
        Cart cart = user.getCart();

        // Get ordered cart items
        List<OrderItem> orderredItems = new ArrayList<>();
        while (ids.hasMoreElements()) {
            // Get id of ordered item
            String id = ids.nextElement();

            // Get ordered item in cart
            CartItem orderredCartItem = cart.getItem(id);

            // Ordered item not found in cart case
            if (orderredCartItem == null) {
                request.setAttribute(
                    "message",
                    "Không tìm thấy sản phẩm '@id' trong giỏ hàng, vui lòng thử lại!".replace(
                        "@id", id
                    )
                );
                request.getRequestDispatcher("cart").forward(request, response);
                return;
            }

            // Get item from orderedCartItem
            Item item = orderredCartItem.getItem();

            // Ordered item's amount not enough for order
            int itemAmount = item.getAmount();
            int cartItemAmount = orderredCartItem.getAmount();
            if (itemAmount < cartItemAmount) {
                request.setAttribute(
                    "message",
                    "Sản phẩm với mã '@id' không có đủ số lượng để phục vụ cho đơn hàng này!".replace(
                        "@id", id
                    )
                );
                request.getRequestDispatcher("cart").forward(request, response);
                return;
            }

            // Reduce amount of item pending on amount of item in cart
            item.removeAmount(cartItemAmount);

            // Save item to db
            dbHandler.save(item);

            // Create new OrderItem
            OrderItem orderredItem = new OrderItem();
            orderredItem.setItem(item);
            orderredItem.setAmount(cartItemAmount);

            // Store ordered cart item
            orderredItems.add(orderredItem);

            // After transferred items in cart into an order, remove that cart item from cart
            dbHandler.remove(orderredCartItem);
        }

        // Create new order
        Order order = new Order();

        // Setup informations for order
        order.setDate(new Date());
        order.setStatus(OrderStatus.AWAITING_CONFIRMATION);
        order.setUser(user);

        // Save order to db
        dbHandler.save(order);

        // Set Order for ordered items and save all to db
        for (OrderItem orderredItem : orderredItems) {
            orderredItem.setOrder(order);
            dbHandler.save(orderredItem);
        }

        // Refresh order
        dbHandler.refresh(order);

        // Re-calculate total price for order
        order.totalPriceCalculate();

        // Save order to db
        dbHandler.save(order);

        // Send redirect to order detail page
        response.sendRedirect(
            "orderdetail?id=@id".replace("@id", ""+order.getId())
        );
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
