package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.UserNotification;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name="notification", urlPatterns="/notification")
public class NotificationServlet extends HttpServlet {
    // CONSTRUCTORS:
    public NotificationServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // User not logged in case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get user from path
        User user = (User)path.get("user");

        // Get user's notifications
        Set<UserNotification> notifications = user.getNotifications();

        // Set notifications attribute for request
        request.setAttribute("notifications", notifications);

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/notification.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws ServletException, IOException {
        this.doGet(request, response);
    }
}