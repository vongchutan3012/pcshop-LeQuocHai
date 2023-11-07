package gdu.pm05.group1.pcshop.controller;

import java.io.IOException;
import java.util.Map;

import gdu.pm05.group1.pcshop.controller.util.ServletUtil;
import gdu.pm05.group1.pcshop.controller.util.enums.UserValidationResult;
import gdu.pm05.group1.pcshop.model.User;
import gdu.pm05.group1.pcshop.model.dbhandler.IDBHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet (name = "changepassword", urlPatterns = "/changepassword")
public class ChangePasswordServlet extends HttpServlet {
    // CONSTRUCTORS:
    public ChangePasswordServlet() {
        super();
    }

    // METHODS:
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // User not logged in case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showLoginRequiredMessage(request, response);
            return;
        }

        // Get dispatcher
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/changepassword.jsp");

        // Forward
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get old password parameter
        String oldPassword = request.getParameter("oldPassword");

        // Old password null case
        if (oldPassword == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // Get new password parameter
        String newPassword = request.getParameter("newPassword");

        // New password null case
        if (newPassword == null) {
            ServletUtil.showInputRequiredMessage(request, response);
            return;
        }

        // User validation
        Map<String, Object> path = ServletUtil.userValidate(request, response);

        // Get user validation result
        UserValidationResult result = (UserValidationResult)path.get("userValidateResult");

        // Not logged in case
        if (result != UserValidationResult.SUCCESSFULLY) {
            ServletUtil.showLoginRequiredMessage(request, response);
            return;
        }

        // Get user from path
        User user = (User)path.get("user");

        // Old password not matches case
        if (!user.getPassword().equals(oldPassword)) {
            request.setAttribute("message", "Mật khẩu cũ không khớp!");
            this.doGet(request, response);
            return;
        }

        // Old password and new password matches each other
        if (oldPassword.equals(newPassword)) {
            request.setAttribute("message", "Mật khẩu cũ và mật khẩu mới trùng nhau!");
            this.doGet(request, response);
            return;
        }

        // Set new password for user
        user.setPassword(newPassword);

        // Get DBHandler from path
        IDBHandler dbHandler = (IDBHandler)path.get("dbHandler");

        // Save user to db
        dbHandler.save(user);

        // Send redirect back to user page
        response.sendRedirect("user");
    }
}
