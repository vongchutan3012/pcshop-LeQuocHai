package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.Notification;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.UserNotification;
import gdu.pm05.group1.pcshop.model.dbhandler.HQLParameter;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "notificationdetail", urlPatterns = "/notificationdetail")
public class NotificationDetailServlet extends HttpServlet {
    // CONSTRUCTORS:
    public NotificationDetailServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // Not logged in case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showLoginRequiredMessage(request, response);
            return;
        }

        // Get id parameter
        String idStr = request.getParameter("id");

        // Id null case
        if (idStr == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Cast id from a string to an integer
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

        // Get notification from given id
        Notification notification = dbHandler.get(
            Notification.class,
            new HQLParameter("id", id)
        );

        // Notification not exist case
        if (notification == null) {
            ServletUtil.showDataNotExistMessage(request, response);
            return;
        }

        // Get user from path
        User user = (User)path.get("user");

        // Check if user is allowed to view this notification
        boolean allowed = false;
        for (UserNotification userNotification : user.getNotifications()) {
            if (userNotification.getNotification().getId() == id) {
                // Update seen proeprty for user notification
                userNotification.setSeen(true);

                // Save user notification to db
                dbHandler.save(userNotification);

                // Update allowed
                allowed = true;
                break;
            }
        }

        // If not allowed
        if (!allowed) {
            // Ger administrator validation result
            AdministratorValidationResult adminResult = (AdministratorValidationResult)path.get("administratorValidateResult");

            // Update allowed
            allowed = (adminResult == AdministratorValidationResult.IS_ADMINISTRATOR);
        }

        // Finally, not allowed
        if (!allowed) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Set notification attribute for request
        request.setAttribute("notification", notification);

        // Set referer attribute for request
        request.setAttribute(
            "referer",
            request.getHeader("referer")
        );

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/notificationdetail.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
