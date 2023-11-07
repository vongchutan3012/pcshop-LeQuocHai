package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.model.Cart;
import gdu.pm05.group1.pcshop.model.Order;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.UserInfo;
import gdu.pm05.group1.pcshop.model.UserNotification;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "deleteuser", urlPatterns = "/deleteuser")
public class DeleteUserServlet extends HttpServlet {
    // CONSTRUCTORS:
    public DeleteUserServlet() {
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

        // Get username parameter
        String username = request.getParameter("username");

        // Username null case
        if (username == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get context
        ServletContext context = request.getServletContext();

        // Get DBHandler
        IDBHandler dbHandler = (IDBHandler)context.getAttribute("dbHandler");

        // Get user from given username
        User user = dbHandler.get(
            User.class,
            new HQLParameter("username", username)
        );

        // User not exist case
        if (user == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Get related entities
        UserInfo userInfo = user.getUserInfo();
        Cart cart = user.getCart();
        Set<UserNotification> notifications = user.getNotifications();
        Set<Order> orders = user.getOrders();

        // Having orders case
        if (orders.size() > 0) {
            ServletUtil.showMessage(
                request, response,
                "Tài khoản người dùng này đang có đơn hàng! Không thể xóa!"
            );
            return;
        }

        // Delete all user notifications
        for (UserNotification notification : notifications) {
            // Remove notification
            dbHandler.remove(notification);
        }

        // Deleting all related entities
        dbHandler.remove(cart, userInfo, user);

        // Send redirect to users management page
        response.sendRedirect("usersmanagement");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
