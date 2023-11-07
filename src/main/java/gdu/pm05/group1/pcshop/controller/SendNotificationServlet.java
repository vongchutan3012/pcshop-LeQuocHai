package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.AdministratorValidationResult;
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

@WebServlet (name = "sendnotification", urlPatterns = "/sendnotification")
public class SendNotificationServlet extends HttpServlet {
    // CONSTRUCTORS:
    public SendNotificationServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Not administrator case
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/sendnotification.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Administrator validation
        Map<String, Object> path = ServletUtil.administratorValidate(request, response);

        // Get administrator validation result
        AdministratorValidationResult result = (AdministratorValidationResult)path.get("administratorValidateResult");

        // Check result
        if (result != AdministratorValidationResult.IS_ADMINISTRATOR) {
            ServletUtil.showPermissionRequiredMessage(request, response);
            return;
        }

        // Get necessary parameter
        String to = request.getParameter("to");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // Get DBHandler from path
        IDBHandler dbHandler = (IDBHandler)path.get("dbHandler");

        // Create a new notification
        Notification notification = new Notification();
        notification.setDate(new Date());
        notification.setTitle(title);
        notification.setContent(content);

        // Create a list of receiver
        List<User> receivers = new ArrayList<>();

        // Send to all case
        if (to.equals("*")) {
            // Get all users and assign into receivers
            receivers = dbHandler.getAll(User.class);
        }
        else {
            // Get User from given "to" as an email
            User user = dbHandler.get(User.class, new HQLParameter("username", to));

            // User not exist case
            if (user == null) {
                request.setAttribute(
                    "message",
                    "Người dùng '@username' không tồn tại!".replace("@username", to)
                );
                this.doGet(request, response);
                return;
            }

            // Add user into receivers
            receivers.add(user);
        }

        // Receivers empty case
        if (receivers.isEmpty()) {
            request.setAttribute(
                "message",
                "Không có bất kỳ người nhận nào cho thông báo này!"
            );
            this.doGet(request, response);
            return;
        }

        // Save notification into db
        dbHandler.save(notification);

        // Send to all receivers
        for (User receiver : receivers) {
            // Create new usernotification entity
            UserNotification userNotification = new UserNotification();

            // Assign informations for userNotification
            userNotification.setUser(receiver);
            userNotification.setNotification(notification);
            userNotification.setSeen(false);

            // Save userNotification into db
            dbHandler.save(userNotification);
        }

        // Send redirect back to send notification page
        response.sendRedirect("sendnotification");
    }
}
