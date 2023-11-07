package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Item;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.OrderItem;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "cancelorder", urlPatterns = "/cancelorder")
public class CancelOrderServlet extends HttpServlet {
    // CONSTRUCTORS:
    public CancelOrderServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Validate user
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Retrieve user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // User validation failed case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showLoginRequiredMessage(request, response);
            return;
        }

        // Get id parameter
        String idStr = request.getParameter("id");

        // ID null case
        if (idStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Cast id string to an integer
        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            e.printStackTrace();
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)path.get("dbHandler");

        // Get order entity with given id
        Order order = dbHandler.get(Order.class, new HQLParameter("id", id));

        // Order not case
        if (order == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Get user
        User user = (User)path.get("user");

        // Check if order's user is different from current user
        if (!order.getUser().getUsername().equals(user.getUsername())) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Check and make sure order's status is awaiting confirmation
        if (order.getStatus() != OrderStatus.AWAITING_CONFIRMATION) {
            ServletUtil.showMessage(
                request, response,
                "Đơn hàng chỉ có thể hủy trong trạng thái chờ xác nhận!"
            );
            return;
        }

        // Change order's status to cancelled
        order.setStatus(OrderStatus.CANCELLED);

        // Save order to DB
        dbHandler.save(order);

        // Recover item's amount in order
        for (OrderItem orderItem : order.getItems()) {
            Item item = orderItem.getItem();
            item.addAmount(orderItem.getAmount());
            dbHandler.save(item);
        }

        // Send redirect back to current url
        response.sendRedirect(
            "orderdetail?id=@id".replace("@id", ""+id)
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
