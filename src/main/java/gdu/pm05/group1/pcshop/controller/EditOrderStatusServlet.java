package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.OrderStatus;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "editorderstatus", urlPatterns = "/editorderstatus")
public class EditOrderStatusServlet extends HttpServlet {
    // CONSTRUCTORS:
    public EditOrderStatusServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Method not allowed
        response.setStatus(
            HttpServletResponse.SC_METHOD_NOT_ALLOWED
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validate result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // User not admin case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get order id
        String idStr = request.getParameter("id");

        // ID null case
        if (idStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Cast id into an integer
        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            e.printStackTrace();
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // Get status
        String statusStr = request.getParameter("status");

        // Status string null case
        if (statusStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Cast statusStr to OrderStatus
        OrderStatus status = OrderStatus.valueOf(statusStr);

        // Status null case
        if (status == null) {
            ServletUtil.showInvalidInputMessage(request, response);
            return;
        }

        // Get DBHander
        IDBHandler dbHandler = (IDBHandler)path.get("dbHandler");

        // Get Order entity with given id
        Order order = dbHandler.get(Order.class, new HQLParameter("id", id));

        // Order not exist case
        if (order == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Set order status for order
        order.setStatus(status);

        // Save order into db
        dbHandler.save(order);

        // Send redirect back to ordersmanagement page
        response.sendRedirect("ordersmanagement");
    }
}
