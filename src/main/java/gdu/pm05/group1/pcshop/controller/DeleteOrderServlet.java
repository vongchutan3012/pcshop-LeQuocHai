package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.OrderItem;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import gdu.pm05.group1.pcshop.model.enums.OrderStatus;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Set;

@WebServlet (name = "deleteorder", urlPatterns = "/deleteorder")
public class DeleteOrderServlet extends HttpServlet {
    // CONSTRUCTORS:
    public DeleteOrderServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Administrator validation failed case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get id parameter
        String idStr = request.getParameter("id");

        // ID null case
        if (idStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get integer id
        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            ServletUtil.showMessage(
                request, response,
                e.toString()
            );
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get order entity with given id
        Order order = dbHandler.get(
            Order.class,
            new HQLParameter("id", id)
        );

        // Order null case
        if (order == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Check order status's
        OrderStatus status = order.getStatus();

        // Order status not delivered successfully and not cancelled
        if (
            status != OrderStatus.DELIVERED_SUCCESSFULLY
            &&
            status != OrderStatus.CANCELLED
        ) {
            ServletUtil.showMessage(
                request, response,
                "Chỉ có thể xóa những đơn hàng trong trạng thái đã giao hoặc đã hủy!"
            );
            return;
        }

        // Get related entities
        Set<OrderItem> items = order.getItems();

        // Delete all order items
        for (OrderItem item : items) {
            dbHandler.remove(item);
        }

        // Remove order
        dbHandler.remove(order);

        // Send redirect back to ordersmanagement page
        response.sendRedirect("ordersmanagement");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
