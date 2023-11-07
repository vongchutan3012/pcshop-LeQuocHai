package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.holder.OrderStatusHolder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "userorders", urlPatterns = "/userorders")
public class UserOrdersServlet extends HttpServlet {
    // CONSTRUCTORS:
    public UserOrdersServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // Not logged in case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showLoginRequiredMessage(request, response);
            return;
        }

        // Get user in path
        User user = (User)path.get("user");

        // Get user's orders
        Set<Order> orders = user.getOrders();

        // Get order status holders
        List<OrderStatusHolder> orderStatusHolders = new ArrayList<>();
        try {
            orderStatusHolders = ServletUtil.getAllStatusHolders();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Set orders attribute for request
        request.setAttribute("orders", orders);

        // Set OrderStatus attribute for request
        request.setAttribute("OrderStatus", orderStatusHolders);

        // Get request dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/userorders.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
